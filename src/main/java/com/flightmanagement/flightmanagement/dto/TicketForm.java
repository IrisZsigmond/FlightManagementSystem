package com.flightmanagement.flightmanagement.dto;

import com.flightmanagement.flightmanagement.model.enums.TicketCategory;

/**
 * Form-backing DTO for creating/updating tickets.
 * Uses IDs only (passengerId, flightId).
 */
public class TicketForm {

    private String id;
    private String passengerId;
    private String flightId;
    private TicketCategory category;
    private double price;
    private String seatNumber;

    public TicketForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public TicketCategory getCategory() {
        return category;
    }

    public void setCategory(TicketCategory category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
