package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PassengerRepositoryInMemory extends BaseRepositoryInMemory<Passenger, String> {

    public PassengerRepositoryInMemory() {
        super(Passenger::getId);
    }
}