package com.flightmanagement.flightmanagement.model;

import java.util.List;

/* Represents an airline employee in the flight management system.
 * Inherits common attributes from Staff (such as ID and name)
 * and adds specific details like role and assigned flights */
public class AirlineEmployee extends Staff{
    private AirlineRole role;
    private List<FlightAssignment> assignments;

    //constructor
    public AirlineEmployee(String Id, String name, AirlineRole role, List<FlightAssignment> assignments) {
        super(Id, name);
        this.role = role;
        this.assignments = assignments;
    }

    //getters and setters
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

    //returns a string representation of the AirlineEmployee object
    @Override
    public String toString() {
        return "AirlineEmployee{" +
                "name='" + getName() + '\'' +
                ", role='" + role + '\'' +
                ", assignments='" + assignments + '\'' +
                '}';
    }
}
