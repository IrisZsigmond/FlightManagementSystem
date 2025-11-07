package com.flightmanagement.flightmanagement.repository;

import org.springframework.stereotype.Repository;

/**
 * In-memory repository for managing Luggage entities.
 * <p>
 * This class extends the generic {@code BaseRepositoryInMemory} and provides
 * CRUD operations specifically for {@code Luggage} objects.
 */
@Repository
public class LuggageRepository extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.Luggage, String> {

    /**
     * Constructs a new Luggage repository instance.
     * Uses the Luggage ID as the unique identifier.
     */
    public LuggageRepository() {
        super(com.flightmanagement.flightmanagement.model.Luggage::getId);
    }
}
