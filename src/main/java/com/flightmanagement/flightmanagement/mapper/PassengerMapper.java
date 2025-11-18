package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.PassengerForm;
import com.flightmanagement.flightmanagement.model.Passenger;
import org.springframework.stereotype.Component;

/**
 * Mapper între Passenger și PassengerForm.
 * Nu atinge lista de tickets (proiecție doar pentru display).
 */
@Component
public class PassengerMapper {

    public Passenger toEntity(PassengerForm form) {
        if (form == null) return null;

        Passenger p = new Passenger();
        p.setId(form.getId());
        p.setName(form.getName());
        p.setCurrency(form.getCurrency());
        return p;
    }

    public PassengerForm toForm(Passenger passenger) {
        if (passenger == null) return null;

        PassengerForm form = new PassengerForm();
        form.setId(passenger.getId());
        form.setName(passenger.getName());
        form.setCurrency(passenger.getCurrency());
        return form;
    }

    public void updateEntityFromForm(Passenger existing, PassengerForm form) {
        if (existing == null || form == null) return;

        existing.setName(form.getName());
        existing.setCurrency(form.getCurrency());
    }
}
