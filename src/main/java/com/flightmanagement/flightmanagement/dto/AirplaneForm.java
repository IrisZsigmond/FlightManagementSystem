package com.flightmanagement.flightmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AirplaneForm {

    @NotBlank(message = "ID is required")
    private String id;

    @Min(value = 1, message = "Number must be a positive integer")
    private int number;

    @Min(value = 1, message = "Capacity must be at least 1 seat")
    private int capacity;

    public AirplaneForm() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}
