package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.repository.AirplaneRepository;
import com.flightmanagement.flightmanagement.repository.FlightRepository;
import org.springframework.stereotype.Component;

@Component
public class AirplaneValidator {

    private final AirplaneRepository airplaneRepository;
    private final FlightRepository flightRepository;

    public AirplaneValidator(AirplaneRepository airplaneRepository,
                             FlightRepository flightRepository) {
        this.airplaneRepository = airplaneRepository;
        this.flightRepository = flightRepository;
    }

    /** 1) Existence */
    public Airplane requireExisting(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Airplane id cannot be null or blank");
        }

        return airplaneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Airplane not found: " + id));
    }

    /** 2) Unique ID – use at CREATE */
    public void assertIdUnique(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Airplane id cannot be null or blank");
        }
        if (airplaneRepository.existsById(id)) {
            throw new IllegalStateException("Airplane id already exists: " + id);
        }
    }

    /** 3a) Unique number – CREATE */
    public void assertNumberUniqueForCreate(int number) {
        if (airplaneRepository.existsByNumber(number)) {
            throw new IllegalArgumentException(
                    "Airplane number '" + number + "' is already used.");
        }
    }

    /**
     * 3b) Unique number – UPDATE (excludem avionul curent).
     * currentId = id-ul avionului pe care îl edităm.
     */
    public void assertNumberUniqueForUpdate(int number, String currentId) {
        if (currentId == null || currentId.isBlank()) {
            // fallback defensiv – ne comportăm ca la create
            assertNumberUniqueForCreate(number);
            return;
        }

        if (airplaneRepository.existsByNumberAndIdNot(number, currentId)) {
            throw new IllegalArgumentException(
                    "Airplane number '" + number + "' is already used by a different airplane.");
        }
    }

    /** 4) Business-Logic for DELETE – FK with Flight */
    public void assertCanBeDeleted(String id) {
        if (flightRepository.existsByAirplane_Id(id)) {
            throw new IllegalStateException(
                    "Cannot delete airplane '" + id + "' because it has assigned flights.");
        }
    }
}
