package com.flightmanagement.flightmanagement.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flightmanagement.flightmanagement.config.AppDataProperties;
import com.flightmanagement.flightmanagement.model.Airplane;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;

/**
 * File-backed implementation of AbstractRepository that persists all entities
 * of type T in a single JSON array file under a configurable runtime data dir.
 *
 * Seed files must exist under classpath:/data/*.json with ≥10 valid records.
 * At runtime, data is read from and written to ${fms.data-dir}/<fileName>.
 */
public class InFileRepository<T, ID> implements AbstractRepository<T, ID> {

    private final Path runtimeFile;
    private final ObjectMapper mapper;
    private final TypeReference<List<T>> listType;
    private final Function<T, ID> idGetter;

    // In-memory state
    private final List<T> items = new ArrayList<>();
    private final Map<ID, Integer> indexById = new HashMap<>();

    /**
     * @param fileName the canonical JSON file name (e.g., "flights.json")
     * @param listType Jackson TypeReference for List<T>
     * @param idGetter function to extract the entity ID
     * @param props app data properties (dir + auto-seed policy)
     * @param resourceLoader to access classpath:/data/<fileName> seeds
     */
    public InFileRepository(
            String fileName,
            TypeReference<List<T>> listType,
            Function<T, ID> idGetter,
            AppDataProperties props,
            ResourceLoader resourceLoader
    ) {
        if (fileName == null || fileName.isBlank())
            throw new IllegalArgumentException("fileName must not be empty");
        if (listType == null) throw new IllegalArgumentException("listType must not be null");
        if (idGetter == null) throw new IllegalArgumentException("idGetter must not be null");
        if (props == null) throw new IllegalArgumentException("props must not be null");

        this.listType = listType;
        this.idGetter = idGetter;

        try {
            Path dataDir = Paths.get(props.getDataDir()).toAbsolutePath().normalize();
            Files.createDirectories(dataDir);
            this.runtimeFile = dataDir.resolve(fileName);

            ensureRuntimeFileExists(fileName, props.isAutoSeed(), resourceLoader);
            this.mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .enable(SerializationFeature.INDENT_OUTPUT);

            loadFromDisk(); // fill items + index
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to prepare repository for " + fileName, e);
        }
    }

    private void ensureRuntimeFileExists(String fileName, boolean autoSeed, ResourceLoader rl) throws IOException {
        if (Files.exists(runtimeFile)) return;

        if (!autoSeed) {
            throw new IOException("Missing data file: " + runtimeFile
                    + " (auto-seed disabled; provide the file before starting)");
        }

        // Try to copy from classpath seed
        String seedLocation = "classpath:data/" + fileName;
        Resource seed = rl.getResource(seedLocation);
        if (!seed.exists()) {
            throw new IOException("Seed file not found on classpath: " + seedLocation);
        }

        try (InputStream in = seed.getInputStream()) {
            Files.createDirectories(runtimeFile.getParent());
            Files.copy(in, runtimeFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private synchronized void loadFromDisk() {
        try {
            if (!Files.exists(runtimeFile)) {
                // Should not happen with ensureRuntimeFileExists, but keep a safe default.
                Files.writeString(runtimeFile, "[]", StandardCharsets.UTF_8);
            }

            List<T> loaded;
            byte[] bytes = Files.readAllBytes(runtimeFile);
            if (bytes.length == 0) {
                // Treat empty file as empty JSON array
                loaded = new ArrayList<>();
                Files.writeString(runtimeFile, "[]", StandardCharsets.UTF_8);
            } else {
                loaded = Optional.ofNullable(mapper.readValue(bytes, listType)).orElseGet(ArrayList::new);
            }

            items.clear();
            items.addAll(loaded);
            rebuildIndex();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load JSON from " + runtimeFile, e);
        }
    }

    private void rebuildIndex() {
        indexById.clear();
        for (int i = 0; i < items.size(); i++) {
            T entity = items.get(i);
            ID id = idGetter.apply(entity);
            if (id == null) {
                throw new IllegalStateException("Entity with null ID found in file: " + runtimeFile);
            }
            if (indexById.put(id, i) != null) {
                throw new IllegalStateException("Duplicate ID '" + id + "' in file: " + runtimeFile);
            }
        }
    }

    private synchronized void flushToDisk() {
        try {
            // Simple write (pretty printed). You can add temp-file + move later if needed.
            mapper.writeValue(runtimeFile.toFile(), items);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write JSON to " + runtimeFile, e);
        }
    }

    // ------------------- CRUD -------------------

    @Override
    public synchronized Airplane save(T entity) {
        if (entity == null) throw new IllegalArgumentException("entity cannot be null");
        ID id = idGetter.apply(entity);
        if (id == null) throw new IllegalArgumentException("entity ID cannot be null");
        if (indexById.containsKey(id)) {
            throw new IllegalArgumentException("Entity with ID '" + id + "' already exists");
        }
        items.add(entity);
        indexById.put(id, items.size() - 1);
        flushToDisk();
        return null;
    }

    @Override
    public synchronized List<T> findAll() {
        return new ArrayList<>(items); // defensive copy
    }

    @Override
    public synchronized boolean update(ID id, T updated) {
        if (id == null) throw new IllegalArgumentException("id must not be null");
        if (updated == null) throw new IllegalArgumentException("updated entity must not be null");

        ID updatedId = idGetter.apply(updated);
        if (updatedId == null) throw new IllegalArgumentException("updated entity ID must not be null");
        if (!id.equals(updatedId))
            throw new IllegalArgumentException("Updated entity ID must match the path ID");

        Integer idx = indexById.get(id);
        if (idx == null) return false;

        items.set(idx, updated);
        // index key stays the same, no rebuild needed
        flushToDisk();
        return true;
    }

    @Override
    public synchronized boolean delete(ID id) {
        Integer idx = indexById.get(id);
        if (idx == null) return false;

        // Swap-remove to keep O(1), then fix index of the moved element
        int lastIndex = items.size() - 1;
        T removed = items.remove((int) idx);
        indexById.remove(id);

        if (idx < lastIndex) {
            T moved = items.get(idx);
            ID movedId = idGetter.apply(moved);
            indexById.put(movedId, idx);
        }

        flushToDisk();
        return removed != null;
    }

    // ------------------- Extras -------------------

    @Override
    public synchronized Optional<T> findById(ID id) {
        Integer idx = indexById.get(id);
        return idx == null ? Optional.empty() : Optional.of(items.get(idx));
    }

    @Override
    public synchronized boolean existsById(ID id) {
        return indexById.containsKey(id);
    }

    @Override
    public synchronized long count() {
        return items.size();
    }

    @Override
    public synchronized void clear() {
        items.clear();
        indexById.clear();
        flushToDisk();
    }
}
