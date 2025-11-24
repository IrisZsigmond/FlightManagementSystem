package com.flightmanagement.flightmanagement.dto;

/**
 * Form DTO pentru AirportEmployee.
 */
public class AirportEmployeeForm {

    private String id;
    private String name;
    private String designation;
    private String department;

    public AirportEmployeeForm() {
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
