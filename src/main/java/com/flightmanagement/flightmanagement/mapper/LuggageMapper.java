package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.LuggageForm;
import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
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

        if (form.getTicketId() != null && !form.getTicketId().isBlank()) {
            l.setTicket(
                    ticketService.findById(form.getTicketId())
                            .orElseThrow(() -> new IllegalArgumentException("Ticket not found"))
            );
        }

        l.setStatus(LuggageStatus.valueOf(form.getStatus()));
        l.setSize(LuggageSize.valueOf(form.getSize()));

        return l;
    }

    public LuggageForm toForm(Luggage l) {
        LuggageForm form = new LuggageForm();
        form.setId(l.getId());
        form.setTicketId(l.getTicket() != null ? l.getTicket().getId() : null);
        form.setStatus(l.getStatus().name());
        form.setSize(l.getSize().name());
        return form;
    }

    public void updateEntityFromForm(Luggage existing, LuggageForm form) {

        if (form.getTicketId() != null && !form.getTicketId().isBlank()) {
            existing.setTicket(
                    ticketService.findById(form.getTicketId())
                            .orElseThrow(() -> new IllegalArgumentException("Ticket not found"))
            );
        }

        existing.setStatus(LuggageStatus.valueOf(form.getStatus()));
        existing.setSize(LuggageSize.valueOf(form.getSize()));
    }
}
