package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportEmployeeServiceImpl extends BaseServiceImpl<AirportEmployee, String> implements AirportEmployeeService {

    public AirportEmployeeServiceImpl(AbstractRepository<AirportEmployee, String> repository) {
        super(repository);
    }
    /// -------- AirportEmployee-specific methods --------

    @Override
    public List<AirportEmployee> findByDepartment(String department) {
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
        return repo().findAll().stream()
                .filter(e -> department.equalsIgnoreCase(e.getdepartment()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AirportEmployee> findByDesignation(String designation) {
        if (designation == null || designation.isBlank()) {
            throw new IllegalArgumentException("Designation cannot be null or empty");
        }
        return repo().findAll().stream()
                .filter(e -> designation.equalsIgnoreCase(e.getDesignation()))
                .collect(Collectors.toList());
    }
}
