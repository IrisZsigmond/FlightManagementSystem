package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.repository.AirlineEmployeeRepository;
import com.flightmanagement.flightmanagement.repository.FlightAssignmentRepository;
import org.springframework.stereotype.Component;

@Component
public class AirlineEmployeeValidator {

    private final AirlineEmployeeRepository employeeRepository;
    private final FlightAssignmentRepository assignmentRepository;
    private final StaffValidator staffValidator;

    public AirlineEmployeeValidator(AirlineEmployeeRepository employeeRepository,
                                    FlightAssignmentRepository assignmentRepository, StaffValidator staffValidator) {
        this.employeeRepository = employeeRepository;
        this.assignmentRepository = assignmentRepository;
        this.staffValidator = staffValidator;
    }

    /** 1) Existență */
    public AirlineEmployee requireExisting(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Airline employee id cannot be null or blank");
        }

        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Airline employee not found: " + id));
    }

    /** 2) ID unic – DOAR la CREATE */
    public void assertIdUnique(String id) {
        staffValidator.assertStaffIdUnique(id);
    }

    /** 3) Regula de DELETE – nu permite dacă are assignment-uri */
    public void assertCanBeDeleted(String id) {
        if (assignmentRepository.existsByAirlineEmployee_Id(id)) {
            throw new IllegalStateException(
                    "Cannot delete airline employee '" + id + "' because they are assigned to flights.");
        }
    }
}
