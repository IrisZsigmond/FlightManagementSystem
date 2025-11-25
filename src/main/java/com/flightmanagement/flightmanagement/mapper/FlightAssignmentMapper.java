package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.FlightAssignmentForm;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.service.FlightService;
import com.flightmanagement.flightmanagement.service.AirlineEmployeeService;
import org.springframework.stereotype.Component;

@Component
public class FlightAssignmentMapper {

    private final FlightService flightService;
    private final AirlineEmployeeService employeeService;

    public FlightAssignmentMapper(
            FlightService flightService,
            AirlineEmployeeService employeeService
    ) {
        this.flightService = flightService;
        this.employeeService = employeeService;
    }

    public FlightAssignment toEntity(FlightAssignmentForm form) {
        FlightAssignment fa = new FlightAssignment();
        fa.setId(form.getId());

        fa.setFlight(
                flightService.findById(form.getFlightId())
                        .orElseThrow(() -> new IllegalArgumentException("Flight not found"))
        );

        fa.setAirlineEmployee(
                employeeService.findById(form.getAirlineEmployeeId())
                        .orElseThrow(() -> new IllegalArgumentException("Employee not found"))
        );

        return fa;
    }

    public FlightAssignmentForm toForm(FlightAssignment a) {
        FlightAssignmentForm form = new FlightAssignmentForm();

        form.setId(a.getId());
        form.setFlightId(a.getFlight().getId());
        form.setAirlineEmployeeId(a.getAirlineEmployee().getId());

        return form;
    }

    public void updateEntityFromForm(FlightAssignment existing, FlightAssignmentForm form) {
        existing.setFlight(
                flightService.findById(form.getFlightId())
                        .orElseThrow(() -> new IllegalArgumentException("Flight not found"))
        );

        existing.setAirlineEmployee(
                employeeService.findById(form.getAirlineEmployeeId())
                        .orElseThrow(() -> new IllegalArgumentException("Employee not found"))
        );
    }
}
