package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Airplane;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AirplaneRepository extends JpaRepository<Airplane, String> {

    // for Queries
    List<Airplane> findByNumber(int number, Sort sort);

    List<Airplane> findByCapacity(int capacity, Sort sort);

    List<Airplane> findByNumberAndCapacity(int number, int capacity, Sort sort);

    // for Validations
    boolean existsByNumber(int number);

    boolean existsByNumberAndIdNot(int number, String id);
}
