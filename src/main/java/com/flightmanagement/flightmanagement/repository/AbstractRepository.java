package com.flightmanagement.flightmanagement.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbstractRepository<T, ID> {
    void save(T entity);
    void saveOrUpdate(T entity);
    List<T> findAll();
    Optional<T> findById(ID id);
    boolean delete(ID id);
    boolean update(ID id, T updated); // must keep same ID
    boolean existsById(ID id);
    long count();
    void clear();

    default List<T> findAll(int offset, int limit) {
        List<T> all = findAll();
        int from = Math.max(0, Math.min(offset, all.size()));
        int to = Math.max(from, Math.min(from + limit, all.size()));
        return all.subList(from, to);
    }
}
