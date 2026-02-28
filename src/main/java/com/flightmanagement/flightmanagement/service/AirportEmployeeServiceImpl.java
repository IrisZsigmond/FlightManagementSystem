package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import com.flightmanagement.flightmanagement.repository.AirportEmployeeRepository;
import com.flightmanagement.flightmanagement.validations.AirportEmployeeValidator;
import org.springframework.data.domain.Sort;
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

    // CRUD

    // CREATE
    @Override
    public AirportEmployee save(AirportEmployee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Airport employee cannot be null");
        }

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

    /** helper folosit de controller */
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

        AirportEmployee existing = validator.requireExisting(id);

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

    @Override
    @Transactional(readOnly = true)
    public List<AirportEmployee> findAll(Sort sort) {
        return repo.findAll(sort);
    }

    @Override
    public List<AirportEmployee> search(String name, String department, String designation, Sort sort) {
        Sort safeSort = sort != null ? sort : Sort.by(Sort.Direction.ASC, "id");

        boolean hasName = name != null && !name.isBlank();
        boolean hasDepartment = department != null && !department.isBlank();
        boolean hasDesignation = designation != null && !designation.isBlank();

        if (hasName && hasDepartment && hasDesignation) {
            return repo.findByNameContainingIgnoreCaseAndDepartmentContainingIgnoreCaseAndDesignationContainingIgnoreCase(name.trim(), department.trim(), designation.trim(), safeSort);
        } else if (hasName && hasDepartment) {
            return repo.findByNameContainingIgnoreCaseAndDepartmentContainingIgnoreCase(name.trim(), department.trim(), safeSort);
        } else if (hasName && hasDesignation) {
            return repo.findByNameContainingIgnoreCaseAndDesignationContainingIgnoreCase(name.trim(), designation.trim(), safeSort);
        } else if (hasDepartment && hasDesignation) {
            return repo.findByDepartmentContainingIgnoreCaseAndDesignationContainingIgnoreCase(department.trim(), designation.trim(), safeSort);
        } else if (hasName) {
            return repo.findByNameContainingIgnoreCase(name.trim(), safeSort);
        } else if (hasDepartment) {
            return repo.findByDepartmentContainingIgnoreCase(department.trim(), safeSort);
        } else if (hasDesignation) {
            return repo.findByDesignationContainingIgnoreCase(designation.trim(), safeSort);
        } else {
            return repo.findAll(safeSort);
        }
    }
}
