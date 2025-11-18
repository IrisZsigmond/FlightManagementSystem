package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.LuggageForm;
import com.flightmanagement.flightmanagement.model.Luggage;
import org.springframework.stereotype.Component;

/**
 * Mapper between Luggage entity and LuggageForm DTO.
 */
@Component
public class LuggageMapper {

    public Luggage toEntity(LuggageForm form) {
        if (form == null) return null;

        Luggage l = new Luggage();
        l.setId(form.getId());
        l.setTicketId(form.getTicketId());
        l.setStatus(form.getStatus());
        l.setSize(form.getSize());
        return l;
    }

    public LuggageForm toForm(Luggage luggage) {
        if (luggage == null) return null;

        LuggageForm form = new LuggageForm();
        form.setId(luggage.getId());
        form.setTicketId(luggage.getTicketId());
        form.setStatus(luggage.getStatus());
        form.setSize(luggage.getSize());
        return form;
    }

    public void updateEntityFromForm(Luggage existing, LuggageForm form) {
        if (existing == null || form == null) return;

        existing.setTicketId(form.getTicketId());
        existing.setStatus(form.getStatus());
        existing.setSize(form.getSize());
    }
}
