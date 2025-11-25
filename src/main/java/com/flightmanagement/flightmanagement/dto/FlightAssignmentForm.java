package com.flightmanagement.flightmanagement.dto;

public class FlightAssignmentForm {

    private String id;
    private String flightId;
    private String airlineEmployeeId;

    public FlightAssignmentForm() {}

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

    public String getAirlineEmployeeId() {
        return airlineEmployeeId;
    }
    public void setAirlineEmployeeId(String airlineEmployeeId) {
        this.airlineEmployeeId = airlineEmployeeId;
    }
}
