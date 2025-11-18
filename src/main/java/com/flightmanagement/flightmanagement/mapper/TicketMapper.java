package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.TicketForm;
import com.flightmanagement.flightmanagement.model.Ticket;
import org.springframework.stereotype.Component;

/**
 * Mapper between Ticket entity and TicketForm DTO.
 * Uses IDs only. Does not touch luggages.
 */
@Component
public class TicketMapper {

    public Ticket toEntity(TicketForm form) {
        if (form == null) return null;

        Ticket ticket = new Ticket();
        ticket.setId(form.getId());
        ticket.setPassengerId(form.getPassengerId());
        ticket.setFlightId(form.getFlightId());
        ticket.setCategory(form.getCategory());
        ticket.setPrice(form.getPrice());
        ticket.setSeatNumber(form.getSeatNumber());
        // luggages remains null/managed separately
        return ticket;
    }

    public TicketForm toForm(Ticket ticket) {
        if (ticket == null) return null;

        TicketForm form = new TicketForm();
        form.setId(ticket.getId());
        form.setPassengerId(ticket.getPassengerId());
        form.setFlightId(ticket.getFlightId());
        form.setCategory(ticket.getCategory());
        form.setPrice(ticket.getPrice());
        form.setSeatNumber(ticket.getSeatNumber());
        return form;
    }

    public void updateEntityFromForm(Ticket existing, TicketForm form) {
        if (existing == null || form == null) return;

        existing.setPassengerId(form.getPassengerId());
        existing.setFlightId(form.getFlightId());
        existing.setCategory(form.getCategory());
        existing.setPrice(form.getPrice());
        existing.setSeatNumber(form.getSeatNumber());
        // existing.luggages unchanged
    }
}
