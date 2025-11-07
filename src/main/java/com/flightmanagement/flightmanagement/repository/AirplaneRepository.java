package com.flightmanagement.flightmanagement.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * In-memory repository for managing Airplane entities.
 * <p>
 * This class extends the generic BaseRepositoryInMemory and provides
 * CRUD operations specifically for Airplane objects.
 */
@Repository
@Primary
public class AirplaneRepository
        extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.Airplane, String> {

    /**
     * Constructor that configures how the repository extracts the ID from an Airplane.
     */
    public AirplaneRepository() {
        super(com.flightmanagement.flightmanagement.model.Airplane::getId);
    }
}
