package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.AirplaneForm;
import com.flightmanagement.flightmanagement.model.Airplane;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AirplaneMapper {

    public Airplane toEntity(AirplaneForm form) {
        Airplane a = new Airplane();
        a.setId(form.getId());
        a.setNumber(form.getNumber());
        a.setCapacity(form.getCapacity());
        return a;
    }

    public AirplaneForm toForm(Airplane airplane) {
        AirplaneForm form = new AirplaneForm();
        form.setId(airplane.getId());
        form.setNumber(airplane.getNumber());
        form.setCapacity(airplane.getCapacity());
        return form;
    }

    public void updateEntityFromForm(Airplane existing, AirplaneForm form) {
        Objects.requireNonNull(existing);
        Objects.requireNonNull(form);
        existing.setNumber(form.getNumber());
        existing.setCapacity(form.getCapacity());
    }
}
