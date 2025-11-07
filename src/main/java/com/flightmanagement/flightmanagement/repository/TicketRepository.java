package com.flightmanagement.flightmanagement.repository;

import org.springframework.stereotype.Repository;

/**
 * In-memory repository for managing Ticket entities.
 * <p>
 * This class extends the generic BaseRepositoryInMemory and provides
 * CRUD operations specifically for Ticket objects.
 */
@Repository
public class TicketRepository extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.Ticket, String> {

    /**
     * Constructor that configures how the repository extracts the ID of a Ticket.
     */
    public TicketRepository() {
        super(com.flightmanagement.flightmanagement.model.Ticket::getId);
    }
}
