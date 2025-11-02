package com.flightmanagement.flightmanagement.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/* In-memory repository for managing FlightAssignment entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for FlightAssignment objects*/
@Repository // Spring Boot registration (discoverable and injectable)
            // Layer identification     (data acces layer component)
@Primary
public class FlightAssignment extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.FlightAssignment, String> {
    public FlightAssignment() {
        super(com.flightmanagement.flightmanagement.model.FlightAssignment::getId);
    }
}
