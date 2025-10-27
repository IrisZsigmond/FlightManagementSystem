package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepositoryInMemory extends BaseRepositoryInMemory<Ticket, String> {

    public TicketRepositoryInMemory() {
        super(Ticket::getId);
    }
}
