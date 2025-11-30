package com.flightmanagement.flightmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class FlightAssignmentForm {

    @NotBlank(message = "ID is required")
    @Pattern(regexp = "^FA\\d{3}$", message = "ID must follow format FAxxx (e.g., FA001)")
    private String id;

    @NotBlank(message = "Flight ID is required")
    @Pattern(regexp = "^F\\d{3}$", message = "Flight ID must follow format Fxxx (e.g., F001)")
    private String flightId;

    @NotBlank(message = "Employee ID is required")
    @Pattern(regexp = "^S\\d{3}$", message = "Employee ID must follow format Exxx (e.g., S001)")
    private String airlineEmployeeId;

    public FlightAssignmentForm() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }

    public String getAirlineEmployeeId() { return airlineEmployeeId; }
    public void setAirlineEmployeeId(String airlineEmployeeId) { this.airlineEmployeeId = airlineEmployeeId; }
}
