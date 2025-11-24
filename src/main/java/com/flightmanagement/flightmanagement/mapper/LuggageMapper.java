package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.LuggageForm;
import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.service.TicketService;
import org.springframework.stereotype.Component;

@Component
public class LuggageMapper {

    private final TicketService ticketService;

    public LuggageMapper(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public Luggage toEntity(LuggageForm form) {
        Luggage l = new Luggage();
        l.setId(form.getId());
        l.setStatus(form.getStatus());
        l.setSize(form.getSize());

        if (form.getTicketId() != null && !form.getTicketId().isBlank()) {
            l.setTicket(
                    ticketService.findById(form.getTicketId())
                            .orElseThrow(() -> new IllegalArgumentException("Ticket not found"))
            );
        } else {
            l.setTicket(null);
        }

        return l;
    }

    public LuggageForm toForm(Luggage luggage) {
        LuggageForm form = new LuggageForm();
        form.setId(luggage.getId());
        form.setStatus(luggage.getStatus());
        form.setSize(luggage.getSize());
        form.setTicketId(
                luggage.getTicket() != null ? luggage.getTicket().getId() : null
        );
        return form;
    }

    public void updateEntityFromForm(Luggage existing, LuggageForm form) {
        existing.setStatus(form.getStatus());
        existing.setSize(form.getSize());

        if (form.getTicketId() != null && !form.getTicketId().isBlank()) {
            existing.setTicket(
                    ticketService.findById(form.getTicketId())
                            .orElseThrow(() -> new IllegalArgumentException("Ticket not found"))
            );
        } else {
            existing.setTicket(null);
        }
    }
}
