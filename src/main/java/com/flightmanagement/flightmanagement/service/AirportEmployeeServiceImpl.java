package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import com.flightmanagement.flightmanagement.repository.AirportEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportEmployeeServiceImpl implements AirportEmployeeService {

    private final AirportEmployeeRepository repo;

    public AirportEmployeeServiceImpl(AirportEmployeeRepository repo) {
        this.repo = repo;
    }

    // ------------ CRUD ------------

    @Override
    public AirportEmployee save(AirportEmployee employee) {
        return repo.save(employee);
    }

    @Override
    public List<AirportEmployee> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<AirportEmployee> findById(String id) {
        return repo.findById(id);
    }

    @Override
    public AirportEmployee update(String id, AirportEmployee updated) {
        if (!repo.existsById(id))
            throw new IllegalArgumentException("Airport employee not found: " + id);

        updated.setId(id);
        return repo.save(updated);
    }

    @Override
    public boolean delete(String id) {
        repo.deleteById(id);
        return true;
    }

    // ------------ Custom ------------

    @Override
    public List<AirportEmployee> findByDepartment(String department) {
        return repo.findByDepartmentIgnoreCase(department);
    }

    @Override
    public List<AirportEmployee> findByDesignation(String designation) {
        return repo.findByDesignationIgnoreCase(designation);
    }

    @Override
    public List<AirportEmployee> findByNameContains(String term) {
        return repo.findByNameContainingIgnoreCase(term);
    }
}
