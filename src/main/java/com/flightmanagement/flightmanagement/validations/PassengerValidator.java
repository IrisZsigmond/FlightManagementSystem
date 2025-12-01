package com.flightmanagement.flightmanagement.validations;

import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.repository.PassengerRepository;
import com.flightmanagement.flightmanagement.repository.TicketRepository;
import org.springframework.stereotype.Component;

@Component
public class PassengerValidator {

    private final PassengerRepository passengerRepository;
    private final TicketRepository ticketRepository;

    public PassengerValidator(PassengerRepository passengerRepository,
                              TicketRepository ticketRepository) {
        this.passengerRepository = passengerRepository;
        this.ticketRepository = ticketRepository;
    }

    // 1. Existence
    public Passenger requireExisting(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Passenger ID cannot be null or blank.");
        }

        return passengerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Passenger not found: " + id));
    }

    // 2. Unique ID (CREATE)
    public void assertIdUnique(String id) {
        if (passengerRepository.existsById(id)) {
            throw new IllegalStateException("Passenger ID already exists: " + id);
        }
    }

    // 3. Delete restrictions
    public void assertCanBeDeleted(String id) {
        boolean hasTickets = ticketRepository.existsByPassenger_Id(id);
        if (hasTickets) {
            throw new IllegalStateException(
                    "Cannot delete passenger '" + id + "' because they have tickets.");
        }
    }
}
