package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if (entity == null || entity.getId() == null) {
            throw new IllegalArgumentException("Ticket and its ID must not be null");
        }
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
        boolean success = repository.update(id, updatedEntity);
        if (!success) {
            throw new IllegalArgumentException("Ticket not found: " + id);
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
}
