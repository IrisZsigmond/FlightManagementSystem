package com.flightmanagement.flightmanagement.dto;

/**
 * Form DTO for creating/updating flight assignments.
 * Uses only IDs (flightId, staffId).
 */
public class FlightAssignmentForm {

    private String id;
    private String flightId;
    private String staffId;

    public FlightAssignmentForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
