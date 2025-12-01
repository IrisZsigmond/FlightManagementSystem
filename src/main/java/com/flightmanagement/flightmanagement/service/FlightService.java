package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Flight;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface FlightService {
    // Basic CRUD methods:
    Flight save(Flight flight);

    List<Flight> findAll();

    Optional<Flight> findById(String id);

    Flight update(String id, Flight updated);

    boolean delete(String id);

    Flight getById(String id);
    // Custom methods:
    List<Flight> findByAirplaneId(String airplaneId);

    Optional<Flight> findWithTicketsAndAssignments(String id);

    List<Flight> findUnassigned();

    List<Flight> findByNoticeBoardId(String noticeBoardId);

    List<Flight> findByNameContains(String term);

    List<Flight> findByDepartureBetween(LocalTime from, LocalTime to);

    List<Flight> findByTicketId(String ticketId);

    List<Flight> findByStaffId(String staffId);
}
