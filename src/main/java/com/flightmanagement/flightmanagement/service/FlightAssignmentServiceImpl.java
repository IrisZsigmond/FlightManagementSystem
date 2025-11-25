package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.repository.FlightAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightAssignmentServiceImpl implements FlightAssignmentService {

    private final FlightAssignmentRepository repo;

    public FlightAssignmentServiceImpl(FlightAssignmentRepository repo) {
        this.repo = repo;
    }

    // ---------------- CRUD ----------------

    @Override
    public FlightAssignment save(FlightAssignment assignment) {
        return repo.save(assignment);
    }

    @Override
    public List<FlightAssignment> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<FlightAssignment> findById(String id) {
        return repo.findById(id);
    }

    @Override
    public FlightAssignment update(String id, FlightAssignment updated) {
        if (!repo.existsById(id))
            throw new IllegalArgumentException("FlightAssignment not found: " + id);

        updated.setId(id);
        return repo.save(updated);
    }

    @Override
    public boolean delete(String id) {
        repo.deleteById(id);
        return true;
    }

    // ---------------- Custom queries ----------------

    @Override
    public List<FlightAssignment> findByFlightId(String flightId) {
        return repo.findByFlight_Id(flightId);
    }

    @Override
    public List<FlightAssignment> findByAirlineEmployeeId(String employeeId) {
        return repo.findByAirlineEmployee_Id(employeeId);
    }
}
