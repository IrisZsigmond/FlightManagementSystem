package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.repository.FlightRepository;
import com.flightmanagement.flightmanagement.validations.FlightValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightValidator validator;

    public FlightServiceImpl(
            FlightRepository flightRepository,
            FlightValidator validator
    ) {
        this.flightRepository = flightRepository;
        this.validator = validator;
    }

    // CREATE
    @Override
    public Flight save(Flight flight) {

        if (flight == null) {
            throw new IllegalArgumentException("Flight cannot be null");
        }

        validator.assertIdUnique(flight.getId());
        validator.assertNameUnique(flight.getName());

        // airplane must exist
        String airplaneId = flight.getAirplane() != null ? flight.getAirplane().getId() : null;
        Airplane airplane = validator.requireExistingAirplane(airplaneId);

        // noticeBoard must exist
        String boardId = flight.getNoticeBoard() != null ? flight.getNoticeBoard().getId() : null;
        NoticeBoard board = validator.requireExistingNoticeBoard(boardId);

        flight.setAirplane(airplane);
        flight.setNoticeBoard(board);

        return flightRepository.save(flight);
    }

    // READ
    @Override
    public Optional<Flight> findById(String id) {
        return flightRepository.findById(id);
    }

    @Override
    public Flight getById(String id) {
        return validator.requireExisting(id);
    }

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    // UPDATE
    @Override
    public Flight update(String id, Flight updated) {
        Flight existing = validator.requireExisting(id);

        updated.setId(id);

        // name unique?
        if (updated.getName() != null) {
            validator.assertNameUniqueForUpdate(updated.getName(), id);
        }

        // airplane
        String newAirplaneId = updated.getAirplane().getId();
        Airplane airplane = validator.requireExistingAirplane(newAirplaneId);

        // noticeBoard
        String newBoardId = updated.getNoticeBoard().getId();
        NoticeBoard board = validator.requireExistingNoticeBoard(newBoardId);

        existing.setName(updated.getName());
        existing.setDepartureTime(updated.getDepartureTime());
        existing.setAirplane(airplane);
        existing.setNoticeBoard(board);

        return flightRepository.save(existing);
    }

    // DELETE
    @Override
    public boolean delete(String id) {

        validator.requireExisting(id);
        validator.assertCanBeDeleted(id);

        flightRepository.deleteById(id);
        return true;
    }

    // CUSTOM BELOW
    @Override
    public List<Flight> findByAirplaneId(String airplaneId) {
        return flightRepository.findByAirplane_Id(airplaneId);
    }

    @Override
    public Optional<Flight> findWithTicketsAndAssignments(String id) {
        return flightRepository.findById(id);
    }

    @Override
    public List<Flight> findUnassigned() {
        return flightRepository.findAll().stream()
                .filter(f -> f.getAirplane() == null)
                .toList();
    }

    @Override
    public List<Flight> findByNoticeBoardId(String noticeBoardId) {
        return flightRepository.findAll().stream()
                .filter(f -> f.getNoticeBoard() != null &&
                        noticeBoardId.equals(f.getNoticeBoard().getId()))
                .toList();
    }

    @Override
    public List<Flight> findByNameContains(String term) {
        if (term == null || term.isBlank()) return List.of();
        String search = term.toLowerCase();
        return flightRepository.findAll().stream()
                .filter(f -> f.getName() != null &&
                        f.getName().toLowerCase().contains(search))
                .toList();
    }

    @Override
    public List<Flight> findByDepartureBetween(LocalTime from, LocalTime to) {
        return flightRepository.findAll().stream()
                .filter(f -> f.getDepartureTime() != null &&
                        !f.getDepartureTime().isBefore(from) &&
                        !f.getDepartureTime().isAfter(to))
                .toList();
    }

    @Override
    public List<Flight> findByTicketId(String ticketId) {
        return flightRepository.findAll(); // handled in controller if needed
    }

    @Override
    public List<Flight> findByStaffId(String staffId) {
        return List.of(); // depends on assignment service (not included here)
    }
}
