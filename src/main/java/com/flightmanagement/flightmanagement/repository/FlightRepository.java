package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Flight;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface FlightRepository extends JpaRepository<Flight, String> {
    // Base methods (findAll, findById, save, deleteById)
}
