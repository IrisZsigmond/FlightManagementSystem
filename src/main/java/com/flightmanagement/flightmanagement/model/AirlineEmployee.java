package com.flightmanagement.flightmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;

import java.util.List;

/**
 * Represents an airline employee in the flight management system.
 * Inherits common attributes from Staff (ID and name)
 * and adds specific details like role and assigned flights.
 */
public class AirlineEmployee extends Staff {

    private AirlineRole role;

    @JsonIgnore
    private List<FlightAssignment> assignments; // proiecție doar pentru citire

    public AirlineEmployee() {
        super();
    }

    public AirlineEmployee(String id, String name, AirlineRole role, List<FlightAssignment> assignments) {
        super(id, name);
        this.role = role;
        this.assignments = assignments;
    }

    public AirlineRole getRole() {
        return role;
    }

    public void setRole(AirlineRole role) {
        this.role = role;
    }

    public List<FlightAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<FlightAssignment> assignments) {
        this.assignments = assignments;
    }

    @Override
    public String toString() {
        return "AirlineEmployee{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", role=" + role +
                ", assignments=" + assignments +
                '}';
    }
}
