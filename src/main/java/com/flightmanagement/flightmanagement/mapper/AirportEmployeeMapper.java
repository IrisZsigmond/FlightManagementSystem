package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.AirportEmployeeForm;
import com.flightmanagement.flightmanagement.model.AirportEmployee;
import org.springframework.stereotype.Component;

@Component
public class AirportEmployeeMapper {

    public AirportEmployee toEntity(AirportEmployeeForm form) {
        AirportEmployee e = new AirportEmployee();
        e.setId(form.getId());
        e.setName(form.getName());
        e.setDesignation(form.getDesignation());
        e.setDepartment(form.getDepartment());
        return e;
    }

    public AirportEmployeeForm toForm(AirportEmployee e) {
        AirportEmployeeForm form = new AirportEmployeeForm();

        form.setId(e.getId());
        form.setName(e.getName());
        form.setDesignation(e.getDesignation());
        form.setDepartment(e.getDepartment());

        return form;
    }

    public void updateEntityFromForm(AirportEmployee existing, AirportEmployeeForm form) {
        existing.setName(form.getName());
        existing.setDesignation(form.getDesignation());
        existing.setDepartment(form.getDepartment());
    }
}
