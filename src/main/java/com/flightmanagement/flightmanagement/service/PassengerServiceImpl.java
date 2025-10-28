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
public class PassengerServiceImpl implements PassengerService {

    private final AbstractRepository<Passenger, String> repository;

    public PassengerServiceImpl(AbstractRepository<Passenger, String> repository) {
        this.repository = repository;
    }

    @Override
    public Passenger save(Passenger entity) {
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

    // Finds all passengers that have the given name
    @Override
    public List<Passenger> findByName(String name) {
        return repository.findAll().stream()
                .filter(p -> name != null && name.equalsIgnoreCase(p.getName()))
                .collect(Collectors.toList());
    }

    // Finds all passengers that use the specified currency
    @Override
    public List<Passenger> findByCurrency(String currency) {
        return repository.findAll().stream()
                .filter(p -> currency != null && currency.equalsIgnoreCase(p.getCurrency()))
                .collect(Collectors.toList());
    }

    // Returns the number of tickets associated with a given passenger
    @Override
    public int countTickets(String passengerId) {
        return repository.findById(passengerId)
                .map(p -> p.getTickets() != null ? p.getTickets().size() : 0)
                .orElse(0);
    }
}
