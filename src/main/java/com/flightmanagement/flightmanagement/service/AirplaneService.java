package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;

import java.util.List;
import java.util.Optional;

public interface AirplaneService {

    Airplane save(Airplane airplane);

    List<Airplane> findAll();

    Optional<Airplane> findById(String id);

    Airplane update(String id, Airplane updated);

    boolean delete(String id);

    // --- Custom logic from the old service ---
    List<Airplane> findByMinCapacity(int minCapacity);

    List<Airplane> findAirplaneForFlight(String flightId);

    Optional<Airplane> findAirplaneWithFlights(String id);
}
