package com.flightmanagement.flightmanagement.dto;

/**
 * Form-backing DTO for creating/updating flights.
 * Contains only scalar fields that are edited in forms.
 */
public class FlightForm {

    private String id;
    private String name;
    private String departureTime;  // e.g. "10:45"
    private String noticeBoardId;
    private String airplaneId;

    public FlightForm() {
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

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getNoticeBoardId() {
        return noticeBoardId;
    }

    public void setNoticeBoardId(String noticeBoardId) {
        this.noticeBoardId = noticeBoardId;
    }

    public String getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(String airplaneId) {
        this.airplaneId = airplaneId;
    }
}
