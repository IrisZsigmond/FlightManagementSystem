package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    private final AbstractRepository<Flight, String> repository;

    public FlightServiceImpl(AbstractRepository<Flight, String> repository) {
        this.repository = repository;
    }

    // ---------- CRUD (BaseService methods) ----------

    @Override
    public Flight save(Flight entity) {
        if (entity == null || entity.getId() == null) {
            throw new IllegalArgumentException("Flight and its ID must not be null");
        }
        repository.save(entity);
        return entity;
    }

    @Override
    public List<Flight> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Flight> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flight update(String id, Flight updatedEntity) {
        if (updatedEntity == null || updatedEntity.getId() == null) {
            throw new IllegalArgumentException("Updated Flight and its ID must not be null");
        }
        if (!id.equals(updatedEntity.getId())) {
            throw new IllegalArgumentException("Path ID and entity ID must match");
        }
        boolean replaced = repository.update(id, updatedEntity);
        if (!replaced) {
            throw new IllegalArgumentException("Flight not found: " + id);
        }
        return updatedEntity;
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void clear() {
        repository.clear();
    }

    // ---------- Domain-specific queries ----------

    @Override
    public List<Flight> findByAirplaneId(String airplaneId) {
        if (airplaneId == null || airplaneId.isBlank()) {
            throw new IllegalArgumentException("Airplane ID cannot be null or empty");
        }
        return repository.findAll().stream()
                .filter(f -> airplaneId.equalsIgnoreCase(f.getAirplaneId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByNoticeBoardId(String noticeBoardId) {
        if (noticeBoardId == null || noticeBoardId.isBlank()) {
            throw new IllegalArgumentException("Notice board ID cannot be null or empty");
        }
        return repository.findAll().stream()
                .filter(f -> noticeBoardId.equalsIgnoreCase(f.getNoticeBoardId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByNameContains(String term) {
        if (term == null || term.isBlank()) {
            return List.of();
        }
        String needle = term.toLowerCase();
        return repository.findAll().stream()
                .filter(f -> f.getName() != null && f.getName().toLowerCase().contains(needle))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByDepartureBetween(LocalTime from, LocalTime to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("From and To times must not be null");
        }
        // Simple non-wrapping interval [from, to)
        if (!from.isBefore(to) && !from.equals(to)) {
            throw new IllegalArgumentException("'from' must be <= 'to' when using non-wrapping range");
        }
        return repository.findAll().stream()
                .filter(f -> {
                    LocalTime dt = f.getDepartureTime();
                    return dt != null && ( !dt.isBefore(from) && dt.isBefore(to) );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> topRated(int minScore) {
        return repository.findAll().stream()
                .filter(f -> f.getFeedbackScore() >= minScore)
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByTicketId(String ticketId) {
        if (ticketId == null || ticketId.isBlank()) {
            return List.of();
        }
        return repository.findAll().stream()
                .filter(f -> {
                    List<Ticket> tickets = f.getTickets();
                    if (tickets == null) return false;
                    for (Ticket t : tickets) {
                        if (t != null && ticketId.equals(t.getId())) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByStaffId(String staffId) {
        if (staffId == null || staffId.isBlank()) {
            return List.of();
        }
        return repository.findAll().stream()
                .filter(f -> {
                    List<FlightAssignment> asg = f.getFlightAssignments();
                    if (asg == null) return false;
                    for (FlightAssignment fa : asg) {
                        if (fa != null && staffId.equals(fa.getStaffId())) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
}
