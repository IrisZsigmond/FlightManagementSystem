package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import java.util.List;

/**
 * Service interface for managing Ticket entities.
 */
public interface TicketService extends BaseService<Ticket, String> {

    /**
     * Finds all tickets belonging to a specific passenger.
     *
     * @param passengerId the passenger identifier
     * @return list of matching tickets
     */
    List<Ticket> findByPassengerId(String passengerId);

    /**
     * Finds all tickets associated with a specific flight.
     *
     * @param flightId the flight identifier
     * @return list of matching tickets
     */
    List<Ticket> findByFlightId(String flightId);

    /**
     * Finds all tickets in the given category.
     *
     * @param category the ticket category
     * @return list of matching tickets
     */
    List<Ticket> findByCategory(TicketCategory category);

    /**
     * Calculates the total price of all tickets for a given passenger.
     *
     * @param passengerId the passenger identifier
     * @return accumulated ticket price
     */
    double calculateTotalPriceForPassenger(String passengerId);
}
