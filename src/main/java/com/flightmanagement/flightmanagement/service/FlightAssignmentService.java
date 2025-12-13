package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.FlightAssignment;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface FlightAssignmentService {

    FlightAssignment save(FlightAssignment assignment);

    List<FlightAssignment> findAll();

    // ⭐ SORT
    List<FlightAssignment> findAll(Sort sort);

    Optional<FlightAssignment> findById(String id);

    FlightAssignment update(String id, FlightAssignment updated);

    boolean delete(String id);

    List<FlightAssignment> findByFlightId(String flightId);

    List<FlightAssignment> findByAirlineEmployeeId(String employeeId);
}
