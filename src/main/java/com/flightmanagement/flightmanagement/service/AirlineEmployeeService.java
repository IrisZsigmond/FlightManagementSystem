package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.AirlineRole;

import java.util.List;
import java.util.Set;

public interface AirlineEmployeeService extends BaseService<AirlineEmployee, String> {
    /**
     * Find all employees with the given role.
     */
    List<AirlineEmployee> findByRole(AirlineRole role);

    /**
     * Find all employees whose role is one of the provided roles.
     */
    List<AirlineEmployee> findByAnyRole(Set<AirlineRole> roles);

    /**
     * Find all employees assigned to a specific FlightAssignment (by its ID).
     * Assumes AirlineEmployee.assignments contains FlightAssignment objects with getId().
     */
    List<AirlineEmployee> findByAssignmentId(String assignmentId);
}
