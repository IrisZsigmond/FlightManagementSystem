package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Passenger;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface PassengerRepository extends JpaRepository<Passenger, String> {

    List<Passenger> findByNameIgnoreCase(String name);
    List<Passenger> findByCurrencyIgnoreCase(String currency);

    List<Passenger> findByNameContainingIgnoreCase(String name, Sort sort);

    List<Passenger> findByCurrencyContainingIgnoreCase(String currency, Sort sort);

    List<Passenger> findByNameContainingIgnoreCaseAndCurrencyContainingIgnoreCase(
            String name,
            String currency,
            Sort sort
    );
}