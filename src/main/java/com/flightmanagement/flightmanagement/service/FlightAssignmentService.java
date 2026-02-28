package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.FlightAssignment;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface FlightAssignmentService {

    // CRUD

    FlightAssignment save(FlightAssignment assignment);

    FlightAssignment update(String id, FlightAssignment updated);

    boolean delete(String id);

    Optional<FlightAssignment> findById(String id);

    List<FlightAssignment> findAll();

    List<FlightAssignment> findAll(Sort sort);

    // SEARCH + SORT

    List<FlightAssignment> search(
            String flightId,
            String airlineEmployeeId,
            Sort sort
    );

    List<FlightAssignment> findByFlightId(String flightId);

    List<FlightAssignment> findByAirlineEmployeeId(String employeeId);
}
