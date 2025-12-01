package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.repository.FlightAssignmentRepository;
import com.flightmanagement.flightmanagement.validations.FlightAssignmentValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlightAssignmentServiceImpl implements FlightAssignmentService {

    private final FlightAssignmentRepository repo;
    private final FlightAssignmentValidator validator;

    public FlightAssignmentServiceImpl(FlightAssignmentRepository repo,
                                       FlightAssignmentValidator validator) {
        this.repo = repo;
        this.validator = validator;
    }

    // ---------------- CRUD ----------------

    // CREATE
    @Override
    public FlightAssignment save(FlightAssignment assignment) {
        if (assignment == null) {
            throw new IllegalArgumentException("Flight assignment cannot be null");
        }

        // ID unic
        validator.assertIdUnique(assignment.getId());

        // extragem ID-urile din entitate (mapperul trebuie să le seteze)
        String flightId = assignment.getFlight() != null ? assignment.getFlight().getId() : null;
        String employeeId = assignment.getAirlineEmployee() != null ? assignment.getAirlineEmployee().getId() : null;

        // existență Flight + Employee
        Flight flight = validator.requireExistingFlight(flightId);
        AirlineEmployee employee = validator.requireExistingAirlineEmployee(employeeId);

        // unicitate pereche
        validator.assertUniquePairForCreate(flight.getId(), employee.getId());

        // reasociem entity-urile „reale” (managed) pe assignment
        assignment.setFlight(flight);
        assignment.setAirlineEmployee(employee);

        return repo.save(assignment);
    }

    // READ
    @Override
    @Transactional(readOnly = true)
    public List<FlightAssignment> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FlightAssignment> findById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }
        return repo.findById(id);
    }

    // UPDATE
    @Override
    public FlightAssignment update(String id, FlightAssignment updated) {
        if (updated == null) {
            throw new IllegalArgumentException("Updated flight assignment cannot be null");
        }

        // asigurăm existența assignment-ului
        FlightAssignment existing = validator.requireExisting(id);

        // extragem ID-urile noii asocieri
        String flightId = updated.getFlight() != null ? updated.getFlight().getId() : null;
        String employeeId = updated.getAirlineEmployee() != null ? updated.getAirlineEmployee().getId() : null;

        // existență Flight + Employee
        Flight flight = validator.requireExistingFlight(flightId);
        AirlineEmployee employee = validator.requireExistingAirlineEmployee(employeeId);

        // unicitate pereche (excludem assignment-ul curent)
        validator.assertUniquePairForUpdate(id, flight.getId(), employee.getId());

        // nu schimbăm ID-ul, doar câmpurile
        existing.setFlight(flight);
        existing.setAirlineEmployee(employee);

        return repo.save(existing);
    }

    // DELETE
    @Override
    public boolean delete(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }

        // verificăm existența; aruncă dacă nu există
        validator.requireExisting(id);

        // eventuale reguli extra
        validator.assertCanBeDeleted(id);

        repo.deleteById(id);
        return true;
    }

    // ---------------- Custom queries ----------------

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
