package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;
import org.springframework.data.domain.Sort;


import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AirlineEmployeeService {
    // CRUD
    AirlineEmployee save(AirlineEmployee employee);

    List<AirlineEmployee> findAll();

    AirlineEmployee update(String id, AirlineEmployee updated);

    boolean delete(String id);

    // Sorting and Searching
    List<AirlineEmployee> findAll(Sort sort);

    List<AirlineEmployee> search(String name, List<AirlineRole> roles, Sort sort);

    // Other Queries
    AirlineEmployee getById(String id);

    Optional<AirlineEmployee> findById(String id);

    List<AirlineEmployee> findByAssignmentId(String assignmentId);

    Optional<AirlineEmployee> findWithAssignments(String id);
}
