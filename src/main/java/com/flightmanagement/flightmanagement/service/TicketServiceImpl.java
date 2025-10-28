package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.TicketCategory;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of TicketService that provides business logic
 * and interacts with the Ticket repository to perform CRUD operations.
 */
@Service
public class TicketServiceImpl implements TicketService {

    private final AbstractRepository<Ticket, String> repository;

    public TicketServiceImpl(AbstractRepository<Ticket, String> repository) {
        this.repository = repository;
    }

    @Override
    public Ticket save(Ticket entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public List<Ticket> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Ticket> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Ticket update(String id, Ticket updatedEntity) {
        repository.update(id, updatedEntity);
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

    // -------- Ticket-specific methods --------

    @Override
    public List<Ticket> findByPassengerId(String passengerId) {
        return repository.findAll().stream()
                .filter(t -> t.getPassenger() != null && passengerId.equals(t.getPassenger().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> findByFlightId(String flightId) {
        return repository.findAll().stream()
                .filter(t -> flightId.equals(t.getFlightId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> findByCategory(TicketCategory category) {
        return repository.findAll().stream()
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
