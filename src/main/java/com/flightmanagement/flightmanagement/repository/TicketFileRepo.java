package com.flightmanagement.flightmanagement.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.flightmanagement.flightmanagement.config.AppDataProperties;
import com.flightmanagement.flightmanagement.model.Ticket;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * File-backed repository for Ticket.
 * Loads/saves tickets into tickets.json under the runtime data directory.
 */
@Repository
@Primary
public class TicketFileRepo extends InFileRepository<Ticket, String> {

    public TicketFileRepo(AppDataProperties props, ResourceLoader resourceLoader) {
        super(
                "tickets.json",
                new TypeReference<List<Ticket>>() {},
                Ticket::getId,
                props,
                resourceLoader
        );
    }
}
