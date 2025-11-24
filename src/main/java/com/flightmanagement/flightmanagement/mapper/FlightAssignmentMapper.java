package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.FlightAssignmentForm;
import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.service.AirlineEmployeeService;
import com.flightmanagement.flightmanagement.service.FlightService;
import org.springframework.stereotype.Component;

@Component
public class FlightAssignmentMapper {

    private final FlightService flightService;
    private final AirlineEmployeeService airlineEmployeeService;

    public FlightAssignmentMapper(FlightService flightService,
                                  AirlineEmployeeService airlineEmployeeService) {
        this.flightService = flightService;
        this.airlineEmployeeService = airlineEmployeeService;
    }

    public FlightAssignment toEntity(FlightAssignmentForm form) {
        FlightAssignment fa = new FlightAssignment();
        fa.setId(form.getId());

        // Flight
        if (form.getFlightId() != null && !form.getFlightId().isBlank()) {
            Flight flight = flightService.findById(form.getFlightId())
                    .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + form.getFlightId()));
            fa.setFlight(flight);
        } else {
            fa.setFlight(null);
        }

        // AirlineEmployee
        if (form.getAirlineEmployeeId() != null && !form.getAirlineEmployeeId().isBlank()) {
            AirlineEmployee employee = airlineEmployeeService.findById(form.getAirlineEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("AirlineEmployee not found: " + form.getAirlineEmployeeId()));
            fa.setAirlineEmployee(employee);
        } else {
            fa.setAirlineEmployee(null);
        }

        return fa;
    }

    public FlightAssignmentForm toForm(FlightAssignment fa) {
        FlightAssignmentForm form = new FlightAssignmentForm();
        form.setId(fa.getId());
        form.setFlightId(
                fa.getFlight() != null ? fa.getFlight().getId() : null
        );
        form.setAirlineEmployeeId(
                fa.getAirlineEmployee() != null ? fa.getAirlineEmployee().getId() : null
        );
        return form;
    }

    public void updateEntityFromForm(FlightAssignment existing, FlightAssignmentForm form) {
        // id de obicei nu se schimbă, deci îl lăsăm cum e

        // Flight
        if (form.getFlightId() != null && !form.getFlightId().isBlank()) {
            Flight flight = flightService.findById(form.getFlightId())
                    .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + form.getFlightId()));
            existing.setFlight(flight);
        } else {
            existing.setFlight(null);
        }

        // AirlineEmployee
        if (form.getAirlineEmployeeId() != null && !form.getAirlineEmployeeId().isBlank()) {
            AirlineEmployee employee = airlineEmployeeService.findById(form.getAirlineEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("AirlineEmployee not found: " + form.getAirlineEmployeeId()));
            existing.setAirlineEmployee(employee);
        } else {
            existing.setAirlineEmployee(null);
        }
    }
}
