package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Flight;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/*In-memory repository for managing Flight entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for Flight objects*/
@Repository
@Primary
public class FlightRepositoryInMemory extends BaseRepositoryInMemory<Flight, String> {
    public FlightRepositoryInMemory() {
        super(Flight::getId);
    }
}
