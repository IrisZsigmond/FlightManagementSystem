package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of LuggageService that provides business logic
 * and interacts with the Luggage repository to perform CRUD operations.
 */
@Service
public class LuggageServiceImpl implements LuggageService {

    private final AbstractRepository<Luggage, String> repository;

    public LuggageServiceImpl(AbstractRepository<Luggage, String> repository) {
        this.repository = repository;
    }

    @Override
    public Luggage save(Luggage entity) {
        if (entity == null || entity.getId() == null) {
            throw new IllegalArgumentException("Luggage and its ID must not be null");
        }
        repository.save(entity);
        return entity;
    }

    @Override
    public List<Luggage> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Luggage> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Luggage update(String id, Luggage updatedEntity) {
        boolean success = repository.update(id, updatedEntity);
        if (!success) {
            throw new IllegalArgumentException("Luggage not found: " + id);
        }
        return updatedEntity;
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void clear() {
        repository.clear();
    }
}
