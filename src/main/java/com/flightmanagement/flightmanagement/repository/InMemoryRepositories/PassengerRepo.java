package com.flightmanagement.flightmanagement.repository.InMemoryRepositories;

import com.flightmanagement.flightmanagement.model.Passenger;
import org.springframework.stereotype.Repository;

/**In-memory repository for managing Passenger entities.
 * This class extends the generic BaseRepositoryInMemory and
 * provides CRUD operations specifically for Passenger objects.*/
@Repository
public class PassengerRepo extends BaseRepositoryInMemory<Passenger, String> {

    public PassengerRepo() {
        super(Passenger::getId);
    }
}