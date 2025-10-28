package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Ticket;

/**
 * Service interface for managing Ticket entities.
 * Extends the generic BaseService interface to provide CRUD operations.
 */
public interface TicketService extends BaseService<Ticket, String> {
    // You can add Ticket-specific service methods here later, e.g.:
    // List<Ticket> findByPassengerName(String name);
}
