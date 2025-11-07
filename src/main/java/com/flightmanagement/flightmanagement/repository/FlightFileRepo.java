package com.flightmanagement.flightmanagement.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.flightmanagement.flightmanagement.config.AppDataProperties;
import com.flightmanagement.flightmanagement.model.Flight;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary // ensure this replaces any in-memory bean if still present
public class FlightFileRepo extends InFileRepository<Flight, String> {

    public FlightFileRepo(AppDataProperties props, ResourceLoader resourceLoader) {
        super(
                "flights.json",
                new TypeReference<List<Flight>>() {},
                Flight::getId,
                props,
                resourceLoader
        );
    }
}
