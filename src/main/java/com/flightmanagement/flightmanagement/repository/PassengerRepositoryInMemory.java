package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*In-memory repository for managing Passenger entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for Passenger objects*/
@Repository
public class PassengerRepositoryInMemory extends BaseRepositoryInMemory<Passenger, String> {

    public PassengerRepositoryInMemory() {
        super(Passenger::getId);
    }
}