package com.flightmanagement.flightmanagement.repository.InFileRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.flightmanagement.flightmanagement.config.AppDataProperties;
import com.flightmanagement.flightmanagement.model.AirportEmployee;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * File-backed repository for AirportEmployee.
 * Loads/saves airport employees into airport-employees.json under the runtime data directory.
 */
//@Repository
public class AirportEmployeeFileRepo extends InFileRepository<AirportEmployee, String> {

    public AirportEmployeeFileRepo(AppDataProperties props, ResourceLoader resourceLoader) {
        super(
                "airport-employees.json",                        // file name
                new TypeReference<List<AirportEmployee>>() {},  // type hint
                AirportEmployee::getId,                         // ID getter
                props,
                resourceLoader
        );
    }
}
