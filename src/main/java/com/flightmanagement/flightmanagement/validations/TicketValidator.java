package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.repository.FlightRepository;
import com.flightmanagement.flightmanagement.repository.LuggageRepository;
import com.flightmanagement.flightmanagement.repository.PassengerRepository;
import com.flightmanagement.flightmanagement.repository.TicketRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TicketValidator {

    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;
    private final LuggageRepository luggageRepository;

    public TicketValidator(
            TicketRepository ticketRepository,
            PassengerRepository passengerRepository,
            FlightRepository flightRepository,
            LuggageRepository luggageRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
        this.luggageRepository = luggageRepository;
    }

    public void assertIdUnique(String id) {
        if (ticketRepository.existsById(id)) {
            throw new IllegalStateException("Ticket ID already exists: " + id);
        }
    }

    public Ticket requireExisting(String id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found: " + id));
    }

    public void requireExistingPassenger(String id) {
        if (!passengerRepository.existsById(id)) {
            throw new IllegalArgumentException("Passenger not found: " + id);
        }
    }

    public void requireExistingFlight(String id) {
        if (!flightRepository.existsById(id)) {
            throw new IllegalArgumentException("Flight not found: " + id);
        }
    }

    public void assertSeatAvailable(String flightId, String seatNumber, String excludeTicketId) {
        ticketRepository.findByFlight_Id(flightId).forEach(t -> {
            if (t.getSeatNumber().equalsIgnoreCase(seatNumber)
                    && !t.getId().equals(excludeTicketId)) {
                throw new IllegalStateException("Seat " + seatNumber + " is already taken on flight " + flightId);
            }
        });
    }

    public void assertCanBeDeleted(String id) {
        Ticket ticket = requireExisting(id);
        Flight flight = ticket.getFlight();

        if (flight != null && flight.getNoticeBoard() != null) {
            LocalDateTime flightDateTime = LocalDateTime.of(
                    flight.getNoticeBoard().getDate(),
                    flight.getDepartureTime() // FIX: getDepartureTime()
            );

            if (flightDateTime.isAfter(LocalDateTime.now())) {
                throw new IllegalStateException("Cannot delete ticket because the flight is upcoming (future).");
            }
        }
    }
}