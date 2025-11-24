package com.flightmanagement.flightmanagement.repository.InFileRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.flightmanagement.flightmanagement.config.AppDataProperties;
import com.flightmanagement.flightmanagement.model.Passenger;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * File-backed repository for Passenger.
 * Loads/saves passengers into passengers.json under the runtime data directory.
 */
@Repository
public class PassengerFileRepo extends InFileRepository<Passenger, String> {

    public PassengerFileRepo(AppDataProperties props, ResourceLoader resourceLoader) {
        super(
                "passengers.json",
                new TypeReference<List<Passenger>>() {},
                Passenger::getId,
                props,
                resourceLoader
        );
    }
}
