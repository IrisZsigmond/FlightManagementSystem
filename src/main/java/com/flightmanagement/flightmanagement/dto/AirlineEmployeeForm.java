package com.flightmanagement.flightmanagement.dto;

import com.flightmanagement.flightmanagement.model.enums.AirlineRole;

/**
 * Form DTO pentru AirlineEmployee (create/update).
 */
public class AirlineEmployeeForm {

    private String id;
    private String name;
    private AirlineRole role;

    public AirlineEmployeeForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AirlineRole getRole() {
        return role;
    }

    public void setRole(AirlineRole role) {
        this.role = role;
    }
}
