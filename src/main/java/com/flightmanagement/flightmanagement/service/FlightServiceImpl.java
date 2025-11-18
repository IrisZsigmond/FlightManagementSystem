package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl extends BaseServiceImpl<Flight, String> implements FlightService {

    private final TicketService ticketService;
    private final FlightAssignmentService flightAssignmentService;

    public FlightServiceImpl(AbstractRepository<Flight, String> repository,
                             TicketService ticketService,
                             FlightAssignmentService flightAssignmentService) {
        super(repository);
        this.ticketService = ticketService;
        this.flightAssignmentService = flightAssignmentService;
    }

    /// -------- Ownership-side read methods --------

    @Override
    public List<Flight> findByAirplaneId(String airplaneId) {
        if (airplaneId == null || airplaneId.isBlank()) return List.of();
        return repo().findAll().stream()
                .filter(f -> airplaneId.equalsIgnoreCase(f.getAirplaneId()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Flight> findWithTicketsAndAssignments(String id) {
        return repo().findById(id).map(f -> {
            f.setTickets(ticketService.findByFlightId(id));
            f.setFlightAssignments(flightAssignmentService.findByFlightId(id));
            return f;
        });
    }

    @Override
    public List<Flight> findUnassigned() {
        return repo().findAll().stream()
                .filter(f -> f.getAirplaneId() == null || f.getAirplaneId().isBlank())
                .collect(Collectors.toList());
    }

    /// -------- Existing query methods --------

    @Override
    public List<Flight> findByNoticeBoardId(String noticeBoardId) {
        if (noticeBoardId == null || noticeBoardId.isBlank()) return List.of();
        return repo().findAll().stream()
                .filter(f -> noticeBoardId.equalsIgnoreCase(f.getNoticeBoardId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByNameContains(String term) {
        if (term == null || term.isBlank()) return List.of();
        String needle = term.toLowerCase();
        return repo().findAll().stream()
                .filter(f -> f.getName() != null && f.getName().toLowerCase().contains(needle))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByDepartureBetween(LocalTime from, LocalTime to) {
        if (from == null || to == null) throw new IllegalArgumentException("Times must not be null");
        return repo().findAll().stream()
                .filter(f -> f.getDepartureTime() != null
                        && !f.getDepartureTime().isBefore(from)
                        && f.getDepartureTime().isBefore(to))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByTicketId(String ticketId) {
        if (ticketId == null || ticketId.isBlank()) return List.of();

        return repo().findAll().stream()
                .filter(f -> ticketService.findByFlightId(f.getId())
                        .stream()
                        .anyMatch(t -> t != null && ticketId.equals(t.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByStaffId(String staffId) {
        if (staffId == null || staffId.isBlank()) return List.of();

        return repo().findAll().stream()
                .filter(f -> flightAssignmentService.findByFlightId(f.getId())
                        .stream()
                        .anyMatch(a -> a != null && staffId.equals(a.getStaffId())))
                .collect(Collectors.toList());
    }
}
