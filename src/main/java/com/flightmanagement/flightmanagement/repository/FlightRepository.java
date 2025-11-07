package com.flightmanagement.flightmanagement.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * In-memory repository for managing Flight entities.
 * <p>
 * This class extends the generic BaseRepositoryInMemory and provides
 * CRUD operations specifically for Flight objects.
 */
@Repository
@Primary
public class FlightRepository extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.Flight, String> {

    /**
     * Constructor that configures how the repository extracts the ID of a Flight.
     */
    public FlightRepository() {
        super(com.flightmanagement.flightmanagement.model.Flight::getId);
    }
}
