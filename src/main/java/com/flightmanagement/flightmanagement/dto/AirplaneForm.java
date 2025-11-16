package com.flightmanagement.flightmanagement.dto;

public class AirplaneForm {

    private String id;
    private int number;
    private int capacity;

    public AirplaneForm() {}

    public AirplaneForm(String id, int number, int capacity) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}
