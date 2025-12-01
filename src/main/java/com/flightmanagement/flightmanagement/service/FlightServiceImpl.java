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
import java.util.stream.Collectors;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightValidator flightValidator;
    private final TicketService ticketService;
    private final FlightAssignmentService flightAssignmentService;

    public FlightServiceImpl(FlightRepository flightRepository,
                             FlightValidator flightValidator,
                             TicketService ticketService,
                             FlightAssignmentService flightAssignmentService) {
        this.flightRepository = flightRepository;
        this.flightValidator = flightValidator;
        this.ticketService = ticketService;
        this.flightAssignmentService = flightAssignmentService;
    }

    // -------------------- CRUD --------------------

    // CREATE – primește direct Flight (entitate)
    @Override
    public Flight save(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Flight cannot be null");
        }

        // ID unic
        flightValidator.assertIdUnique(flight.getId());

        // dacă vrei și unicitate pe name, decomentezi:
        // if (flight.getName() != null) {
        //     flightValidator.assertNameUnique(flight.getName());
        // }

        // Validăm și încărcăm Airplane
        if (flight.getAirplane() == null ||
                flight.getAirplane().getId() == null ||
                flight.getAirplane().getId().isBlank()) {
            throw new IllegalArgumentException("Airplane must be provided for the flight");
        }
        Airplane airplane = flightValidator.requireExistingAirplane(flight.getAirplane().getId());

        // Validăm și încărcăm NoticeBoard
        if (flight.getNoticeBoard() == null ||
                flight.getNoticeBoard().getId() == null ||
                flight.getNoticeBoard().getId().isBlank()) {
            throw new IllegalArgumentException("NoticeBoard must be provided for the flight");
        }
        NoticeBoard noticeBoard = flightValidator.requireExistingNoticeBoard(flight.getNoticeBoard().getId());

        // (opțional) verificare de disponibilitate a avionului:
        // flightValidator.assertAirplaneAvailableFor(
        //     airplane.getId(), noticeBoard.getDate(), flight.getDepartureTime()
        // );

        // Setăm entitățile "managed" în Flight
        flight.setAirplane(airplane);
        flight.setNoticeBoard(noticeBoard);

        return flightRepository.save(flight);
    }

    // READ
    @Override
    @Transactional(readOnly = true)
    public Flight getById(String id) {
        return flightValidator.requireExisting(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Flight> findById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }
        return flightRepository.findById(id);
    }

    // UPDATE – primește Flight (entitate)
    @Override
    public Flight update(String id, Flight updated) {
        if (updated == null) {
            throw new IllegalArgumentException("Updated flight cannot be null");
        }

        Flight existing = flightValidator.requireExisting(id);

        // Nu modificăm ID-ul – îl forțăm să fie cel din path
        updated.setId(id);

        // Dacă se schimbă name și ai regulă de unicitate:
        if (updated.getName() != null &&
                (existing.getName() == null || !existing.getName().equals(updated.getName()))) {
            // de decomentat doar dacă ai nevoie de unicitate pe nume:
            // flightValidator.assertNameUnique(updated.getName());
        }

        // --- Airplane ---
        if (updated.getAirplane() == null ||
                updated.getAirplane().getId() == null ||
                updated.getAirplane().getId().isBlank()) {
            throw new IllegalArgumentException("Airplane must be provided for the flight");
        }

        String newAirplaneId = updated.getAirplane().getId();
        if (existing.getAirplane() == null ||
                !existing.getAirplane().getId().equals(newAirplaneId)) {
            Airplane airplane = flightValidator.requireExistingAirplane(newAirplaneId);
            // (opțional) alte verificări de business pentru schimbarea avionului
            existing.setAirplane(airplane);
        }

        // --- NoticeBoard ---
        if (updated.getNoticeBoard() == null ||
                updated.getNoticeBoard().getId() == null ||
                updated.getNoticeBoard().getId().isBlank()) {
            throw new IllegalArgumentException("NoticeBoard must be provided for the flight");
        }

        String newNoticeBoardId = updated.getNoticeBoard().getId();
        if (existing.getNoticeBoard() == null ||
                !existing.getNoticeBoard().getId().equals(newNoticeBoardId)) {
            NoticeBoard noticeBoard = flightValidator.requireExistingNoticeBoard(newNoticeBoardId);
            existing.setNoticeBoard(noticeBoard);
        }

        // Câmpuri simple
        existing.setName(updated.getName());
        existing.setDepartureTime(updated.getDepartureTime());

        return flightRepository.save(existing);
    }

    // DELETE
    @Override
    public boolean delete(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }

        Optional<Flight> optional = flightRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        flightValidator.assertCanBeDeleted(id);
        flightRepository.deleteById(id);
        return true;
    }

    // ---------------- CUSTOM METHODS ----------------

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findByAirplaneId(String airplaneId) {
        if (airplaneId == null || airplaneId.isBlank()) return List.of();
        return flightRepository.findAll().stream()
                .filter(f -> f.getAirplane() != null
                        && airplaneId.equalsIgnoreCase(f.getAirplane().getId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Flight> findWithTicketsAndAssignments(String id) {
        return flightRepository.findById(id).map(f -> {
            f.setTickets(ticketService.findByFlightId(id));
            f.setFlightAssignments(flightAssignmentService.findByFlightId(id));
            return f;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findUnassigned() {
        return flightRepository.findAll().stream()
                .filter(f -> f.getAirplane() == null
                        || f.getAirplane().getId() == null
                        || f.getAirplane().getId().isBlank())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findByNoticeBoardId(String noticeBoardId) {
        if (noticeBoardId == null || noticeBoardId.isBlank()) return List.of();
        return flightRepository.findAll().stream()
                .filter(f -> f.getNoticeBoard() != null
                        && noticeBoardId.equalsIgnoreCase(f.getNoticeBoard().getId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findByNameContains(String term) {
        if (term == null || term.isBlank()) return List.of();
        String needle = term.toLowerCase();
        return flightRepository.findAll().stream()
                .filter(f -> f.getName() != null
                        && f.getName().toLowerCase().contains(needle))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findByDepartureBetween(LocalTime from, LocalTime to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Times must not be null");
        }
        return flightRepository.findAll().stream()
                .filter(f -> f.getDepartureTime() != null
                        && !f.getDepartureTime().isBefore(from)
                        && f.getDepartureTime().isBefore(to))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findByTicketId(String ticketId) {
        if (ticketId == null || ticketId.isBlank()) return List.of();

        return flightRepository.findAll().stream()
                .filter(f -> ticketService.findByFlightId(f.getId())
                        .stream()
                        .anyMatch(t -> t != null && ticketId.equals(t.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findByStaffId(String staffId) {
        if (staffId == null || staffId.isBlank()) return List.of();

        return flightRepository.findAll().stream()
                .filter(f -> flightAssignmentService.findByFlightId(f.getId())
                        .stream()
                        .anyMatch(a -> a != null
                                && a.getAirlineEmployee() != null
                                && staffId.equals(a.getAirlineEmployee().getId())))
                .collect(Collectors.toList());
    }
}
