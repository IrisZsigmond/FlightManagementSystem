package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.FlightAssignment;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface FlightAssignmentRepository extends JpaRepository<FlightAssignment, String> {

    // ---------------- Existing ----------------

    List<FlightAssignment> findByFlight_Id(String flightId);

    List<FlightAssignment> findByAirlineEmployee_Id(String employeeId);

    boolean existsByAirlineEmployee_Id(String airlineEmployeeId);

    boolean existsByFlight_IdAndAirlineEmployee_Id(String flightId, String employeeId);

    boolean existsByFlight_IdAndAirlineEmployee_IdAndIdNot(
            String flightId,
            String employeeId,
            String id
    );

    // ---------------- NEW (search + sort) ----------------

    List<FlightAssignment> findByFlight_Id(String flightId, Sort sort);

    List<FlightAssignment> findByAirlineEmployee_Id(String employeeId, Sort sort);

    List<FlightAssignment> findByFlight_IdAndAirlineEmployee_Id(
            String flightId,
            String employeeId,
            Sort sort
    );
}
