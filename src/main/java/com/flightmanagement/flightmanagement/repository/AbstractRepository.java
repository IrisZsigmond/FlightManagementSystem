package com.flightmanagement.flightmanagement.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*Generic repository interface that defines basic CRUD operations for any data type.*/
@Repository
public interface AbstractRepository<T, ID> {
    void save(T entity);                               //saves a new entity if it doesn't exist
    List<T> findAll();                                 //retrieves all entities in the repository
    Optional<T> findById(ID id);                       //finds an entity by its ID
    boolean delete(ID id);                             //deletes an entity by its ID
    boolean update(ID id, T updated);                  // must keep same ID
    boolean existsById(ID id);                         //checks if an entity with the given ID exists
    long count();                                      //returns the number of entities in the repository
    void clear();                                      //clears all entities from the repository

    default List<T> findAll(int offset, int limit) {   //returns a sublist of entities for pagination
        List<T> all = findAll();
        int from = Math.max(0, Math.min(offset, all.size()));
        int to = Math.max(from, Math.min(from + limit, all.size()));
        return all.subList(from, to);
    }
}
