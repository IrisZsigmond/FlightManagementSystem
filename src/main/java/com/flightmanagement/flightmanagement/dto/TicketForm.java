package com.flightmanagement.flightmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TicketForm {

    @NotBlank(message = "ID is required")
    @Pattern(regexp = "^T\\d{3}$", message = "ID must match the format T001.")
    private String id;

    @NotBlank(message = "Passenger ID is required")
    @Pattern(regexp = "^P\\d{3}$", message = "Passenger ID must match the format P001.")
    private String passengerId;

    @NotBlank(message = "Flight ID is required")
    @Pattern(regexp = "^F\\d{3}$", message = "Flight ID must match the format F001.")
    private String flightId;

    @NotBlank(message = "Category must be selected")
    private String category;

    @NotBlank(message = "Price is required")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?$",
            message = "Price must be a valid number")
    private String price;

    @NotBlank(message = "Seat number is required")
    @Size(min = 1, max = 5, message = "Seat number must be 1–5 characters")
    private String seatNumber;

    public TicketForm() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassengerId() { return passengerId; }
    public void setPassengerId(String passengerId) { this.passengerId = passengerId; }

    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
}
