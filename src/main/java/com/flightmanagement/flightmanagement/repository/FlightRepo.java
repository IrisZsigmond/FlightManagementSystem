package com.flightmanagement.flightmanagement.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/*In-memory repository for managing Flight entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for Flight objects*/
@Repository
@Primary
public class FlightRepo extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.Flight, String> {
    public FlightRepo() {
        super(com.flightmanagement.flightmanagement.model.Flight::getId);
    }
}
