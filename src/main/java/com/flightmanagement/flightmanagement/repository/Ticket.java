package com.flightmanagement.flightmanagement.repository;

import org.springframework.stereotype.Repository;

/*In-memory repository for managing Ticket entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for Ticket objects*/
@Repository
public class Ticket extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.Ticket, String> {

    public Ticket() {
        super(com.flightmanagement.flightmanagement.model.Ticket::getId);
    }
}
