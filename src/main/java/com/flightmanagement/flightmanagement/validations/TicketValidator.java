package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.repository.FlightRepository;
import com.flightmanagement.flightmanagement.repository.LuggageRepository;
import com.flightmanagement.flightmanagement.repository.PassengerRepository;
import com.flightmanagement.flightmanagement.repository.TicketRepository;
import org.springframework.stereotype.Component;

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
                .orElseThrow(() -> new IllegalArgumentException(
                        "Ticket not found: " + id));
    }

    public Passenger requireExistingPassenger(String id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Passenger not found: " + id));
    }

    public Flight requireExistingFlight(String id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Flight not found: " + id));
    }

    public void assertSeatAvailable(String flightId, String seatNumber, String excludeTicketId) {
        ticketRepository.findByFlight_Id(flightId).forEach(t -> {
            if (t.getSeatNumber().equalsIgnoreCase(seatNumber)
                    && !t.getId().equals(excludeTicketId)) {
                throw new IllegalStateException(
                        "Seat " + seatNumber + " is already taken on flight " + flightId);
            }
        });
    }

    // --- NU poți șterge un ticket dacă are luggages ---
    public void assertCanBeDeleted(String id) {
        Ticket ticket = requireExisting(id);

        if (ticket.getLuggages() != null && !ticket.getLuggages().isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete ticket '" + id + "' because it has assigned luggage."
            );
        }
    }


}
