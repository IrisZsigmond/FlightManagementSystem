package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final TicketService ticketService;

    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                TicketService ticketService) {
        this.passengerRepository = passengerRepository;
        this.ticketService = ticketService;
    }

    // ---------------- CRUD ----------------

    @Override
    public Passenger save(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    @Override
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    @Override
    public Optional<Passenger> findById(String id) {
        return passengerRepository.findById(id);
    }

    @Override
    public Passenger update(String id, Passenger updated) {
        if (!passengerRepository.existsById(id))
            throw new IllegalArgumentException("Passenger not found: " + id);

        updated.setId(id);
        return passengerRepository.save(updated);
    }

    @Override
    public boolean delete(String id) {
        var tickets = ticketService.findByPassengerId(id);
        if (!tickets.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete passenger '" + id +
                            "' because tickets are still assigned (" + tickets.size() + ")."
            );
        }

        passengerRepository.deleteById(id);
        return true;
    }

    // ---------------- CUSTOM ----------------

    @Override
    public List<Passenger> findByName(String name) {
        return passengerRepository.findByNameIgnoreCase(name);
    }

    @Override
    public List<Passenger> findByCurrency(String currency) {
        return passengerRepository.findByCurrencyIgnoreCase(currency);
    }

    @Override
    public int countTickets(String passengerId) {
        return passengerRepository.findById(passengerId)
                .map(p -> p.getTickets() != null ? p.getTickets().size() : 0)
                .orElse(0);
    }

    @Override
    public Optional<Passenger> findWithTickets(String id) {
        return passengerRepository.findById(id)
                .map(p -> {
                    p.setTickets(ticketService.findByPassengerId(id));
                    return p;
                });
    }
}
