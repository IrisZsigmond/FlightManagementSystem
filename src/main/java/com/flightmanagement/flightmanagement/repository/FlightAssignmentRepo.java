package com.flightmanagement.flightmanagement.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.flightmanagement.flightmanagement.model.FlightAssignment;

/** In-memory repository for managing FlightAssignment entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for FlightAssignment objects*/
@Repository // Spring Boot registration (discoverable and injectable)
            // Layer identification     (data acces layer component)
@Primary
public class FlightAssignmentRepo extends BaseRepositoryInMemory<FlightAssignment, String> {
    public FlightAssignmentRepo() {
        super(FlightAssignment::getId);
    }
}
