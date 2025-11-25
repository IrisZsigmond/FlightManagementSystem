package com.flightmanagement.flightmanagement.repository.InMemoryRepositories;

import com.flightmanagement.flightmanagement.model.FlightAssignment;
import org.springframework.stereotype.Repository;

/** In-memory repository for managing FlightAssignment entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for FlightAssignment objects*/
//@Repository // Spring Boot registration (discoverable and injectable)
            // Layer identification     (data acces layer component)
public class FlightAssignmentRepo extends BaseRepositoryInMemory<FlightAssignment, String> {
    public FlightAssignmentRepo() {
        super(FlightAssignment::getId);
    }
}
