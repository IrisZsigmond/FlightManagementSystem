package com.flightmanagement.flightmanagement.model;

import java.time.LocalTime;
import java.util.List;

/* Represents a flight in the management system.
* Contains details such as name, departure time, feedback score, notice board ID, and airplane ID*/
public class Flight {
    private String id;
    private String name;
    private LocalTime departureTime;
    private int feedbackScore;
    private String noticeBoardId;
    private String airplaneId;
    List<Ticket> tickets;
    List<FlightAssignment> flightAssignments;

    public Flight(String id, String name, LocalTime departureTime, int feedbackScore, String noticeBoardId, String airplaneId) {
        this.id = id;
        this.name = name;
        this.departureTime = departureTime;
        this.feedbackScore = feedbackScore;
        this.noticeBoardId = noticeBoardId;
        this.airplaneId = airplaneId;
    }

    //getters and setters
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

    public int getFeedbackScore() {
        return feedbackScore;
    }

    public void setFeedbackScore(int feedbackScore) {
        this.feedbackScore = feedbackScore;
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

    //returns a string representation of the Flight object
    @Override
    public String toString() {
        return "Flight{" +
                " name='" + name + '\'' +
                ", departureTime=" + departureTime +
                ", feedbackScore=" + feedbackScore +
                ", noticeBoardId='" + noticeBoardId + '\'' +
                ", airplaneId='" + airplaneId + '\'' +
                '}';
    }
}
