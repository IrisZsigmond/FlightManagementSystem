package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    Ticket save(Ticket ticket);

    List<Ticket> findAll();
    List<Ticket> findAll(Sort sort);

    Optional<Ticket> findById(String id);

    Ticket update(String id, Ticket updated);

    boolean delete(String id);

    List<Ticket> findByPassengerId(String passengerId);

    List<Ticket> findByFlightId(String flightId);

    List<Ticket> findByCategory(TicketCategory category);

    double calculateTotalPriceForPassenger(String passengerId);

    // NOU: Metoda de căutare/filtrare combinată
    List<Ticket> search(Double minPrice, Double maxPrice, TicketCategory category, Sort sort);
}