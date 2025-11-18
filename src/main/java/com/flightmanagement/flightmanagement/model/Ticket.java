package com.flightmanagement.flightmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;

import java.util.List;

/**
 * Represents a ticket in the management system.
 * Uses IDs (passengerId, flightId) as the single source of truth.
 */
public class Ticket {

    private String id;
    private String passengerId;          // <--- now an ID, not a Passenger object
    private String flightId;
    private TicketCategory category;
    private double price;
    private String seatNumber;

    @JsonIgnore
    private List<Luggage> luggages;      // projection only, not persisted in tickets.json

    public Ticket() {}

    public Ticket(String id,
                  String passengerId,
                  String flightId,
                  TicketCategory category,
                  double price,
                  String seatNumber,
                  List<Luggage> luggages) {
        this.id = id;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.category = category;
        this.price = price;
        this.setSeatNumber(seatNumber);
        this.luggages = luggages;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassengerId() { return passengerId; }
    public void setPassengerId(String passengerId) { this.passengerId = passengerId; }

    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }

    public TicketCategory getCategory() { return category; }
    public void setCategory(TicketCategory category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public List<Luggage> getLuggages() { return luggages; }
    public void setLuggages(List<Luggage> luggages) { this.luggages = luggages; }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", flightId='" + flightId + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", seatNumber='" + seatNumber + '\'' +
                ", luggages=" + luggages +
                '}';
    }
}
