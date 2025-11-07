package com.flightmanagement.flightmanagement.repository;

import org.springframework.stereotype.Repository;

/**
 * In-memory repository for managing Passenger entities.
 * <p>
 * This class extends the generic BaseRepositoryInMemory and provides
 * CRUD operations specifically for Passenger objects.
 */
@Repository
public class PassengerRepository extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.Passenger, String> {

    /**
     * Constructor that configures how the repository extracts the ID of a Passenger.
     */
    public PassengerRepository() {
        super(com.flightmanagement.flightmanagement.model.Passenger::getId);
    }
}
