package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImplImpl extends BaseServiceImpl<Flight, String> implements FlightService {

    public FlightServiceImplImpl(AbstractRepository<Flight, String> repository) {
        super(repository);
    }
    /// -------- Flight-specific methods --------

    @Override
    public List<Flight> findByAirplaneId(String airplaneId) {
        if (airplaneId == null || airplaneId.isBlank()) return List.of();
        return repo().findAll().stream()
                .filter(f -> airplaneId.equalsIgnoreCase(f.getAirplaneId()))
                .collect(Collectors.toList());
    }

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
    public List<Flight> topRated(int minScore) {
        return repo().findAll().stream()
                .filter(f -> f.getFeedbackScore() >= minScore)
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByTicketId(String ticketId) {
        if (ticketId == null || ticketId.isBlank()) return List.of();
        return repo().findAll().stream()
                .filter(f -> f.getTickets() != null &&
                        f.getTickets().stream().anyMatch(t -> t != null && ticketId.equals(t.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByStaffId(String staffId) {
        if (staffId == null || staffId.isBlank()) return List.of();
        return repo().findAll().stream()
                .filter(f -> f.getFlightAssignments() != null &&
                        f.getFlightAssignments().stream().anyMatch(a -> a != null && staffId.equals(a.getStaffId())))
                .collect(Collectors.toList());
    }
}
