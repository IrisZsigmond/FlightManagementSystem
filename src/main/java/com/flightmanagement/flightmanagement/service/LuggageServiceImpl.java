package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.LuggageStatus;
import com.flightmanagement.flightmanagement.model.LuggageSize;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of LuggageService that provides business logic
 * and interacts with the Luggage repository to perform CRUD operations.
 */
@Service
public class LuggageServiceImpl implements LuggageService {

    private final AbstractRepository<Luggage, String> repository;

    public LuggageServiceImpl(AbstractRepository<Luggage, String> repository) {
        this.repository = repository;
    }

    @Override
    public Luggage save(Luggage entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public List<Luggage> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Luggage> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Luggage update(String id, Luggage updatedEntity) {
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

    // Finds all luggage items with the given status
    @Override
    public List<Luggage> findByStatus(LuggageStatus status) {
        return repository.findAll().stream()
                .filter(luggage -> status != null && status.equals(luggage.getStatus()))
                .collect(Collectors.toList());
    }

    // Finds all luggage items of a specific size
    @Override
    public List<Luggage> findBySize(LuggageSize size) {
        return repository.findAll().stream()
                .filter(luggage -> size != null && size.equals(luggage.getSize()))
                .collect(Collectors.toList());
    }

    // Finds all luggage items associated with a specific ticket
    @Override
    public List<Luggage> findByTicketId(String ticketId) {
        return repository.findAll().stream()
                .filter(luggage -> luggage.getTicket() != null && ticketId.equals(luggage.getTicket().getId()))
                .collect(Collectors.toList());
    }
}
