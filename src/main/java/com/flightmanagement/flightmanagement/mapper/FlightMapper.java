package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.FlightForm;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.service.AirplaneService;
import com.flightmanagement.flightmanagement.service.NoticeBoardService;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class FlightMapper {

    private final NoticeBoardService noticeBoardService;
    private final AirplaneService airplaneService;

    // format pentru câmpul de timp din form (ex. "10:45")
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public FlightMapper(NoticeBoardService noticeBoardService,
                        AirplaneService airplaneService) {
        this.noticeBoardService = noticeBoardService;
        this.airplaneService = airplaneService;
    }

    public Flight toEntity(FlightForm form) {
        Flight f = new Flight();
        f.setId(form.getId());
        f.setName(form.getName());

        // String -> LocalTime
        if (form.getDepartureTime() != null && !form.getDepartureTime().isBlank()) {
            try {
                LocalTime time = LocalTime.parse(form.getDepartureTime(), TIME_FORMATTER);
                f.setDepartureTime(time);
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Invalid time format for departureTime. Use HH:mm", ex);
            }
        } else {
            f.setDepartureTime(null);
        }

        // NoticeBoard
        if (form.getNoticeBoardId() != null && !form.getNoticeBoardId().isBlank()) {
            f.setNoticeBoard(
                    noticeBoardService.findById(form.getNoticeBoardId())
                            .orElseThrow(() -> new IllegalArgumentException("NoticeBoard not found"))
            );
        } else {
            f.setNoticeBoard(null);
        }

        // Airplane
        if (form.getAirplaneId() != null && !form.getAirplaneId().isBlank()) {
            f.setAirplane(
                    airplaneService.findById(form.getAirplaneId())
                            .orElseThrow(() -> new IllegalArgumentException("Airplane not found"))
            );
        } else {
            f.setAirplane(null);
        }

        return f;
    }

    public FlightForm toForm(Flight flight) {
        FlightForm form = new FlightForm();
        form.setId(flight.getId());
        form.setName(flight.getName());

        // LocalTime -> String
        if (flight.getDepartureTime() != null) {
            form.setDepartureTime(flight.getDepartureTime().format(TIME_FORMATTER));
        } else {
            form.setDepartureTime(null);
        }

        form.setNoticeBoardId(
                flight.getNoticeBoard() != null ? flight.getNoticeBoard().getId() : null
        );
        form.setAirplaneId(
                flight.getAirplane() != null ? flight.getAirplane().getId() : null
        );
        return form;
    }

    public void updateEntityFromForm(Flight existing, FlightForm form) {
        existing.setName(form.getName());

        // String -> LocalTime
        if (form.getDepartureTime() != null && !form.getDepartureTime().isBlank()) {
            try {
                LocalTime time = LocalTime.parse(form.getDepartureTime(), TIME_FORMATTER);
                existing.setDepartureTime(time);
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Invalid time format for departureTime. Use HH:mm", ex);
            }
        } else {
            existing.setDepartureTime(null);
        }

        if (form.getNoticeBoardId() != null && !form.getNoticeBoardId().isBlank()) {
            existing.setNoticeBoard(
                    noticeBoardService.findById(form.getNoticeBoardId())
                            .orElseThrow(() -> new IllegalArgumentException("NoticeBoard not found"))
            );
        } else {
            existing.setNoticeBoard(null);
        }

        if (form.getAirplaneId() != null && !form.getAirplaneId().isBlank()) {
            existing.setAirplane(
                    airplaneService.findById(form.getAirplaneId())
                            .orElseThrow(() -> new IllegalArgumentException("Airplane not found"))
            );
        } else {
            existing.setAirplane(null);
        }
    }
}
