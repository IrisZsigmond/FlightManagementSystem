package com.flightmanagement.flightmanagement.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "airplanes")
public class Airplane {

    @Id
    private String id;

    private int number;

    private int capacity;

    // One airplane has many flights
    @OneToMany(mappedBy = "airplane")
    @JsonIgnore
    private List<Flight> flights;

    public Airplane() {}

    public Airplane(String id, int number, int capacity, List<Flight> flights) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.flights = flights;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<Flight> getFlights() { return flights; }
    public void setFlights(List<Flight> flights) { this.flights = flights; }

    @Override
    public String toString() {
        return "Airplane{" +
                "id='" + id + '\'' +
                ", number=" + number +
                ", capacity=" + capacity +
                '}';
    }
}
