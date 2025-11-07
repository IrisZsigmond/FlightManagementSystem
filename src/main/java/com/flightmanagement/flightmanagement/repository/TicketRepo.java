package com.flightmanagement.flightmanagement.repository;

import org.springframework.stereotype.Repository;
import com.flightmanagement.flightmanagement.model.Ticket;

/**In-memory repository for managing Ticket entities.
 * This class extends the generic BaseRepositoryInMemory and
 * provides CRUD operations specifically for Ticket objects.*/
@Repository
public class TicketRepo extends BaseRepositoryInMemory<Ticket, String> {

    public TicketRepo() {
        super(Ticket::getId);
    }
}
