package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Flight;
import org.springframework.data.domain.Sort;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface FlightService {
    // CRUD
    Flight save(Flight flight);

    List<Flight> findAll();


    Flight update(String id, Flight updated);

    boolean delete(String id);

    Flight getById(String id);

    // Sorting and Filtering
    List<Flight> findAll(Sort sort);

    List<Flight> findByNameContainingIgnoreCase(String name, Sort sort);

    List<Flight> findByDepartureTimeBetween(LocalTime startTime, LocalTime endTime, Sort sort);

    List<Flight> findByNameContainingIgnoreCaseAndDepartureTimeBetween(String name, LocalTime startTime, LocalTime endTime, Sort sort);

    List<Flight> search(String name, LocalTime startTime, LocalTime endTime, Sort sort);

    // Custom methods:
    Optional<Flight> findById(String id);

    List<Flight> findByNoticeBoardId(String noticeBoardId);
}
