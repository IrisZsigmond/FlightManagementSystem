package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.TicketCategory;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of TicketService that provides business logic
 * and interacts with the Ticket repository to perform CRUD operations.
 */
@Service
public class TicketServiceImplImpl extends BaseServiceImpl<Ticket, String> implements TicketService {

    public TicketServiceImplImpl(AbstractRepository<Ticket, String> repository) {
        super(repository);
    }

    /// -------- Ticket-specific methods --------

    @Override
    public List<Ticket> findByPassengerId(String passengerId) {
        return repo().findAll().stream()
                .filter(t -> t.getPassenger() != null && passengerId.equals(t.getPassenger().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> findByFlightId(String flightId) {
        return repo().findAll().stream()
                .filter(t -> flightId.equals(t.getFlightId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> findByCategory(TicketCategory category) {
        return repo().findAll().stream()
                .filter(t -> category.equals(t.getCategory()))
                .collect(Collectors.toList());
    }

    @Override
    public double calculateTotalPriceForPassenger(String passengerId) {
        return findByPassengerId(passengerId).stream()
                .mapToDouble(Ticket::getPrice)
                .sum();
    }
}
