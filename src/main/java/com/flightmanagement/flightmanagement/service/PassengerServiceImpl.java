package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of PassengerService that provides business logic
 * and interacts with the Passenger repository to perform CRUD operations.
 */
@Service
public class PassengerServiceImpl extends BaseServiceImpl<Passenger, String> implements PassengerService {

    private final TicketService ticketService;

    public PassengerServiceImpl(AbstractRepository<Passenger, String> repository,
                                TicketService ticketService) {
        super(repository);
        this.ticketService = ticketService;
    }
    /// -------- Passenger-specific methods --------

    @Override
    public List<Passenger> findByName(String name) {
        return repo().findAll().stream()
                .filter(p -> name != null && name.equalsIgnoreCase(p.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Passenger> findByCurrency(String currency) {
        return repo().findAll().stream()
                .filter(p -> currency != null && currency.equalsIgnoreCase(p.getCurrency()))
                .collect(Collectors.toList());
    }

    // Returns the number of tickets associated with a given passenger
    @Override
    public int countTickets(String passengerId) {
        return repo().findById(passengerId)
                .map(p -> p.getTickets() != null ? p.getTickets().size() : 0)
                .orElse(0);
    }

    @Override
    public Optional<Passenger> findWithTickets(String id) {
        return repo().findById(id).map(p -> {
            p.setTickets(ticketService.findByPassengerId(id));
            return p;
        });
    }

    @Override
    public boolean delete(String id) {
        var tickets = ticketService.findByPassengerId(id);
        if (!tickets.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete passenger '" + id +
                            "' because tickets are still assigned (" + tickets.size() + ").");
        }
        return super.delete(id);
    }
}
