package com.flightmanagement.flightmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "airline_employee")
@DiscriminatorValue("AIRLINE")
public class AirlineEmployee extends Staff {

    @Enumerated(EnumType.STRING)
    private AirlineRole role;

    // One airline employee can have many flight assignments
    @OneToMany(mappedBy = "airlineEmployee")
    @JsonIgnore
    private List<FlightAssignment> assignments;

    public AirlineEmployee() {
        super();
    }

    public AirlineEmployee(String id, String name, AirlineRole role, List<FlightAssignment> assignments) {
        super(id, name);
        this.role = role;
        this.assignments = assignments;
    }

    public AirlineRole getRole() { return role; }
    public void setRole(AirlineRole role) { this.role = role; }

    public List<FlightAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<FlightAssignment> assignments) { this.assignments = assignments; }

    @Override
    public String toString() {
        return "AirlineEmployee{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", role=" + role +
                '}';
    }
}
