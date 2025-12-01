package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import com.flightmanagement.flightmanagement.repository.AirportEmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class AirportEmployeeValidator {

    private final AirportEmployeeRepository repo;

    public AirportEmployeeValidator(AirportEmployeeRepository repo) {
        this.repo = repo;
    }

    /** 1) Existență */
    public AirportEmployee requireExisting(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Airport employee id cannot be null or blank");
        }

        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Airport employee not found: " + id));
    }

    /** 2) ID unic – doar la CREATE */
    public void assertIdUnique(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Airport employee id cannot be null or blank");
        }
        if (repo.existsById(id)) {
            throw new IllegalStateException("Airport employee id already exists: " + id);
        }
    }

    /** 3) Reguli pentru DELETE – momentan nu avem FK-uri, dar lăsăm hook-ul */
    public void assertCanBeDeleted(String id) {
        // dacă în viitor vei avea entități care depind de AirportEmployee,
        // adaugi aici verificările (existsByAirportEmployee_Id etc.)
    }
}
