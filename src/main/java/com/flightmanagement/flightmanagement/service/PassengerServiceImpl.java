package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of PassengerService that provides business logic
 * and interacts with the Passenger repository to perform CRUD operations.
 */
@Service
public class PassengerServiceImpl implements PassengerService {

    private final AbstractRepository<Passenger, String> repository;

    public PassengerServiceImpl(AbstractRepository<Passenger, String> repository) {
        this.repository = repository;
    }

    @Override
    public Passenger save(Passenger entity) {
        if (entity == null || entity.getId() == null) {
            throw new IllegalArgumentException("Passenger and its ID must not be null");
        }
        repository.save(entity);
        return entity;
    }

    @Override
    public List<Passenger> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Passenger> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Passenger update(String id, Passenger updatedEntity) {
        boolean success = repository.update(id, updatedEntity);
        if (!success) {
            throw new IllegalArgumentException("Passenger not found: " + id);
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
