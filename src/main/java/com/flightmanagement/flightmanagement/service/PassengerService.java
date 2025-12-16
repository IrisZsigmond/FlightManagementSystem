package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Passenger;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface PassengerService {

    Passenger save(Passenger passenger);

    Passenger update(String id, Passenger updated);

    boolean delete(String id);

    List<Passenger> findAll();
    List<Passenger> findAll(Sort sort);

    Optional<Passenger> findById(String id);

    Passenger getById(String id);

    // Helpers (se păstrează)
    List<Passenger> findByName(String name);
    List<Passenger> findByCurrency(String currency);

    Optional<Passenger> findWithTickets(String id);

    // NOU: Metoda de căutare/filtrare combinată
    List<Passenger> search(String name, String currency, Sort sort);
}