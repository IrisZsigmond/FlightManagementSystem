package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AirlineEmployeeService extends BaseService<AirlineEmployee, String> {

    List<AirlineEmployee> findByRole(AirlineRole role);

    List<AirlineEmployee> findByAnyRole(Set<AirlineRole> roles);

    /**
     * Găsește angajații asociați unui anumit FlightAssignment (via staffId).
     */
    List<AirlineEmployee> findByAssignmentId(String assignmentId);

    /**
     * Proiecție: încarcă AirlineEmployee + lista de FlightAssignment
     * bazată pe FlightAssignment.staffId = employee.id
     */
    Optional<AirlineEmployee> findWithAssignments(String id);
}
