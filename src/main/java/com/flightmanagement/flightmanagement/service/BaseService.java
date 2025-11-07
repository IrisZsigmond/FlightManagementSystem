package com.flightmanagement.flightmanagement.service;

import java.util.List;
import java.util.Optional;

/**
 * Generic service interface defining CRUD operations.
 * Mirrors the methods from AbstractRepository for use at the Service Layer.
 *
 * @param <T>  Entity type
 * @param <ID> Identifier type
 */
public interface BaseService<T, ID> {
    /**
     * Saves a new entity to the repository.
     * @return the saved entity
     */
    T save(T entity);

    /**
     * Retrieves all entities from the repository.
     * @return the list of all entities
     */
    List<T> findAll();

    /**
     * Updates an existing entity identified by the given ID.
     * @return the upated entity
     */
    T update(ID id, T updatedEntity);

    /**
     * Deletes an entity by its ID.
     * @return true if deleted / false if not found
     */
    boolean delete(ID id);

    /**
     * Retrieves an entity by its ID.
     * @return the entity if it was found / nothing
     */
    Optional<T> findById(ID id);

    /**
     * Checks whether an entity with the given ID exists.
     * @return true if it was found / false otherwise
     */
    boolean existsById(ID id);

    /**
     * Returns the number of entities stored.
     */
    long count();

    /**
     * Removes all entities from the repository.
     */
    void clear();
}
