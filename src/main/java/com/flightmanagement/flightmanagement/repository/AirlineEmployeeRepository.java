package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirlineEmployeeRepository extends JpaRepository<AirlineEmployee, String> {

    List<AirlineEmployee> findByRole(AirlineRole role);

    List<AirlineEmployee> findByRoleIn(List<AirlineRole> roles);
}
