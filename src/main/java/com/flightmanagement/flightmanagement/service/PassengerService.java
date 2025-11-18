package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Passenger;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Passenger entities.
 */
public interface PassengerService extends BaseService<Passenger, String> {

    /**
     * Finds all passengers that have the given name.
     *
     * @param name the passenger name
     * @return list of matching passengers
     */
    List<Passenger> findByName(String name);

    /**
     * Finds all passengers that use the specified currency.
     *
     * @param currency the currency code
     * @return list of matching passengers
     */
    List<Passenger> findByCurrency(String currency);

    /**
     * Returns the number of tickets associated with a given passenger.
     *
     * @param passengerId the passenger identifier
     * @return number of tickets
     */
    int countTickets(String passengerId);

    /**
     * Încarcă Passenger + îi atașează lista de bilete (tickets).
     * Tickets sunt obținute din TicketService pe baza passengerId.
     */
    Optional<Passenger> findWithTickets(String id);
}
