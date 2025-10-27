package com.flightmanagement.flightmanagement.model;

import java.time.LocalDate;
import java.util.List;

/* Represents a notice board in the management system.
* Contains details such as date and flights of the day*/
public class NoticeBoard {

    private String id;
    private LocalDate date;
    private List<Flight> flightsOfTheDay;

    public NoticeBoard() {}

    public NoticeBoard(String id, LocalDate date, List<Flight> flightsOfTheDay) {
        this.id = id;
        this.date = date;
        this.flightsOfTheDay = flightsOfTheDay;
    }

    //getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public List<Flight> getFlightsOfTheDay() { return flightsOfTheDay; }
    public void setFlightsOfTheDay(List<Flight> flightsOfTheDay) { this.flightsOfTheDay = flightsOfTheDay; }

    //returns a string representation of the NoticeBoard object
    @Override
    public String toString() {
        return "NoticeBoard{" +
                "date=" + date +
                ", flightsOfTheDay=" + flightsOfTheDay +
                '}';
    }
}
