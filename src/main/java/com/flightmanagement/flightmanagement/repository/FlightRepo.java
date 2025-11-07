package com.flightmanagement.flightmanagement.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.flightmanagement.flightmanagement.model.Flight;

/**In-memory repository for managing Flight entities.
 * This class extends the generic BaseRepositoryInMemory
 * and provides CRUD operations specifically for Flight objects*/
@Repository
@Primary
public class FlightRepo extends BaseRepositoryInMemory<Flight, String> {
    public FlightRepo() {
        super(Flight::getId);
    }
}
