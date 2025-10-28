package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.TicketCategory;
import java.util.List;

/**
 * Service interface for managing Ticket entities.
 */
public interface TicketService extends BaseService<Ticket, String> {

    // Finds all tickets belonging to a specific passenger.
    List<Ticket> findByPassengerId(String passengerId);

    // Finds all tickets associated with a specific flight.
    List<Ticket> findByFlightId(String flightId);

    // Finds all tickets in the given category.
    List<Ticket> findByCategory(TicketCategory category);

    // Calculates the total price of all tickets for a given passenger.
    double calculateTotalPriceForPassenger(String passengerId);
}
