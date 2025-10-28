package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirportEmployee;

import java.util.List;

public interface AirportEmployeeService extends BaseService<AirportEmployee, String> {
    /**
     * Finds all employees working in the given department.
     *
     * @param department the department name (e.g., "Operations", "Security")
     * @return list of employees belonging to that department
     */
    List<AirportEmployee> findByDepartment(String department);

    /**
     * Finds all employees with the given designation.
     *
     * @param designation the job title (e.g., "Manager", "Technician")
     * @return list of employees with that designation
     */
    List<AirportEmployee> findByDesignation(String designation);
}
