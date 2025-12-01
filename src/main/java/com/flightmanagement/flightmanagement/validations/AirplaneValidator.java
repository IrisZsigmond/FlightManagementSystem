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

    /** 3) Unique number – use at CREATE / UPDATE */
    public void assertNumberUnique(int number) {
        airplaneRepository.findByNumber(number)
                .ifPresent(a -> {
                    throw new IllegalArgumentException(
                            "Airplane number '" + number + "' is already used by airplane with ID '" + a.getId() + "'.");
                });
    }

    /** 4) Business-Logic for DELETE – FK with Flight */
    public void assertCanBeDeleted(String id) {
        if (flightRepository.existsByAirplane_Id(id)) {
            throw new IllegalStateException(
                    "Cannot delete airplane '" + id + "' because it has assigned flights.");
        }
    }
}
