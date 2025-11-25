package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, String> {

    // Extra custom queries could be added later if needed
}
