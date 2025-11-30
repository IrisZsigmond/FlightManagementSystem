package com.flightmanagement.flightmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class FlightForm {

    @NotBlank(message = "ID is required")
    @Pattern(
            regexp = "^F\\d{3}$",
            message = "ID must follow the format Fxxx (e.g., F001)"
    )
    private String id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Departure time is required")
    @Pattern(
            regexp = "^([01]\\d|2[0-3]):[0-5]\\d$",
            message = "Departure time must be in HH:mm format (00:00–23:59)"
    )
    private String departureTime;  // e.g. "10:45"

    @NotBlank(message = "Notice Board selection is required")
    private String noticeBoardId;

    @NotBlank(message = "Airplane selection is required")
    private String airplaneId;

    public FlightForm() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getNoticeBoardId() { return noticeBoardId; }
    public void setNoticeBoardId(String noticeBoardId) { this.noticeBoardId = noticeBoardId; }

    public String getAirplaneId() { return airplaneId; }
    public void setAirplaneId(String airplaneId) { this.airplaneId = airplaneId; }
}
