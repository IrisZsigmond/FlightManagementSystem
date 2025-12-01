package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneRepository extends JpaRepository<Airplane, String> {

    // pentru UPDATE
    Airplane findByNumber(int number);

    // OPTIONAL (pentru CREATE)
    boolean existsByNumber(int number);

    boolean existsByNumberAndIdNot(int number, String id);
}
