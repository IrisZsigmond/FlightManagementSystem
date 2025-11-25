package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;
import com.flightmanagement.flightmanagement.repository.AirlineEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AirlineEmployeeServiceImpl implements AirlineEmployeeService {

    private final AirlineEmployeeRepository repo;
    private final FlightAssignmentService assignmentService;

    public AirlineEmployeeServiceImpl(
            AirlineEmployeeRepository repo,
            FlightAssignmentService assignmentService) {
        this.repo = repo;
        this.assignmentService = assignmentService;
    }

    // ------------------ CRUD -------------------

    @Override
    public AirlineEmployee save(AirlineEmployee employee) {
        return repo.save(employee);
    }

    @Override
    public List<AirlineEmployee> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<AirlineEmployee> findById(String id) {
        return repo.findById(id);
    }

    @Override
    public AirlineEmployee update(String id, AirlineEmployee updated) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Airline employee not found: " + id);
        }
        updated.setId(id);
        return repo.save(updated);
    }

    @Override
    public boolean delete(String id) {
        List<FlightAssignment> assignments = assignmentService.findByAirlineEmployeeId(id);

        if (!assignments.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete airline employee '" + id +
                            "' because assignments still exist (" + assignments.size() + ").");
        }

        repo.deleteById(id);
        return true;
    }

    // ------------------ Custom queries -------------------

    @Override
    public List<AirlineEmployee> findByRole(AirlineRole role) {
        return repo.findByRole(role);
    }

    @Override
    public List<AirlineEmployee> findByAnyRole(Set<AirlineRole> roles) {
        return repo.findByRoleIn(roles.stream().toList());
    }

    @Override
    public List<AirlineEmployee> findByAssignmentId(String assignmentId) {
        Optional<FlightAssignment> assignment = assignmentService.findById(assignmentId);

        return assignment
                .map(a -> repo.findById(a.getAirlineEmployee().getId())
                        .map(List::of)
                        .orElse(List.of()))
                .orElse(List.of());
    }

    @Override
    public Optional<AirlineEmployee> findWithAssignments(String id) {
        return repo.findById(id).map(e -> {
            e.setAssignments(assignmentService.findByAirlineEmployeeId(id));
            return e;
        });
    }
}
