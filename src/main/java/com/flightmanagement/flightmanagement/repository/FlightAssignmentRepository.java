package com.flightmanagement.flightmanagement.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * In-memory repository for managing FlightAssignment entities.
 * <p>
 * This class extends the generic BaseRepositoryInMemory and provides
 * CRUD operations specifically for FlightAssignment objects.
 */
@Repository
@Primary
public class FlightAssignmentRepository extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.FlightAssignment, String> {

    /**
     * Constructor that configures how the repository extracts the ID of a FlightAssignment.
     */
    public FlightAssignmentRepository() {
        super(com.flightmanagement.flightmanagement.model.FlightAssignment::getId);
    }
}
