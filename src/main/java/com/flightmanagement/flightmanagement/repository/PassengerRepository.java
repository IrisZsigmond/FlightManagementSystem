package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, String> {

    List<Passenger> findByNameIgnoreCase(String name);

    List<Passenger> findByCurrencyIgnoreCase(String currency);
}
