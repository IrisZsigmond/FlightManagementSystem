package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Arrays;

@Service
public class AirplaneServiceImpl extends BaseServiceImpl<Airplane, String> implements AirplaneService {

    private final FlightService flightService;

    public AirplaneServiceImpl(AbstractRepository<Airplane, String> repository,
                               FlightService flightService) {
        super(repository);
        this.flightService = flightService;
    }

    /* ---------------- CSV -> List<Flight> helper ---------------- */

    private List<Flight> resolveFlightsFromCsv(String csv) {
        if (csv == null || csv.isBlank()) return new ArrayList<>();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(id -> flightService.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void applyCsvIfPresent(Airplane airplane) {
        // Only recompute from CSV if user actually used the CSV field.
        // You can make this unconditional if that’s your desired behavior.
        if (airplane.getFlightsCsv() != null) {
            airplane.setFlights(resolveFlightsFromCsv(airplane.getFlightsCsv()));
        }
    }

    /* ---------------- CRUD overrides to hook CSV mapping ---------------- */

    @Override
    public Airplane save(Airplane airplane) {
        applyCsvIfPresent(airplane);
        return super.save(airplane);
    }

    @Override
    public Airplane update(String id, Airplane airplane) {
        Airplane existing = repo().findById(id).orElseThrow();

        existing.setNumber(airplane.getNumber());
        existing.setCapacity(airplane.getCapacity());

        // Recompute flights from CSV provided in the edit form
        if (airplane.getFlightsCsv() != null) {
            existing.setFlights(resolveFlightsFromCsv(airplane.getFlightsCsv()));
        }

        return repo().save(existing);
    }

    /* ---------------- Query helpers ---------------- */

    @Override
    public List<Airplane> findByMinCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new IllegalArgumentException("Minimum capacity cannot be negative");
        }
        return repo().findAll().stream()
                .filter(a -> a.getCapacity() >= minCapacity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Airplane> findByFlightId(String flightId) {
        if (flightId == null || flightId.isBlank()) {
            throw new IllegalArgumentException("Flight ID cannot be null or empty");
        }
        return repo().findAll().stream()
                .filter(a -> a.getFlights() != null &&
                        a.getFlights().stream().anyMatch(f -> flightId.equals(f.getId())))
                .collect(Collectors.toList());
    }
}
