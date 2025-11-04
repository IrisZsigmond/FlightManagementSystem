package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl extends BaseServiceImpl<Flight, String> implements FlightService {

    public FlightServiceImpl(AbstractRepository<Flight, String> repository) {
        super(repository);
    }

    /**
     * Preloads sample Flight objects when the application starts.
     * Runs automatically once after bean creation, thanks to @PostConstruct.
     */
    @PostConstruct
    public void initData() {
        repo().save(new Flight("F001", "Morning Express", LocalTime.of(6, 30), 9, "A01", "NB01"));
        repo().save(new Flight("F002", "Sunset Cruiser", LocalTime.of(18, 45), 8, "A02", "NB02"));
        repo().save(new Flight("F003", "Night Owl", LocalTime.of(22, 0), 10, "A03", "NB03"));
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
