package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface AirplaneService {

    // Basic CRUD methods:
    Airplane save(Airplane airplane);

    List<Airplane> findAll();

    Airplane update(String id, Airplane updated);

    boolean delete(String id);

    /// Sorting and Searching

    List<Airplane> findAll(Sort sort);

    List<Airplane> findByNumber(Integer number, Sort sort);

    List<Airplane> findByCapacity(Integer capacity, Sort sort);

    List<Airplane> findByNumberAndCapacity(Integer number, Integer capacity, Sort sort);

    List<Airplane> search(Integer number, Integer capacity, Sort sort);

    Optional<Airplane> findById(String id);

    Airplane getById(String id);
}
