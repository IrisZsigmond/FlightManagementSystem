package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightAssignmentServiceImpl implements FlightAssignmentService {

    private final AbstractRepository<FlightAssignment, String> repository;

    public FlightAssignmentServiceImpl(AbstractRepository<FlightAssignment, String> repository) {
        this.repository = repository;
    }


    @Override
    public FlightAssignment save(FlightAssignment entity) {
        return null;
    }

    @Override
    public List<FlightAssignment> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<FlightAssignment> findById(String id) {
        return repository.findById(id);
    }


    @Override
    public FlightAssignment update(String id, FlightAssignment updatedEntity) {
        boolean success = repository.update(id, updatedEntity);
        if (!success) {
            try {
                throw new Exception("FlightAssignment not found: " + id);
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
        return false;
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
