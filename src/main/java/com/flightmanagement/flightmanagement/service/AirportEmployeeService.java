package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirportEmployee;

import java.util.List;

public interface AirportEmployeeService extends BaseService<AirportEmployee, String> {

    List<AirportEmployee> findByDepartment(String department);

    List<AirportEmployee> findByDesignation(String designation);

    List<AirportEmployee> findByNameContains(String term);
}
