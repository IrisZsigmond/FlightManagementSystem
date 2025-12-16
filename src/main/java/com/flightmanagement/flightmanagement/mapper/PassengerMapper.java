package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.PassengerForm;
import com.flightmanagement.flightmanagement.model.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    public Passenger toEntity(PassengerForm form) {
        Passenger p = new Passenger();
        p.setId(form.getId());
        p.setName(form.getName());
        p.setCurrency(form.getCurrency());
        return p;
    }

    public PassengerForm toForm(Passenger p) {
        PassengerForm form = new PassengerForm();
        form.setId(p.getId());
        form.setName(p.getName());
        form.setCurrency(p.getCurrency());
        return form;
    }

    public void updateEntityFromForm(Passenger existing, PassengerForm form) {
        existing.setName(form.getName());
        existing.setCurrency(form.getCurrency());
    }
}
