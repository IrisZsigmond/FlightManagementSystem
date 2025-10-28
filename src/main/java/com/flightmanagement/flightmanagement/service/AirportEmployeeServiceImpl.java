package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirportEmployeeServiceImpl implements AirportEmployeeService {

    private final AbstractRepository<AirportEmployee, String> repository;

    public AirportEmployeeServiceImpl(AbstractRepository<AirportEmployee, String> repository) {
        this.repository = repository;
    }


    @Override
    public AirportEmployee save(AirportEmployee entity) {
        return null;
    }

    @Override
    public List<AirportEmployee> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<AirportEmployee> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public AirportEmployee update(String id, AirportEmployee updatedEntity) {
        boolean success = repository.update(id, updatedEntity);
        if (!success) {
            try {
                throw new Exception("AirportEmployee not found: " + id);
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

    @Override
    public List<AirportEmployee> findByDepartment(String department) {
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
        return repository.findAll().stream()
                .filter(e -> department.equalsIgnoreCase(e.getdepartment()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AirportEmployee> findByDesignation(String designation) {
        if (designation == null || designation.isBlank()) {
            throw new IllegalArgumentException("Designation cannot be null or empty");
        }
        return repository.findAll().stream()
                .filter(e -> designation.equalsIgnoreCase(e.getDesignation()))
                .collect(Collectors.toList());
    }
}
