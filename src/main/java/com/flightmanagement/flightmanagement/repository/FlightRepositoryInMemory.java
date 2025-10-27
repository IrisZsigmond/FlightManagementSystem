package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Flight;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


@Repository
@Primary
public class FlightRepositoryInMemory extends BaseRepositoryInMemory<Flight, String> {
    public FlightRepositoryInMemory() {
        super(Flight::getId);
    }
}
