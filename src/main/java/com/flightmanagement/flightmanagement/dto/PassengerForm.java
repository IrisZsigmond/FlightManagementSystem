package com.flightmanagement.flightmanagement.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Form-backing DTO for creating/updating passengers.
 */
public class PassengerForm {

    @NotBlank(message = "ID is required")
    @Pattern(regexp = "^P\\d{3}$", message = "Passenger ID must be in format P001.")
    private String id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Currency is required")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "Currency must be a 3-letter ISO code (e.g., EUR, USD)"
    )
    private String currency;

    public PassengerForm() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
