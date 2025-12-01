package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.Staff;
import com.flightmanagement.flightmanagement.repository.StaffRepository;
import org.springframework.stereotype.Component;

@Component
public class StaffValidator {

    private final StaffRepository staffRepository;

    public StaffValidator(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    /** ID unic la nivel de TOȚI staff members (Airline + Airport + orice alt subtip) */
    public void assertStaffIdUnique(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Staff id cannot be null or blank");
        }
        if (staffRepository.existsById(id)) {
            throw new IllegalStateException("Staff id already exists: " + id);
        }
    }
}
