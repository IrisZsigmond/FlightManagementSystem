package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Ticket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketRepositoryInMemory implements AbstractRepository<Ticket> {

    private final List<Ticket> tickets = new ArrayList<>();

    @Override
    public void save(Ticket ticket) {
        tickets.add(ticket);
    }

    @Override
    public List<Ticket> findAll() {
        return tickets;
    }

    @Override
    public Optional<Ticket> findById(String id) {
        return tickets.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(String id) {
        tickets.removeIf(t -> t.getId().equals(id));
    }

    @Override
    public void update(String id, Ticket updatedTicket) {
        for (Ticket ticket : tickets) {
            if (ticket.getId().equals(id)) {
                ticket.setPassenger(updatedTicket.getPassenger());
                ticket.setFlightId(updatedTicket.getFlightId());
                ticket.setCategory(updatedTicket.getCategory());
                ticket.setPrice(updatedTicket.getPrice());
                ticket.setSeatNumber(updatedTicket.getSeatNumber());
                ticket.setLuggages(updatedTicket.getLuggages());
                break;
            }
        }
    }

}
