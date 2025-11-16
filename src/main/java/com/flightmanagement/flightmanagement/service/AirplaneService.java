package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;

import java.util.List;
import java.util.Optional;

public interface AirplaneService extends BaseService<Airplane, String> {
    /**
     * Finds all airplanes with a capacity greater than or equal to the specified value.
     *
     * @param minCapacity minimum capacity
     * @return list of airplanes meeting the capacity requirement
     */
    List<Airplane> findByMinCapacity(int minCapacity);

    /**
     * Finds all airplanes assigned to a specific flight.
     *
     * @param flightId the flight identifier
     * @return list of airplanes containing that flight
     */
    List<Airplane> findAirplaneForFlight(String flightId);

    /**
     * Load airplane and attach its flights (read-only projection).
     */
    Optional<Airplane> findAirplaneWithFlights(String id);
}
