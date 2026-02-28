package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface AirportEmployeeService {
    // CRUD
    AirportEmployee save(AirportEmployee employee);

    List<AirportEmployee> findAll();

    AirportEmployee update(String id, AirportEmployee updated);

    boolean delete(String id);

    // Sorting and Searching
    List<AirportEmployee> findAll(Sort sort);

    List<AirportEmployee> search(String name, String department, String designation, Sort sort);

    // Other Queries
    Optional<AirportEmployee> findById(String id);
}
