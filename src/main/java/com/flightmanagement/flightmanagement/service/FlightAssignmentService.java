package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.FlightAssignment;

import java.util.List;

public interface FlightAssignmentService extends BaseService<FlightAssignment, String> {
    /**
     * Finds all assignments linked to a specific flight.
     *
     * @param flightId the ID of the flight
     * @return list of matching flight assignments
     */
    List<FlightAssignment> findByFlightId(String flightId);

    /**
     * Finds all assignments belonging to a specific staff member.
     *
     * @param staffId the ID of the staff member
     * @return list of matching flight assignments
     */
    List<FlightAssignment> findByStaffId(String staffId);
}