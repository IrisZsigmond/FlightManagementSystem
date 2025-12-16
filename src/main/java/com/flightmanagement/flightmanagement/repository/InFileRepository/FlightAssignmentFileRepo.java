package com.flightmanagement.flightmanagement.repository.InFileRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.flightmanagement.flightmanagement.config.AppDataProperties;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * File-backed repository for FlightAssignment.
 * Loads/saves flight assignments into flightassignments.json under the runtime data directory.
 */
//@Repository
public class FlightAssignmentFileRepo extends InFileRepository<FlightAssignment, String> {

    public FlightAssignmentFileRepo(AppDataProperties props, ResourceLoader resourceLoader) {
        super(
                "flightassignments.json",
                new TypeReference<List<FlightAssignment>>() {},
                FlightAssignment::getId,
                props,
                resourceLoader
        );
    }
}
