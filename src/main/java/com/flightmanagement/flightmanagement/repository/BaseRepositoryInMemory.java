package com.flightmanagement.flightmanagement.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * In-memory repository implementation of AbstractRepository.
 *
 * Uses a ConcurrentHashMap to store entities to ensure thread safety.
 */
public class BaseRepositoryInMemory<T, ID> implements AbstractRepository<T, ID> {

    /**
     * Used "final" to ensure that the store reference cannot be changed
     * after initialization.
     */
    private final Map<ID, T> store = new ConcurrentHashMap<>();

    /**
     * Used "final" to ensure that the idExtractor reference cannot be changed
     * after initialization.
     */
    private final Function<T, ID> idExtractor;

    public BaseRepositoryInMemory(Function<T, ID> idExtractor) {
        if (idExtractor == null) {
            throw new IllegalArgumentException("idExtractor cannot be null");
        }
        this.idExtractor = idExtractor;
    }

    @Override
    public void save(T entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity cannot be null");
        ID id = idExtractor.apply(entity);
        if (id == null)
            throw new IllegalArgumentException("entity ID cannot be null");
        store.putIfAbsent(id, entity);
    }

    @Override
    public List<T> findAll() {
        // defensive copy
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public boolean delete(ID id) {
        return store.remove(id) != null;
    }

    @Override
    public boolean update(ID id, T updated) {
        if (updated == null)
            throw new IllegalArgumentException("updated entity cannot be null");
        ID updatedId = idExtractor.apply(updated);
        if (updatedId == null)
            throw new IllegalArgumentException("updated entity ID cannot be null");
        if (!updatedId.equals(id))
            throw new IllegalArgumentException("Updated entity ID must match the path ID");

        return store.replace(id, updated) != null;
    }

    @Override
    public boolean existsById(ID id) {
        return store.containsKey(id);
    }

    @Override
    public long count() {
        return store.size();
    }

    @Override
    public void clear() {
        store.clear();
    }
}
