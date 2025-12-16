package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface AirportEmployeeRepository extends JpaRepository<AirportEmployee, String> {

    List<AirportEmployee> findByDepartmentContainingIgnoreCase(String department, Sort sort);

    List<AirportEmployee> findByDesignationContainingIgnoreCase(String designation, Sort sort);

    List<AirportEmployee> findByNameContainingIgnoreCase(String namePart, Sort sort);

    List<AirportEmployee> findByNameContainingIgnoreCaseAndDepartmentContainingIgnoreCase(
            String namePart,
            String departmentPart,
            Sort sort
    );

    List<AirportEmployee> findByNameContainingIgnoreCaseAndDesignationContainingIgnoreCase(
            String namePart,
            String designationPart,
            Sort sort
    );

    List<AirportEmployee> findByDepartmentContainingIgnoreCaseAndDesignationContainingIgnoreCase(
            String departmentPart,
            String designationPart,
            Sort sort
    );

    List<AirportEmployee> findByNameContainingIgnoreCaseAndDepartmentContainingIgnoreCaseAndDesignationContainingIgnoreCase(
            String namePart,
            String departmentPart,
            String designationPart,
            Sort sort
    );
}
