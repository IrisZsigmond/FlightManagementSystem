package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.AirportEmployeeForm;
import com.flightmanagement.flightmanagement.model.AirportEmployee;
import org.springframework.stereotype.Component;

/**
 * Mapper între AirportEmployee și AirportEmployeeForm.
 */
@Component
public class AirportEmployeeMapper {

    public AirportEmployee toEntity(AirportEmployeeForm form) {
        if (form == null) return null;

        AirportEmployee e = new AirportEmployee();
        e.setId(form.getId());
        e.setName(form.getName());
        e.setDesignation(form.getDesignation());
        e.setDepartment(form.getDepartment());
        return e;
    }

    public AirportEmployeeForm toForm(AirportEmployee e) {
        if (e == null) return null;

        AirportEmployeeForm form = new AirportEmployeeForm();
        form.setId(e.getId());
        form.setName(e.getName());
        form.setDesignation(e.getDesignation());
        form.setDepartment(e.getDepartment());
        return form;
    }

    public void updateEntityFromForm(AirportEmployee existing, AirportEmployeeForm form) {
        if (existing == null || form == null) return;

        existing.setName(form.getName());
        existing.setDesignation(form.getDesignation());
        existing.setDepartment(form.getDepartment());
    }
}
