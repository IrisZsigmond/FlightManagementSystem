package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.TicketForm;
import com.flightmanagement.flightmanagement.model.Ticket;
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
        t.setCategory(form.getCategory());
        t.setPrice(form.getPrice());
        t.setSeatNumber(form.getSeatNumber());

        if (form.getPassengerId() != null && !form.getPassengerId().isBlank()) {
            t.setPassenger(
                    passengerService.findById(form.getPassengerId())
                            .orElseThrow(() -> new IllegalArgumentException("Passenger not found"))
            );
        } else {
            t.setPassenger(null);
        }

        if (form.getFlightId() != null && !form.getFlightId().isBlank()) {
            t.setFlight(
                    flightService.findById(form.getFlightId())
                            .orElseThrow(() -> new IllegalArgumentException("Flight not found"))
            );
        } else {
            t.setFlight(null);
        }

        return t;
    }

    public TicketForm toForm(Ticket ticket) {
        TicketForm form = new TicketForm();
        form.setId(ticket.getId());
        form.setCategory(ticket.getCategory());
        form.setPrice(ticket.getPrice());
        form.setSeatNumber(ticket.getSeatNumber());
        form.setPassengerId(
                ticket.getPassenger() != null ? ticket.getPassenger().getId() : null
        );
        form.setFlightId(
                ticket.getFlight() != null ? ticket.getFlight().getId() : null
        );
        return form;
    }

    public void updateEntityFromForm(Ticket existing, TicketForm form) {
        existing.setCategory(form.getCategory());
        existing.setPrice(form.getPrice());
        existing.setSeatNumber(form.getSeatNumber());

        if (form.getPassengerId() != null && !form.getPassengerId().isBlank()) {
            existing.setPassenger(
                    passengerService.findById(form.getPassengerId())
                            .orElseThrow(() -> new IllegalArgumentException("Passenger not found"))
            );
        } else {
            existing.setPassenger(null);
        }

        if (form.getFlightId() != null && !form.getFlightId().isBlank()) {
            existing.setFlight(
                    flightService.findById(form.getFlightId())
                            .orElseThrow(() -> new IllegalArgumentException("Flight not found"))
            );
        } else {
            existing.setFlight(null);
        }
    }
}
