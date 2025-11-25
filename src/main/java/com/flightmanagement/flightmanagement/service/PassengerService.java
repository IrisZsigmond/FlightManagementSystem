package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Passenger;

import java.util.List;
import java.util.Optional;

public interface PassengerService {

    Passenger save(Passenger passenger);

    List<Passenger> findAll();

    Optional<Passenger> findById(String id);

    Passenger update(String id, Passenger updated);

    boolean delete(String id);

    List<Passenger> findByName(String name);

    List<Passenger> findByCurrency(String currency);

    int countTickets(String passengerId);

    Optional<Passenger> findWithTickets(String id);
}
