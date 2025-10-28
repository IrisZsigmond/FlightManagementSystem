package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirplaneServiceImpl implements AirplaneService {

    private final AbstractRepository<Airplane, String> repository;

    public AirplaneServiceImpl(AbstractRepository<Airplane, String> repository) {
        this.repository = repository;
    }


    @Override
    public Airplane save(Airplane entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public List<Airplane> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Airplane> findById(String id) {
        return repository.findById(id);
    }


    @Override
    public Airplane update(String id, Airplane updatedEntity) {
        boolean success = repository.update(id, updatedEntity);
        if (!success) {
            try {
                throw new Exception("Airplane not found: " + id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return updatedEntity;
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }

    @Override
    public boolean existsById(String s) {
        return repository.existsById(s);
    }


    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void clear() {
        repository.clear();
    }

    @Override
    public List<Airplane> findByMinCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new IllegalArgumentException("Minimum capacity cannot be negative");
        }
        return repository.findAll().stream()
                .filter(a -> a.getCapacity() >= minCapacity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Airplane> findByFlightId(String flightId) {
        if (flightId == null || flightId.isBlank()) {
            throw new IllegalArgumentException("Flight ID cannot be null or empty");
        }
        return repository.findAll().stream()
                .filter(a -> a.getFlights() != null && a.getFlights().contains(flightId))
                .collect(Collectors.toList());
    }
}
