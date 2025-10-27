package com.flightmanagement.flightmanagement.model;

import java.time.LocalDate;
import java.util.List;


public class NoticeBoard {


    private String id;

    private LocalDate date;


    private List<String> flightsOfTheDay;

    public NoticeBoard() {}

    public NoticeBoard(String id, LocalDate date, List<String> flightsOfTheDay) {
        this.id = id;
        this.date = date;
        this.flightsOfTheDay = flightsOfTheDay;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public List<String> getFlightsOfTheDay() { return flightsOfTheDay; }
    public void setFlightsOfTheDay(List<String> flightsOfTheDay) { this.flightsOfTheDay = flightsOfTheDay; }
}
