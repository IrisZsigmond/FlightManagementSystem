package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import com.flightmanagement.flightmanagement.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // --------------- CRUD ------------------

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Optional<Ticket> findById(String id) {
        return ticketRepository.findById(id);
    }

    @Override
    public Ticket update(String id, Ticket updated) {
        if (!ticketRepository.existsById(id))
            throw new IllegalArgumentException("Ticket not found: " + id);

        updated.setId(id);
        return ticketRepository.save(updated);
    }

    @Override
    public boolean delete(String id) {
        ticketRepository.deleteById(id);
        return true;
    }

    // --------------- CUSTOM ------------------

    @Override
    public List<Ticket> findByPassengerId(String passengerId) {
        return ticketRepository.findByPassenger_Id(passengerId);
    }

    @Override
    public List<Ticket> findByFlightId(String flightId) {
        return ticketRepository.findByFlight_Id(flightId);
    }

    @Override
    public List<Ticket> findByCategory(TicketCategory category) {
        return ticketRepository.findByCategory(category);
    }

    @Override
    public double calculateTotalPriceForPassenger(String passengerId) {
        return findByPassengerId(passengerId).stream()
                .mapToDouble(Ticket::getPrice)
                .sum();
    }
}
