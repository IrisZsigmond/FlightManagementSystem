package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.TicketForm;
import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import com.flightmanagement.flightmanagement.service.FlightService;
import com.flightmanagement.flightmanagement.service.PassengerService;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    private final PassengerService passengerService;
    private final FlightService flightService;

    public TicketMapper(PassengerService passengerService,
                        FlightService flightService) {
        this.passengerService = passengerService;
        this.flightService = flightService;
    }

    public Ticket toEntity(TicketForm form) {
        Ticket t = new Ticket();
        t.setId(form.getId());

        // passenger
        t.setPassenger(
                passengerService.findById(form.getPassengerId())
                        .orElseThrow(() -> new IllegalArgumentException("Passenger not found"))
        );

        // flight
        t.setFlight(
                flightService.findById(form.getFlightId())
                        .orElseThrow(() -> new IllegalArgumentException("Flight not found"))
        );

        t.setCategory(TicketCategory.valueOf(form.getCategory()));
        t.setPrice(Double.parseDouble(form.getPrice()));
        t.setSeatNumber(form.getSeatNumber());

        return t;
    }

    public TicketForm toForm(Ticket t) {
        TicketForm form = new TicketForm();

        form.setId(t.getId());
        form.setPassengerId(t.getPassenger().getId());
        form.setFlightId(t.getFlight().getId());
        form.setCategory(t.getCategory().name());
        form.setPrice(String.valueOf(t.getPrice()));
        form.setSeatNumber(t.getSeatNumber());

        return form;
    }

    public void updateEntityFromForm(Ticket existing, TicketForm form) {

        existing.setPassenger(
                passengerService.findById(form.getPassengerId())
                        .orElseThrow(() -> new IllegalArgumentException("Passenger not found"))
        );

        existing.setFlight(
                flightService.findById(form.getFlightId())
                        .orElseThrow(() -> new IllegalArgumentException("Flight not found"))
        );

        existing.setCategory(TicketCategory.valueOf(form.getCategory()));
        existing.setPrice(Double.parseDouble(form.getPrice()));
        existing.setSeatNumber(form.getSeatNumber());
    }
}
