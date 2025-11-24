package com.flightmanagement.flightmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "flight_assignments")
public class FlightAssignment {

    @Id
    private String id;

    // Many assignments belong to one flight
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    // Many assignments belong to one airline employee (crew member)
    @ManyToOne
    @JoinColumn(name = "airline_employee_id")
    private AirlineEmployee airlineEmployee;

    public FlightAssignment() {}

    public FlightAssignment(String id, Flight flight, AirlineEmployee airlineEmployee) {
        this.id = id;
        this.flight = flight;
        this.airlineEmployee = airlineEmployee;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public AirlineEmployee getAirlineEmployee() { return airlineEmployee; }
    public void setAirlineEmployee(AirlineEmployee airlineEmployee) { this.airlineEmployee = airlineEmployee; }

    @Override
    public String toString() {
        return "FlightAssignment{" +
                "id='" + id + '\'' +
                ", flight=" + (flight != null ? flight.getId() : null) +
                ", airlineEmployee=" + (airlineEmployee != null ? airlineEmployee.getId() : null) +
                '}';
    }
}
