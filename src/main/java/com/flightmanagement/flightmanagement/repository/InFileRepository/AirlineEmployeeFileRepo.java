package com.flightmanagement.flightmanagement.repository.InFileRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.flightmanagement.flightmanagement.config.AppDataProperties;
import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * File-backed repository for AirlineEmployee.
 * Loads/saves employees into airline-employees.json under the runtime data directory.
 */
@Repository
public class AirlineEmployeeFileRepo extends InFileRepository<AirlineEmployee, String> {

    public AirlineEmployeeFileRepo(AppDataProperties props, ResourceLoader resourceLoader) {
        super(
                "airline-employees.json",                        // file name EXACT
                new TypeReference<List<AirlineEmployee>>() {},   // type hint
                AirlineEmployee::getId,                          // id mapping
                props,
                resourceLoader
        );
    }
}
