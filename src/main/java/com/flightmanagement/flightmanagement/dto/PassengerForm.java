package com.flightmanagement.flightmanagement.dto;

/**
 * DTO pentru formularele de create/update Passenger.
 */
public class PassengerForm {

    private String id;
    private String name;
    private String currency;

    public PassengerForm() {
    }

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
