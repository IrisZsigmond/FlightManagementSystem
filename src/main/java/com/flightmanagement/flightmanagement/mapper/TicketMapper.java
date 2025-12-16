package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.TicketForm;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import com.flightmanagement.flightmanagement.repository.FlightRepository;
import com.flightmanagement.flightmanagement.repository.PassengerRepository;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;

    public TicketMapper(PassengerRepository passengerRepository,
                        FlightRepository flightRepository) {
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
    }

    public Ticket toEntity(TicketForm form) {

        Passenger p = passengerRepository.findById(form.getPassengerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Passenger not found: " + form.getPassengerId()));

        Flight f = flightRepository.findById(form.getFlightId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Flight not found: " + form.getFlightId()));

        Ticket t = new Ticket();
        t.setId(form.getId());
        t.setPassenger(p);
        t.setFlight(f);
        t.setCategory(TicketCategory.valueOf(form.getCategory()));
        t.setPrice(Double.parseDouble(form.getPrice()));
        t.setSeatNumber(form.getSeatNumber());

        return t;
    }

    public TicketForm toForm(Ticket ticket) {

        TicketForm form = new TicketForm();

        form.setId(ticket.getId());
        form.setPassengerId(ticket.getPassenger().getId());
        form.setFlightId(ticket.getFlight().getId());
        form.setCategory(ticket.getCategory().name());
        form.setPrice(String.valueOf(ticket.getPrice()));
        form.setSeatNumber(ticket.getSeatNumber());

        return form;
    }

    public void updateEntityFromForm(Ticket existing, TicketForm form) {

        Passenger p = passengerRepository.findById(form.getPassengerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Passenger not found: " + form.getPassengerId()));

        Flight f = flightRepository.findById(form.getFlightId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Flight not found: " + form.getFlightId()));

        existing.setPassenger(p);
        existing.setFlight(f);
        existing.setCategory(TicketCategory.valueOf(form.getCategory()));
        existing.setPrice(Double.parseDouble(form.getPrice()));
        existing.setSeatNumber(form.getSeatNumber());
    }
}
