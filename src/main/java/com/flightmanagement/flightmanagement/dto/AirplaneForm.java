package com.flightmanagement.flightmanagement.dto;

import java.util.ArrayList;
import java.util.List;

public class AirplaneForm {

    private String id;
    private int number;
    private int capacity;

    // The UI will send selected flight IDs here
    private List<String> flightIds = new ArrayList<>();

    public AirplaneForm() {
    }

    public AirplaneForm(String id, int number, int capacity, List<String> flightIds) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.flightIds = flightIds;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<String> getFlightIds() { return flightIds; }
    public void setFlightIds(List<String> flightIds) { this.flightIds = flightIds; }
}
