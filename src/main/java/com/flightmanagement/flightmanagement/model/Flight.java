package com.flightmanagement.flightmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalTime;
import java.util.List;

/** Represents a flight in the management system.
 * Contains details such as name, departure time,
 * notice board ID, and airplane ID.*/

public class Flight {
    private String id;
    private String name;
    private LocalTime departureTime;
    private String noticeBoardId;
    private String airplaneId;
    @JsonIgnore
    List<Ticket> tickets;
    @JsonIgnore
    List<FlightAssignment> flightAssignments;

    public Flight() {};

    public Flight(String id, String name, LocalTime departureTime, String noticeBoardId, String airplaneId) {
        this.id = id;
        this.name = name;
        this.departureTime = departureTime;
        this.noticeBoardId = noticeBoardId;
        this.airplaneId = airplaneId;
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

    public LocalTime getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getNoticeBoardId() {
        return noticeBoardId;
    }
    public void setNoticeBoardId(String noticeBoardId) {
        this.noticeBoardId = noticeBoardId;
    }

    public String getAirplaneId() {
        return airplaneId;
    }
    public void setAirplaneId(String airplaneId) {
        this.airplaneId = airplaneId;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<FlightAssignment> getFlightAssignments() {
        return flightAssignments;
    }
    public void setFlightAssignments(List<FlightAssignment> flightAssignments) {
        this.flightAssignments = flightAssignments;
    }


    @Override
    public String toString() {
        return "Flight{" +
                " name='" + name + '\'' +
                ", departureTime=" + departureTime +
                ", noticeBoardId='" + noticeBoardId + '\'' +
                ", airplaneId='" + airplaneId + '\'' +
                '}';
    }
}
