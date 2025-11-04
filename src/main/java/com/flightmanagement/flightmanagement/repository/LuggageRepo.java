package com.flightmanagement.flightmanagement.repository;

import org.springframework.stereotype.Repository;

/*In-memory repository for managing Luggage entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for Luggage objects*/
@Repository
public class LuggageRepo extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.Luggage, String> {

    public LuggageRepo() {
        super(com.flightmanagement.flightmanagement.model.Luggage::getId);
    }
}

