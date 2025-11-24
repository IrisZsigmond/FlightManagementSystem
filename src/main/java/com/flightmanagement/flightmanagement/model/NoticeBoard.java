package com.flightmanagement.flightmanagement.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "noticeboards")
public class NoticeBoard {

    @Id
    private String id;

    private LocalDate date;

    // One noticeboard has many flights
    @OneToMany(mappedBy = "noticeBoard")
    @JsonIgnore
    private List<Flight> flightsOfTheDay;

    public NoticeBoard() {}

    public NoticeBoard(String id, LocalDate date, List<Flight> flightsOfTheDay) {
        this.id = id;
        this.date = date;
        this.flightsOfTheDay = flightsOfTheDay;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public List<Flight> getFlightsOfTheDay() { return flightsOfTheDay; }
    public void setFlightsOfTheDay(List<Flight> flightsOfTheDay) { this.flightsOfTheDay = flightsOfTheDay; }

    @Override
    public String toString() {
        return "NoticeBoard{" +
                "id='" + id + '\'' +
                ", date=" + date +
                '}';
    }
}
