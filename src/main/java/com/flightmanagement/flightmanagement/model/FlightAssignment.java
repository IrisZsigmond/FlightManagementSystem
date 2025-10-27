package com.flightmanagement.flightmanagement.model;

public class FlightAssignment {
    String id;
    String flightId;
    String staffId;

    public FlightAssignment(String id, String flightId, String staffId) {
        this.id = id;
        this.flightId = flightId;
        this.staffId = staffId;
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

    @Override
    public String toString() {
        return "FlightAssignment {flightId=" + flightId + ", staffId=" + staffId + "}";
    }
}
