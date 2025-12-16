package com.flightmanagement.flightmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "airport_employee")
@DiscriminatorValue("AIRPORT")
public class AirportEmployee extends Staff {

    private String designation;
    private String department;

    public AirportEmployee() {
        super();
    }

    public AirportEmployee(String id, String name, String designation, String department) {
        super(id, name);
        this.designation = designation;
        this.department = department;
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

    @Override
    public String toString() {
        return "AirportEmployee{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", designation='" + designation + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
