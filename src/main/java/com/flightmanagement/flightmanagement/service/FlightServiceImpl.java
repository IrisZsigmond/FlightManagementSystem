package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final TicketService ticketService;
    private final FlightAssignmentService flightAssignmentService;

    public FlightServiceImpl(FlightRepository flightRepository,
                             TicketService ticketService,
                             FlightAssignmentService flightAssignmentService) {
        this.flightRepository = flightRepository;
        this.ticketService = ticketService;
        this.flightAssignmentService = flightAssignmentService;
    }

    // ------------- CRUD de bază ----------------

    @Override
    public Flight save(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Flight must not be null");
        }
        return flightRepository.save(flight);
    }

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public Optional<Flight> findById(String id) {
        return flightRepository.findById(id);
    }

    @Override
    public Flight update(String id, Flight updated) {
        if (id == null || updated == null) {
            throw new IllegalArgumentException("id and updated flight must not be null");
        }
        if (!flightRepository.existsById(id)) {
            throw new IllegalArgumentException("Flight not found: " + id);
        }
        // ne asigurăm că id-ul din entitate e același
        updated.setId(id);
        return flightRepository.save(updated);
    }

    @Override
    public boolean delete(String id) {
        if (!flightRepository.existsById(id)) {
            return false;
        }
        flightRepository.deleteById(id);
        return true;
    }

    // ------------- Metode custom (moștenite din vechea implementare) -----------

    @Override
    public List<Flight> findByAirplaneId(String airplaneId) {
        if (airplaneId == null || airplaneId.isBlank()) return List.of();
        return flightRepository.findAll().stream()
                .filter(f -> f.getAirplane() != null
                        && airplaneId.equalsIgnoreCase(f.getAirplane().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Flight> findWithTicketsAndAssignments(String id) {
        return flightRepository.findById(id).map(f -> {
            f.setTickets(ticketService.findByFlightId(id));
            f.setFlightAssignments(flightAssignmentService.findByFlightId(id));
            return f;
        });
    }

    @Override
    public List<Flight> findUnassigned() {
        return flightRepository.findAll().stream()
                .filter(f -> f.getAirplane() == null
                        || f.getAirplane().getId() == null
                        || f.getAirplane().getId().isBlank())
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByNoticeBoardId(String noticeBoardId) {
        if (noticeBoardId == null || noticeBoardId.isBlank()) return List.of();
        return flightRepository.findAll().stream()
                .filter(f -> f.getNoticeBoard() != null
                        && noticeBoardId.equalsIgnoreCase(f.getNoticeBoard().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByNameContains(String term) {
        if (term == null || term.isBlank()) return List.of();
        String needle = term.toLowerCase();
        return flightRepository.findAll().stream()
                .filter(f -> f.getName() != null
                        && f.getName().toLowerCase().contains(needle))
                .collect(Collectors.toList());
    }

    @Override
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
    public List<Flight> findByTicketId(String ticketId) {
        if (ticketId == null || ticketId.isBlank()) return List.of();

        return flightRepository.findAll().stream()
                .filter(f -> ticketService.findByFlightId(f.getId())
                        .stream()
                        .anyMatch(t -> t != null && ticketId.equals(t.getId())))
                .collect(Collectors.toList());
    }

    @Override
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
