package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Flight;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
@Primary
public interface FlightRepository extends JpaRepository<Flight, String> {
    // Sorting and Filtering
    List<Flight> findByNameContainingIgnoreCase(String name, Sort sort);

    List<Flight> findByDepartureTimeBetween(LocalTime startTime, LocalTime endTime, Sort sort);

    List<Flight> findByNameContainingIgnoreCaseAndDepartureTimeBetween(String name, LocalTime startTime, LocalTime endTime, Sort sort);

    // Custom query methods
    boolean existsByAirplane_Id(String airplaneId);

    boolean existsByName(String name);

    List<Flight> findByAirplane_Id(String airplaneId);
}
