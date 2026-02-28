package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.repository.AirlineEmployeeRepository;
import com.flightmanagement.flightmanagement.repository.FlightAssignmentRepository;
import com.flightmanagement.flightmanagement.repository.FlightRepository;
import org.springframework.stereotype.Component;

@Component
public class FlightAssignmentValidator {

    private final FlightAssignmentRepository assignmentRepository;
    private final FlightRepository flightRepository;
    private final AirlineEmployeeRepository airlineEmployeeRepository;

    public FlightAssignmentValidator(FlightAssignmentRepository assignmentRepository,
                                     FlightRepository flightRepository,
                                     AirlineEmployeeRepository airlineEmployeeRepository) {
        this.assignmentRepository = assignmentRepository;
        this.flightRepository = flightRepository;
        this.airlineEmployeeRepository = airlineEmployeeRepository;
    }

    public FlightAssignment requireExisting(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Flight assignment id cannot be null or blank");
        }

        return assignmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Flight assignment not found: " + id));
    }

    public void assertIdUnique(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Flight assignment id cannot be null or blank");
        }
        if (assignmentRepository.existsById(id)) {
            throw new IllegalStateException("Flight assignment id already exists: " + id);
        }
    }

    public Flight requireExistingFlight(String flightId) {
        if (flightId == null || flightId.isBlank()) {
            throw new IllegalArgumentException("Flight id cannot be null or blank");
        }
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Flight not found: " + flightId));
    }

    public AirlineEmployee requireExistingAirlineEmployee(String employeeId) {
        if (employeeId == null || employeeId.isBlank()) {
            throw new IllegalArgumentException("Airline employee id cannot be null or blank");
        }
        return airlineEmployeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Airline employee not found: " + employeeId));
    }

    public void assertUniquePairForCreate(String flightId, String employeeId) {
        if (assignmentRepository.existsByFlight_IdAndAirlineEmployee_Id(flightId, employeeId)) {
            throw new IllegalStateException(
                    "This airline employee is already assigned to this flight.");
        }
    }

    public void assertUniquePairForUpdate(String id, String flightId, String employeeId) {
        if (assignmentRepository.existsByFlight_IdAndAirlineEmployee_IdAndIdNot(flightId, employeeId, id)) {
            throw new IllegalStateException(
                    "This airline employee is already assigned to this flight.");
        }
    }

    public void assertCanBeDeleted(String id) {
        // to be implemented if needed
    }
}
