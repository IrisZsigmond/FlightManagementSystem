package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;
import com.flightmanagement.flightmanagement.repository.AirlineEmployeeRepository;
import com.flightmanagement.flightmanagement.validations.AirlineEmployeeValidator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AirlineEmployeeServiceImpl implements AirlineEmployeeService {

    private final AirlineEmployeeRepository repo;
    private final FlightAssignmentService assignmentService;
    private final AirlineEmployeeValidator employeeValidator;

    public AirlineEmployeeServiceImpl(
            AirlineEmployeeRepository repo,
            FlightAssignmentService assignmentService, AirlineEmployeeValidator employeeValidator) {
        this.repo = repo;
        this.assignmentService = assignmentService;
        this.employeeValidator = employeeValidator;
    }

    // ------------------ CRUD -------------------

    // CREATE
    @Override
    public AirlineEmployee save(AirlineEmployee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Airline employee cannot be null");
        }

        employeeValidator.assertIdUnique(employee.getId());

        return repo.save(employee);
    }

    // UPDATE
    @Override
    public AirlineEmployee update(String id, AirlineEmployee updated) {
        if (updated == null) {
            throw new IllegalArgumentException("Updated airline employee cannot be null");
        }

        AirlineEmployee existing = employeeValidator.requireExisting(id);

        existing.setName(updated.getName());
        existing.setRole(updated.getRole());

        return repo.save(existing);
    }

    // DELETE
    @Override
    public boolean delete(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }

        Optional<AirlineEmployee> optional = repo.findById(id);
        if (optional.isEmpty()) {
            return false;
        }

        employeeValidator.assertCanBeDeleted(id);
        repo.deleteById(id);
        return true;
    }

    // READ

    @Override
    @Transactional(readOnly = true)
    public List<AirlineEmployee> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirlineEmployee> findAll(Sort sort) {
        return repo.findAll(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public AirlineEmployee getById(String id) {
        return employeeValidator.requireExisting(id);
    }

    @Override
    public Optional<AirlineEmployee> findById(String id) {
        return repo.findById(id);
    }

    // ------------------ Custom queries -------------------

    @Override
    @Transactional(readOnly = true)
    public List<AirlineEmployee> search(String name, List<AirlineRole> roles, Sort sort) {

        Sort safeSort = (sort != null) ? sort : Sort.by(Sort.Direction.ASC, "id");

        boolean hasName = name != null && !name.trim().isBlank();
        boolean hasRoles = roles != null && !roles.isEmpty();

        if (hasName && hasRoles) {
            return repo.findByNameContainingIgnoreCaseAndRoleIn(name.trim(), roles, safeSort);
        }

        if (hasName) {
            return repo.findByNameContainingIgnoreCase(name.trim(), safeSort);
        }

        if (hasRoles) {
            return repo.findByRoleIn(roles, safeSort);
        }

        return repo.findAll(safeSort);
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
