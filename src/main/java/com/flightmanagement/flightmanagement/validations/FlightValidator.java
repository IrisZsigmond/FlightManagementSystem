package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.repository.AirplaneRepository;
import com.flightmanagement.flightmanagement.repository.FlightAssignmentRepository;
import com.flightmanagement.flightmanagement.repository.FlightRepository;
import com.flightmanagement.flightmanagement.repository.NoticeBoardRepository;
import org.springframework.stereotype.Component;

@Component
public class FlightValidator {

    private final FlightRepository flightRepository;
    private final AirplaneRepository airplaneRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final FlightAssignmentRepository flightAssignmentRepository;

    public FlightValidator(
            FlightRepository flightRepository,
            AirplaneRepository airplaneRepository,
            NoticeBoardRepository noticeBoardRepository,
            FlightAssignmentRepository flightAssignmentRepository
    ) {
        this.flightRepository = flightRepository;
        this.airplaneRepository = airplaneRepository;
        this.noticeBoardRepository = noticeBoardRepository;
        this.flightAssignmentRepository = flightAssignmentRepository;
    }

    // ---------------------- EXISTENCE ----------------------

    public Flight requireExisting(String id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + id));
    }

    public Airplane requireExistingAirplane(String id) {
        return airplaneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Airplane not found: " + id));
    }

    public NoticeBoard requireExistingNoticeBoard(String id) {
        return noticeBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("NoticeBoard not found: " + id));
    }

    // ---------------------- UNICITATE ----------------------

    public void assertIdUnique(String id) {
        if (flightRepository.existsById(id)) {
            throw new IllegalStateException("Flight ID already exists: " + id);
        }
    }

    public void assertNameUnique(String name) {
        if (flightRepository.existsByName(name)) {
            throw new IllegalStateException("Flight name already exists: " + name);
        }
    }

    public void assertNameUniqueForUpdate(String newName, String currentId) {
        var exists = flightRepository.findAll().stream()
                .anyMatch(f -> f.getName() != null
                        && f.getName().equalsIgnoreCase(newName)
                        && !f.getId().equals(currentId));

        if (exists) {
            throw new IllegalStateException("Another flight already uses name: " + newName);
        }
    }

    // ---------------------- DELETE RULE ----------------------

    public void assertCanBeDeleted(String id) {

        boolean hasTickets = !flightRepository.findById(id)
                .orElseThrow()
                .getTickets()
                .isEmpty();

        boolean hasAssignments =
                !flightAssignmentRepository.findByFlight_Id(id).isEmpty();

        if (hasTickets) {
            throw new IllegalStateException(
                    "Cannot delete flight '" + id + "' because it has assigned tickets."
            );
        }

        if (hasAssignments) {
            throw new IllegalStateException(
                    "Cannot delete flight '" + id + "' because it has flight assignments."
            );
        }
    }
}
