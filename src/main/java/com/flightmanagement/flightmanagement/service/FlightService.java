package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Flight;

import java.time.LocalTime;
import java.util.List;

/**
 * Service interface for managing Flight entities.
 * Extends BaseService for CRUD and adds domain-specific queries.
 */
public interface FlightService extends BaseService<Flight, String> {

    /**
     * Find all flights operated by a given airplane.
     */
    List<Flight> findByAirplaneId(String airplaneId);

    /**
     * Find all flights attached to a given notice board.
     */
    List<Flight> findByNoticeBoardId(String noticeBoardId);

    /**
     * Case-insensitive substring search on flight name.
     */
    List<Flight> findByNameContains(String term);

    /**
     * Find flights departing between 'from' (inclusive) and 'to' (exclusive).
     */
    List<Flight> findByDepartureBetween(LocalTime from, LocalTime to);

    /**
     * Find flights with feedbackScore >= minScore.
     */
    List<Flight> topRated(int minScore);

    /**
     * Find all flights that include a ticket with the given ticketId.
     */
    List<Flight> findByTicketId(String ticketId);

    /**
     * Find all flights that include a staff member with the given staffId (via assignments).
     */
    List<Flight> findByStaffId(String staffId);
}
