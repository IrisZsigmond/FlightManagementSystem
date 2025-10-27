package com.flightmanagement.flightmanagement.model;

import java.util.List;

public class AirlineEmployee extends Staff{
    private AirlineRole role;
    private List<String> assignments;

    public AirlineEmployee(String Id, String name, AirlineRole role, List<String> assignments) {
        super(Id, name);
        this.role = role;
        this.assignments = assignments;
    }

    public AirlineRole getRole() {
        return role;
    }

    public List<String> getAssignments() {
        return assignments;
    }

    public void setRole(AirlineRole role) {
        this.role = role;
    }

    public void setAssignments(List<String> assignments) {
        this.assignments = assignments;
    }

    @Override
    public String toString() {
        return "AirlineEmployee{" +
                ", name='" + getName() + '\'' +
                ", role='" + role + '\'' +
                ", assignments='" + assignments + '\'' +
                '}';
    }
}
