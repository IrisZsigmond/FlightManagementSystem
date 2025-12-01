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

    /** 1) Existență assignment */
    public FlightAssignment requireExisting(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Flight assignment id cannot be null or blank");
        }

        return assignmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Flight assignment not found: " + id));
    }

    /** 2) ID unic – doar la CREATE */
    public void assertIdUnique(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Flight assignment id cannot be null or blank");
        }
        if (assignmentRepository.existsById(id)) {
            throw new IllegalStateException("Flight assignment id already exists: " + id);
        }
    }

    /** 3) Existență Flight */
    public Flight requireExistingFlight(String flightId) {
        if (flightId == null || flightId.isBlank()) {
            throw new IllegalArgumentException("Flight id cannot be null or blank");
        }
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Flight not found: " + flightId));
    }

    /** 4) Existență AirlineEmployee */
    public AirlineEmployee requireExistingAirlineEmployee(String employeeId) {
        if (employeeId == null || employeeId.isBlank()) {
            throw new IllegalArgumentException("Airline employee id cannot be null or blank");
        }
        return airlineEmployeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Airline employee not found: " + employeeId));
    }

    /** 5a) Unicitate pereche (flight, employee) la CREATE */
    public void assertUniquePairForCreate(String flightId, String employeeId) {
        if (assignmentRepository.existsByFlight_IdAndAirlineEmployee_Id(flightId, employeeId)) {
            throw new IllegalStateException(
                    "This airline employee is already assigned to this flight.");
        }
    }

    /** 5b) Unicitate pereche (flight, employee) la UPDATE – excludem assignment-ul curent */
    public void assertUniquePairForUpdate(String id, String flightId, String employeeId) {
        if (assignmentRepository.existsByFlight_IdAndAirlineEmployee_IdAndIdNot(flightId, employeeId, id)) {
            throw new IllegalStateException(
                    "This airline employee is already assigned to this flight.");
        }
    }

    /** 6) assertCanBeDeleted – momentan nu avem altă entitate care depinde de assignment,
     * deci nu blocăm ștergerea. Lăsăm hook-ul pentru viitor. */
    public void assertCanBeDeleted(String id) {
        // momentan nu facem nimic special
    }
}
