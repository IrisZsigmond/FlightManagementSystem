package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.FlightAssignment;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface FlightAssignmentRepository extends JpaRepository<FlightAssignment, String> {

    List<FlightAssignment> findByFlight_Id(String flightId);

    List<FlightAssignment> findByAirlineEmployee_Id(String employeeId);
}
