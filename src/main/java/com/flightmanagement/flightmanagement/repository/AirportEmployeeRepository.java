package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface AirportEmployeeRepository extends JpaRepository<AirportEmployee, String> {

    List<AirportEmployee> findByDepartmentIgnoreCase(String department);

    List<AirportEmployee> findByDesignationIgnoreCase(String designation);

    List<AirportEmployee> findByNameContainingIgnoreCase(String namePart);
}
