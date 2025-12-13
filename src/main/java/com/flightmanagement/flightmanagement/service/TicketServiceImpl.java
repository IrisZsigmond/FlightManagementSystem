package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import com.flightmanagement.flightmanagement.repository.TicketRepository;
import com.flightmanagement.flightmanagement.validations.TicketValidator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketValidator validator;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             TicketValidator validator) {
        this.ticketRepository = ticketRepository;
        this.validator = validator;
    }

    @Override
    public Ticket save(Ticket ticket) {
        validator.assertIdUnique(ticket.getId());
        validator.requireExistingPassenger(ticket.getPassenger().getId());
        validator.requireExistingFlight(ticket.getFlight().getId());

        validator.assertSeatAvailable(
                ticket.getFlight().getId(),
                ticket.getSeatNumber(),
                null
        );

        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public List<Ticket> findAll(Sort sort) {
        return ticketRepository.findAll(sort);
    }

    @Override
    public Optional<Ticket> findById(String id) {
        return ticketRepository.findById(id);
    }

    @Override
    public Ticket update(String id, Ticket updated) {
        Ticket existing = validator.requireExisting(id);

        validator.requireExistingPassenger(updated.getPassenger().getId());
        validator.requireExistingFlight(updated.getFlight().getId());

        validator.assertSeatAvailable(
                updated.getFlight().getId(),
                updated.getSeatNumber(),
                id
        );

        updated.setId(id);
        return ticketRepository.save(updated);
    }

    @Override
    public boolean delete(String id) {
        validator.requireExisting(id);
        validator.assertCanBeDeleted(id);
        ticketRepository.deleteById(id);
        return true;
    }

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
        return findByPassengerId(passengerId)
                .stream()
                .mapToDouble(Ticket::getPrice)
                .sum();
    }
}
