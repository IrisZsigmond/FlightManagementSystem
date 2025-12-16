package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.AirlineEmployeeForm;
import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AirlineEmployeeMapper {

    public AirlineEmployee toEntity(AirlineEmployeeForm form) {
        AirlineEmployee e = new AirlineEmployee();
        e.setId(form.getId());
        e.setName(form.getName());
        e.setRole(AirlineRole.valueOf(form.getRole()));
        return e;
    }

    public AirlineEmployeeForm toForm(AirlineEmployee e) {
        AirlineEmployeeForm form = new AirlineEmployeeForm();

        form.setId(e.getId());
        form.setName(e.getName());
        form.setRole(e.getRole().name());

        return form;
    }

    public void updateEntityFromForm(AirlineEmployee existing, AirlineEmployeeForm form) {
        Objects.requireNonNull(existing);
        Objects.requireNonNull(form);

        existing.setName(form.getName());
        existing.setRole(AirlineRole.valueOf(form.getRole()));
    }
}
