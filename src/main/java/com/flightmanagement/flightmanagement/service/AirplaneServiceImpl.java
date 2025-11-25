package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.repository.AirplaneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirplaneServiceImpl implements AirplaneService {

    private final AirplaneRepository airplaneRepository;
    private final FlightService flightService;

    public AirplaneServiceImpl(AirplaneRepository airplaneRepository,
                               FlightService flightService) {
        this.airplaneRepository = airplaneRepository;
        this.flightService = flightService;
    }

    // ---------------- CRUD ----------------

    @Override
    public Airplane save(Airplane airplane) {
        if (airplane == null)
            throw new IllegalArgumentException("Airplane cannot be null");
        return airplaneRepository.save(airplane);
    }

    @Override
    public List<Airplane> findAll() {
        return airplaneRepository.findAll();
    }

    @Override
    public Optional<Airplane> findById(String id) {
        return airplaneRepository.findById(id);
    }

    @Override
    public Airplane update(String id, Airplane updated) {
        if (!airplaneRepository.existsById(id))
            throw new IllegalArgumentException("Airplane not found: " + id);

        updated.setId(id);
        return airplaneRepository.save(updated);
    }

    @Override
    public boolean delete(String id) {
        List<Flight> assignedFlights = flightService.findByAirplaneId(id);
        if (!assignedFlights.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete airplane '" + id + "' because it has " +
                            assignedFlights.size() + " assigned flights."
            );
        }

        airplaneRepository.deleteById(id);
        return true;
    }

    // ---------------- CUSTOM LOGIC ----------------

    @Override
    public List<Airplane> findByMinCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new IllegalArgumentException("Minimum capacity cannot be negative");

        return airplaneRepository.findAll().stream()
                .filter(a -> a.getCapacity() >= minCapacity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Airplane> findAirplaneForFlight(String flightId) {
        return flightService.findById(flightId)
                .flatMap(flight -> Optional.ofNullable(flight.getAirplane()))
                .map(List::of)
                .orElse(List.of());
    }

    @Override
    public Optional<Airplane> findAirplaneWithFlights(String id) {
        return airplaneRepository.findById(id).map(a -> {
            a.setFlights(flightService.findByAirplaneId(id));
            return a;
        });
    }
}
