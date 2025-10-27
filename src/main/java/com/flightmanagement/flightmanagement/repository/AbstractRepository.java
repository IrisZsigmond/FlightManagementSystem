package com.flightmanagement.flightmanagement.repository;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T> {

    public abstract void save(T entity);

    public abstract List<T> findAll();

    public abstract Optional<T> findById(String id);

    public abstract void update(String id, T entity);

    public abstract void delete(String id);
}
