package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.repository.LuggageRepository;
import com.flightmanagement.flightmanagement.repository.TicketRepository;
import org.springframework.stereotype.Component;

@Component
public class LuggageValidator {

    private final LuggageRepository luggageRepository;
    private final TicketRepository ticketRepository;

    public LuggageValidator(LuggageRepository luggageRepository,
                            TicketRepository ticketRepository) {
        this.luggageRepository = luggageRepository;
        this.ticketRepository = ticketRepository;
    }

    // Existence
    public Luggage requireExisting(String id) {
        return luggageRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Luggage not found: " + id));
    }

    public Ticket requireExistingTicket(String id) {
        return ticketRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Ticket not found: " + id));
    }

    // Unique ID (Create)
    public void assertIdUnique(String id) {
        if (luggageRepository.existsById(id)) {
            throw new IllegalStateException("Luggage ID already exists: " + id);
        }
    }

    // Delete Restriction
    public void assertCanBeDeleted(String id) {
        Luggage l = requireExisting(id);

        if (l.getTicket() != null) {
            throw new IllegalStateException(
                    "Cannot delete luggage '" + id + "' because it is assigned to ticket "
                            + l.getTicket().getId()
            );
        }
    }
}
