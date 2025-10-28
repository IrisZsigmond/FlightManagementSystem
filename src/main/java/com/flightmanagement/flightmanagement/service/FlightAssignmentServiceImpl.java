package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightAssignmentServiceImpl implements FlightAssignmentService {

    private final AbstractRepository<FlightAssignment, String> repository;

    public FlightAssignmentServiceImpl(AbstractRepository<FlightAssignment, String> repository) {
        this.repository = repository;
    }

    @Override
    public FlightAssignment save(FlightAssignment entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public List<FlightAssignment> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<FlightAssignment> findById(String id) {
        return repository.findById(id);
    }


    @Override
    public FlightAssignment update(String id, FlightAssignment updatedEntity) {
        boolean success = repository.update(id, updatedEntity);
        if (!success) {
            try {
                throw new Exception("FlightAssignment not found: " + id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return updatedEntity;
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }

    @Override
    public boolean existsById(String s) {
        return repository.existsById(s);
    }


    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void clear() {
        repository.clear();
    }

    @Override
    public List<FlightAssignment> findByFlightId(String flightId) {
        if (flightId == null || flightId.isBlank()) {
            throw new IllegalArgumentException("Flight ID cannot be null or empty");
        }
        return repository.findAll().stream()
                .filter(a -> flightId.equalsIgnoreCase(a.getFlightId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightAssignment> findByStaffId(String staffId) {
        if (staffId == null || staffId.isBlank()) {
            throw new IllegalArgumentException("Staff ID cannot be null or empty");
        }
        return repository.findAll().stream()
                .filter(a -> staffId.equalsIgnoreCase(a.getStaffId()))
                .collect(Collectors.toList());
    }
}
