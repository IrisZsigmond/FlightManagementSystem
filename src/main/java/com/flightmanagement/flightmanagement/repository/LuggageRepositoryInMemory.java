package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Luggage;
import org.springframework.stereotype.Repository;

/*In-memory repository for managing Luggage entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for Luggage objects*/
@Repository
public class LuggageRepositoryInMemory extends BaseRepositoryInMemory<Luggage, String> {

    public LuggageRepositoryInMemory() {
        super(Luggage::getId);
    }
}

