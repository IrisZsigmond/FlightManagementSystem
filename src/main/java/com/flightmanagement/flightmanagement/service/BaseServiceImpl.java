package com.flightmanagement.flightmanagement.service;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

/**
 * Generic base class that implements the common CRUD from BaseService.
 * Entity-specific services can extend this and only add custom methods.
 */
public abstract class BaseServiceImpl<T, ID> implements BaseService<T, ID> {

    private final AbstractRepository<T, ID> repository;

    protected BaseServiceImpl(AbstractRepository<T, ID> repository) {
        this.repository = repository;
    }

    /// Give subclasses safe access if they need it
    protected AbstractRepository<T, ID> repo() {
        return repository;
    }

    @Override
    public T save(T entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must not be null");
        repository.save(entity);
        return entity;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public T update(ID id, T updatedEntity) {
        if (updatedEntity == null || id == null)
            throw new IllegalArgumentException("Updated entity and id must not be null");
        boolean replaced = repository.update(id, updatedEntity);
        if (!replaced) {
            throw new IllegalArgumentException("Entity not found: " + id);
        }
        return updatedEntity;
    }

    @Override
    public boolean delete(ID id) {
        return repository.delete(id);
    }

    @Override
    public boolean existsById(ID id) {
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
