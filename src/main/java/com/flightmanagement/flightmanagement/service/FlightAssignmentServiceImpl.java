package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.repository.FlightAssignmentRepository;
import com.flightmanagement.flightmanagement.validations.FlightAssignmentValidator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlightAssignmentServiceImpl implements FlightAssignmentService {

    private final FlightAssignmentRepository repo;
    private final FlightAssignmentValidator validator;

    public FlightAssignmentServiceImpl(
            FlightAssignmentRepository repo,
            FlightAssignmentValidator validator
    ) {
        this.repo = repo;
        this.validator = validator;
    }

    // ---------------- CREATE ----------------

    @Override
    public FlightAssignment save(FlightAssignment assignment) {
        if (assignment == null) {
            throw new IllegalArgumentException("Flight assignment cannot be null");
        }

        validator.assertIdUnique(assignment.getId());

        String flightId = assignment.getFlight() != null
                ? assignment.getFlight().getId()
                : null;

        String employeeId = assignment.getAirlineEmployee() != null
                ? assignment.getAirlineEmployee().getId()
                : null;

        Flight flight = validator.requireExistingFlight(flightId);
        AirlineEmployee employee = validator.requireExistingAirlineEmployee(employeeId);

        validator.assertUniquePairForCreate(flight.getId(), employee.getId());

        assignment.setFlight(flight);
        assignment.setAirlineEmployee(employee);

        return repo.save(assignment);
    }

    // ---------------- READ ----------------

    @Override
    @Transactional(readOnly = true)
    public List<FlightAssignment> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightAssignment> findAll(Sort sort) {
        return repo.findAll(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FlightAssignment> findById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }
        return repo.findById(id);
    }

    // ---------------- UPDATE ----------------

    @Override
    public FlightAssignment update(String id, FlightAssignment updated) {
        if (updated == null) {
            throw new IllegalArgumentException("Updated flight assignment cannot be null");
        }

        FlightAssignment existing = validator.requireExisting(id);

        String flightId = updated.getFlight() != null
                ? updated.getFlight().getId()
                : null;

        String employeeId = updated.getAirlineEmployee() != null
                ? updated.getAirlineEmployee().getId()
                : null;

        Flight flight = validator.requireExistingFlight(flightId);
        AirlineEmployee employee = validator.requireExistingAirlineEmployee(employeeId);

        validator.assertUniquePairForUpdate(id, flight.getId(), employee.getId());

        existing.setFlight(flight);
        existing.setAirlineEmployee(employee);

        return repo.save(existing);
    }

    // ---------------- DELETE ----------------

    @Override
    public boolean delete(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }

        validator.requireExisting(id);
        validator.assertCanBeDeleted(id);

        repo.deleteById(id);
        return true;
    }

    // ---------------- SEARCH + SORT ----------------

    @Override
    @Transactional(readOnly = true)
    public List<FlightAssignment> search(
            String flightId,
            String airlineEmployeeId,
            Sort sort
    ) {
        Sort safeSort = (sort != null)
                ? sort
                : Sort.by(Sort.Direction.ASC, "id");

        boolean hasFlight = flightId != null && !flightId.isBlank();
        boolean hasEmployee = airlineEmployeeId != null && !airlineEmployeeId.isBlank();

        if (hasFlight && hasEmployee) {
            return repo.findByFlight_IdAndAirlineEmployee_Id(
                    flightId,
                    airlineEmployeeId,
                    safeSort
            );
        }

        if (hasFlight) {
            return repo.findByFlight_Id(flightId, safeSort);
        }

        if (hasEmployee) {
            return repo.findByAirlineEmployee_Id(airlineEmployeeId, safeSort);
        }

        return repo.findAll(safeSort);
    }

    // ---------------- Existing helpers ----------------

    @Override
    @Transactional(readOnly = true)
    public List<FlightAssignment> findByFlightId(String flightId) {
        if (flightId == null || flightId.isBlank()) return List.of();
        return repo.findByFlight_Id(flightId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightAssignment> findByAirlineEmployeeId(String employeeId) {
        if (employeeId == null || employeeId.isBlank()) return List.of();
        return repo.findByAirlineEmployee_Id(employeeId);
    }
}
