package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;

import java.util.List;
import java.util.Optional;

public interface AirplaneService {

    // Basic CRUD methods:
    Airplane save(Airplane airplane);

    List<Airplane> findAll();

    Airplane update(String id, Airplane updated);

    boolean delete(String id);

    Optional<Airplane> findById(String id);

    Airplane getById(String id);

    // Custom methods:
    List<Airplane> findByMinCapacity(int minCapacity);

    List<Airplane> findAirplaneForFlight(String flightId);

    Optional<Airplane> findAirplaneWithFlights(String id);
}
