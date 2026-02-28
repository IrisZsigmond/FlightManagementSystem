package com.flightmanagement.flightmanagement.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    private String id;

    private String name;

    private LocalTime departureTime;

    //RELATIONSHIPS

    // Many flights can belong to one NoticeBoard
    @ManyToOne
    @JoinColumn(name = "noticeboard_id")
    private NoticeBoard noticeBoard;

    // Many flights use one Airplane
    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    // One flight has many Tickets
    @OneToMany(mappedBy = "flight")
    @JsonIgnore
    private List<Ticket> tickets;

    // One flight has many FlightAssignments
    @OneToMany(mappedBy = "flight")
    @JsonIgnore
    private List<FlightAssignment> flightAssignments;

    public Flight() {}

    public Flight(String id, String name, LocalTime departureTime, NoticeBoard noticeBoard, Airplane airplane) {
        this.id = id;
        this.name = name;
        this.departureTime = departureTime;
        this.noticeBoard = noticeBoard;
        this.airplane = airplane;
    }

   //GETTERS AND SETTERS

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

    public NoticeBoard getNoticeBoard() {
        return noticeBoard;
    }

    public void setNoticeBoard(NoticeBoard noticeBoard) {
        this.noticeBoard = noticeBoard;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
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
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", departureTime=" + departureTime +
                ", noticeBoard=" + (noticeBoard != null ? noticeBoard.getId() : null) +
                ", airplane=" + (airplane != null ? airplane.getId() : null) +
                '}';
    }
}