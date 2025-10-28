package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Passenger;

/**
 * Service interface for managing Passenger entities.
 * Extends the generic BaseService interface to provide CRUD operations.
 */
public interface PassengerService extends BaseService<Passenger, String> {
    // You can add Passenger-specific service methods here later, e.g.:
    // List<Passenger> findByCurrency(String currency);
}
