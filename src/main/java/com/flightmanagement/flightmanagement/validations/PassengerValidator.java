package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.repository.PassengerRepository;
import com.flightmanagement.flightmanagement.repository.TicketRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PassengerValidator {

    private final PassengerRepository passengerRepository;
    private final TicketRepository ticketRepository;

    public PassengerValidator(PassengerRepository passengerRepository,
                              TicketRepository ticketRepository) {
        this.passengerRepository = passengerRepository;
        this.ticketRepository = ticketRepository;
    }

    public Passenger requireExisting(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Passenger ID cannot be null or blank.");
        return passengerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Passenger not found: " + id));
    }

    public void assertIdUnique(String id) {
        if (passengerRepository.existsById(id)) {
            throw new IllegalStateException("Passenger ID already exists: " + id);
        }
    }

    public void assertCanBeDeleted(String id) {
        List<Ticket> tickets = ticketRepository.findByPassenger_Id(id);

        for (Ticket t : tickets) {
            Flight f = t.getFlight();
            if (f != null && f.getNoticeBoard() != null) {
                LocalDateTime flightDateTime = LocalDateTime.of(
                        f.getNoticeBoard().getDate(),
                        f.getDepartureTime() // Folosim getter-ul corect
                );

                if (flightDateTime.isAfter(LocalDateTime.now())) {
                    throw new IllegalStateException("Cannot delete passenger. They have upcoming flights (Ticket ID: " + t.getId() + ")");
                }
            }
        }
    }
}