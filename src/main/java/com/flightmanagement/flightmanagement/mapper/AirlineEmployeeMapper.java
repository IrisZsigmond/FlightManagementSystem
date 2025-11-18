package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.AirlineEmployeeForm;
import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import org.springframework.stereotype.Component;

/**
 * Mapper între AirlineEmployee și AirlineEmployeeForm.
 */
@Component
public class AirlineEmployeeMapper {

    public AirlineEmployee toEntity(AirlineEmployeeForm form) {
        if (form == null) return null;

        AirlineEmployee e = new AirlineEmployee();
        e.setId(form.getId());
        e.setName(form.getName());
        e.setRole(form.getRole());
        return e;
    }

    public AirlineEmployeeForm toForm(AirlineEmployee e) {
        if (e == null) return null;

        AirlineEmployeeForm form = new AirlineEmployeeForm();
        form.setId(e.getId());
        form.setName(e.getName());
        form.setRole(e.getRole());
        return form;
    }

    public void updateEntityFromForm(AirlineEmployee existing, AirlineEmployeeForm form) {
        if (existing == null || form == null) return;

        existing.setName(form.getName());
        existing.setRole(form.getRole());
    }
}
