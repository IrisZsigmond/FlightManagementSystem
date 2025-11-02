package com.flightmanagement.flightmanagement.repository;

import org.springframework.stereotype.Repository;

/*In-memory repository for managing Passenger entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for Passenger objects*/
@Repository
public class Passenger extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.Passenger, String> {

    public Passenger() {
        super(com.flightmanagement.flightmanagement.model.Passenger::getId);
    }
}