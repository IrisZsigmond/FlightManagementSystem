package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Passenger;
import java.util.List;

/**
 * Service interface for managing Passenger entities.
 */
public interface PassengerService extends BaseService<Passenger, String> {

    // Finds all passengers that have the given name.
    List<Passenger> findByName(String name);

    // Finds all passengers that use the specified currency.
    List<Passenger> findByCurrency(String currency);

    // Returns the number of tickets associated with a given passenger.
    int countTickets(String passengerId);
}
