package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirportEmployee;

import java.util.List;
import java.util.Optional;

public interface AirportEmployeeService {

    AirportEmployee save(AirportEmployee employee);

    List<AirportEmployee> findAll();

    Optional<AirportEmployee> findById(String id);

    AirportEmployee update(String id, AirportEmployee updated);

    boolean delete(String id);

    List<AirportEmployee> findByDepartment(String department);

    List<AirportEmployee> findByDesignation(String designation);

    List<AirportEmployee> findByNameContains(String term);
}
