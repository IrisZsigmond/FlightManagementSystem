package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AirlineEmployeeService {

    AirlineEmployee save(AirlineEmployee employee);

    List<AirlineEmployee> findAll();

    Optional<AirlineEmployee> findById(String id);

    AirlineEmployee update(String id, AirlineEmployee updated);

    boolean delete(String id);

    List<AirlineEmployee> findByRole(AirlineRole role);

    List<AirlineEmployee> findByAnyRole(Set<AirlineRole> roles);

    List<AirlineEmployee> findByAssignmentId(String assignmentId);

    Optional<AirlineEmployee> findWithAssignments(String id);
}
