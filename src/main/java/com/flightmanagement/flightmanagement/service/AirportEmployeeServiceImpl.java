package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import com.flightmanagement.flightmanagement.repository.AirportEmployeeRepository;
import com.flightmanagement.flightmanagement.validations.AirportEmployeeValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AirportEmployeeServiceImpl implements AirportEmployeeService {

    private final AirportEmployeeRepository repo;
    private final AirportEmployeeValidator validator;

    public AirportEmployeeServiceImpl(AirportEmployeeRepository repo,
                                      AirportEmployeeValidator validator) {
        this.repo = repo;
        this.validator = validator;
    }

    // ------------ CRUD ------------

    // CREATE
    @Override
    public AirportEmployee save(AirportEmployee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Airport employee cannot be null");
        }

        // ID unic la create
        validator.assertIdUnique(employee.getId());

        return repo.save(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirportEmployee> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AirportEmployee> findById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }
        return repo.findById(id);
    }

    /** helper folosit de controller, similar cu getById de la Airline/Airplane */
    @Transactional(readOnly = true)
    public AirportEmployee getById(String id) {
        return validator.requireExisting(id);
    }

    // UPDATE
    @Override
    public AirportEmployee update(String id, AirportEmployee updated) {
        if (updated == null) {
            throw new IllegalArgumentException("Updated airport employee cannot be null");
        }

        // verificăm că există
        AirportEmployee existing = validator.requireExisting(id);

        // nu schimbăm ID-ul
        existing.setName(updated.getName());
        existing.setDesignation(updated.getDesignation());
        existing.setDepartment(updated.getDepartment());

        return repo.save(existing);
    }

    // DELETE
    @Override
    public boolean delete(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }

        Optional<AirportEmployee> optional = repo.findById(id);
        if (optional.isEmpty()) {
            return false;
        }

        validator.assertCanBeDeleted(id);
        repo.deleteById(id);
        return true;
    }

    // ------------ Custom ------------

    @Override
    @Transactional(readOnly = true)
    public List<AirportEmployee> findByDepartment(String department) {
        if (department == null || department.isBlank()) return List.of();
        return repo.findByDepartmentIgnoreCase(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirportEmployee> findByDesignation(String designation) {
        if (designation == null || designation.isBlank()) return List.of();
        return repo.findByDesignationIgnoreCase(designation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirportEmployee> findByNameContains(String term) {
        if (term == null || term.isBlank()) return List.of();
        return repo.findByNameContainingIgnoreCase(term);
    }
}
