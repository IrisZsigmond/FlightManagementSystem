package com.flightmanagement.flightmanagement.model;

import java.util.List;


public class Airplane {


    private String id;

    private int number;
    private int capacity;


    private List<String> flights;

    public Airplane() {}

    public Airplane(String id, int number, int capacity, List<String> flights) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.flights = flights;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<String> getFlights() { return flights; }
    public void setFlights(List<String> flights) { this.flights = flights; }

    @Override
    public String toString() {
        return "Airplane{" +
                "flights=" + flights +
                ", number=" + number +
                ", capacity=" + capacity +
                '}';
    }
}
