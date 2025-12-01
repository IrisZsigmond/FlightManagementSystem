package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.repository.AirplaneRepository;
import com.flightmanagement.flightmanagement.validations.AirplaneValidator;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AirplaneServiceImpl implements AirplaneService {

    private final AirplaneRepository airplaneRepository;
    private final AirplaneValidator airplaneValidator;
    private final FlightService flightService;

    public AirplaneServiceImpl(AirplaneRepository airplaneRepository,
                               AirplaneValidator airplaneValidator, FlightService flightService) {
        this.airplaneRepository = airplaneRepository;
        this.airplaneValidator = airplaneValidator;
        this.flightService = flightService;
    }

    // ---------------- CRUD ----------------

    // CREATE
    @Override
    public Airplane save(Airplane airplane) {
        if (airplane == null) {
            throw new IllegalArgumentException("Airplane cannot be null");
        }

        // ID unic
        airplaneValidator.assertIdUnique(airplane.getId());

        // Number unic - CREATE
        airplaneValidator.assertNumberUniqueForCreate(airplane.getNumber());

        return airplaneRepository.save(airplane);
    }

    // UPDATE
    @Override
    public Airplane update(String id, Airplane updated) {
        if (updated == null) {
            throw new IllegalArgumentException("Updated airplane cannot be null");
        }

        // există avionul?
        Airplane existing = airplaneValidator.requireExisting(id);

        // verificăm number unic, excluzând avionul curent
        airplaneValidator.assertNumberUniqueForUpdate(updated.getNumber(), id);

        // actualizăm doar câmpurile editabile
        existing.setNumber(updated.getNumber());
        existing.setCapacity(updated.getCapacity());

        return airplaneRepository.save(existing);
    }



    // READ
    @Override
    @Transactional(readOnly = true)
    public List<Airplane> findAll() {
        return airplaneRepository.findAll();
    }

    // DELETE
    @Override
    public boolean delete(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }

        Optional<Airplane> optional = airplaneRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        airplaneValidator.assertCanBeDeleted(id);
        airplaneRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Airplane getById(String id) {
        return airplaneValidator.requireExisting(id);
    }

    @Override
    public Optional<Airplane> findById(String id) {
        return airplaneRepository.findById(id);
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
