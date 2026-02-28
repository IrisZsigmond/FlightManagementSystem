package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import com.flightmanagement.flightmanagement.repository.AirportEmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class AirportEmployeeValidator {

    private final AirportEmployeeRepository repo;
    private final StaffValidator staffValidator;

    public AirportEmployeeValidator(AirportEmployeeRepository repo, StaffValidator staffValidator) {
        this.repo = repo;
        this.staffValidator = staffValidator;
    }

    public AirportEmployee requireExisting(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Airport employee id cannot be null or blank");
        }

        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Airport employee not found: " + id));
    }

    public void assertIdUnique(String id) {
        staffValidator.assertStaffIdUnique(id);
    }

    public void assertCanBeDeleted(String id) {
        // if future entities depend on airport employees, implement here
    }
}
