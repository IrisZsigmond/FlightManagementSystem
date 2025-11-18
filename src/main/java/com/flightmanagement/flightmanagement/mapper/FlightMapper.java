package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.FlightForm;
import com.flightmanagement.flightmanagement.model.Flight;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Mapper between Flight entity and FlightForm DTO.
 * Does NOT touch tickets or assignments (those are populated
 * separately in the service layer and are @JsonIgnore).
 */
@Component
public class FlightMapper {

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    public Flight toEntity(FlightForm form) {
        if (form == null) return null;

        Flight flight = new Flight();
        flight.setId(form.getId());
        flight.setName(form.getName());
        flight.setDepartureTime(parseTime(form.getDepartureTime()));
        flight.setNoticeBoardId(form.getNoticeBoardId());
        flight.setAirplaneId(form.getAirplaneId());
        return flight;
    }

    public FlightForm toForm(Flight flight) {
        if (flight == null) return null;

        FlightForm form = new FlightForm();
        form.setId(flight.getId());
        form.setName(flight.getName());
        form.setDepartureTime(formatTime(flight.getDepartureTime()));
        form.setNoticeBoardId(flight.getNoticeBoardId());
        form.setAirplaneId(flight.getAirplaneId());
        return form;
    }

    /**
     * Update scalar fields on an existing Flight from the form.
     * Does not touch tickets or assignments.
     */
    public void updateEntityFromForm(Flight existing, FlightForm form) {
        if (existing == null || form == null) return;

        existing.setName(form.getName());
        existing.setDepartureTime(parseTime(form.getDepartureTime()));
        existing.setNoticeBoardId(form.getNoticeBoardId());
        existing.setAirplaneId(form.getAirplaneId());
        // tickets & flightAssignments remain under control of the service layer
    }

    // -------- helper methods --------

    private LocalTime parseTime(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return LocalTime.parse(value, TIME_FMT);
        } catch (DateTimeParseException e) {
            // In a real app you might throw a validation exception instead
            return null;
        }
    }

    private String formatTime(LocalTime time) {
        return time == null ? "" : time.format(TIME_FMT);
    }
}
