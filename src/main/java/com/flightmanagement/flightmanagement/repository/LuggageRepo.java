package com.flightmanagement.flightmanagement.repository;

import org.springframework.stereotype.Repository;
import com.flightmanagement.flightmanagement.model.Luggage;

/**In-memory repository for managing Luggage entities.
 * This class extends the generic BaseRepositoryInMemory
 * and provides CRUD operations specifically for Luggage objects*/
@Repository
public class LuggageRepo extends BaseRepositoryInMemory<Luggage, String> {

    public LuggageRepo() {
        super(Luggage::getId);
    }
}

