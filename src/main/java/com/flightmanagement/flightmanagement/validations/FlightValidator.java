package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.repository.AirplaneRepository;
import com.flightmanagement.flightmanagement.repository.FlightRepository;
import com.flightmanagement.flightmanagement.repository.NoticeBoardRepository;
import org.springframework.stereotype.Component;

@Component
public class FlightValidator {

    private final FlightRepository flightRepository;
    private final AirplaneRepository airplaneRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    public FlightValidator(FlightRepository flightRepository,
                           AirplaneRepository airplaneRepository,
                           NoticeBoardRepository noticeBoardRepository) {
        this.flightRepository = flightRepository;
        this.airplaneRepository = airplaneRepository;
        this.noticeBoardRepository = noticeBoardRepository;
    }

    /** 1) Existence */
    public Flight requireExisting(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Flight id cannot be null or blank");
        }

        return flightRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + id));
    }

    /** 2) Unique ID Flight – use at CREATE */
    public void assertIdUnique(String id) {
        if (flightRepository.existsById(id)) {
            throw new IllegalStateException("Flight id already exists: " + id);
        }
    }

    /** 3) Unique name Flight - use at CREATE / EDIT */
    public void assertNameUnique(String name) {
        if (flightRepository.existsByName(name)) {
            throw new IllegalStateException("Flight name already exists: " + name);
        }
    }

    /** 4) FK: Airplane needs to exist */
    public Airplane requireExistingAirplane(String airplaneId) {
        if (airplaneId == null || airplaneId.isBlank()) {
            throw new IllegalArgumentException("Airplane id cannot be null or blank");
        }

        return airplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Airplane not found for flight: " + airplaneId));
    }

    /** 5) FK: NoticeBoard needs to exist */
    public NoticeBoard requireExistingNoticeBoard(String noticeBoardId) {
        if (noticeBoardId == null || noticeBoardId.isBlank()) {
            throw new IllegalArgumentException("NoticeBoard id cannot be null or blank");
        }

        return noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "NoticeBoard not found for flight: " + noticeBoardId));
    }

    /** 6) Reguli de delete – aici ulterior poți verifica tickets, assignments etc. */
    public void assertCanBeDeleted(String id) {
        // deocamdată nu ai Tickets/Assignments implementate,
        // dar aici va fi locul: dacă există tickets -> interzici delete.
    }

    /** 7) Reguli de business legate de Airplane (availability) – placeholder */
    // public void assertAirplaneAvailableFor(...){ ... }
}
