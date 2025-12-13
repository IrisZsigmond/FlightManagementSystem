package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface AirlineEmployeeRepository extends JpaRepository<AirlineEmployee, String> {

    List<AirlineEmployee> findByRoleIn(List<AirlineRole> roles, Sort sort);

    List<AirlineEmployee> findByNameContainingIgnoreCase(String name, Sort sort);

    List<AirlineEmployee> findByNameContainingIgnoreCaseAndRoleIn(
            String name,
            List<AirlineRole> roles,
            Sort sort
    );
}
