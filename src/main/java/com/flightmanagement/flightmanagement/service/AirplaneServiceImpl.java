package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AirplaneServiceImpl extends BaseServiceImpl<Airplane, String> implements AirplaneService {

    private final FlightService flightService;

    public AirplaneServiceImpl(AbstractRepository<Airplane, String> repository,
                               FlightService flightService) {
        super(repository);
        this.flightService = flightService;
    }

    @Override
    public List<Airplane> findByMinCapacity(int minCapacity) {
        if (minCapacity < 0) throw new IllegalArgumentException("Minimum capacity cannot be negative");
        return repo().findAll().stream()
                .filter(a -> a.getCapacity() >= minCapacity)
                .toList();
    }

    /**
     * Given a flight ID, return the airplane to which it belongs.
     * Returns [] if the flight is unassigned or the airplane does not exist.
     */
    @Override
    public List<Airplane> findAirplaneForFlight(String flightId) {
        if (flightId == null || flightId.isBlank())
            throw new IllegalArgumentException("Flight ID cannot be null or empty");

        var flightOpt = flightService.findById(flightId);
        if (flightOpt.isEmpty()) return List.of();

        var airplaneId = flightOpt.get().getAirplaneId();
        if (airplaneId == null || airplaneId.isBlank()) return List.of();

        return repo().findById(airplaneId)
                .map(List::of)
                .orElseGet(List::of);
    }

    /**
     * Load airplane and attach its flights (read-only projection).
     */
    @Override
    public Optional<Airplane> findAirplaneWithFlights(String id) {
        return repo().findById(id).map(a -> {
            a.setFlights(flightService.findByAirplaneId(id));
            return a;
        });
    }

    @Override
    public boolean delete(String id) {
        var assigned = flightService.findByAirplaneId(id);
        if (!assigned.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete airplane '" + id + "' because flights are still assigned (" + assigned.size() + ").");
        }
        return super.delete(id);
    }
}
