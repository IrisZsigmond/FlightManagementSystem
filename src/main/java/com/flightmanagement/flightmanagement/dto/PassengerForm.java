package com.flightmanagement.flightmanagement.dto;

/**
 * Form-backing DTO for creating/updating passengers.
 */
public class PassengerForm {

    private String id;
    private String name;
    private String currency;

    public PassengerForm() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
