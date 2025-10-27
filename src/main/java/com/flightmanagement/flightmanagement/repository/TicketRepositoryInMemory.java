package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*In-memory repository for managing Ticket entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for Ticket objects*/
@Repository
public class TicketRepositoryInMemory extends BaseRepositoryInMemory<Ticket, String> {

    public TicketRepositoryInMemory() {
        super(Ticket::getId);
    }
}
