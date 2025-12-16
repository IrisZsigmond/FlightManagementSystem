package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.repository.PassengerRepository;
import com.flightmanagement.flightmanagement.repository.TicketRepository;
import com.flightmanagement.flightmanagement.repository.LuggageRepository;
import com.flightmanagement.flightmanagement.validations.PassengerValidator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerValidator passengerValidator;
    private final TicketService ticketService;
    private final TicketRepository ticketRepository; // Adăugat pentru ștergere în cascadă
    private final LuggageRepository luggageRepository; // Adăugat pentru ștergere în cascadă

    // CONSTRUCTOR ACTUALIZAT
    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                PassengerValidator passengerValidator,
                                TicketService ticketService,
                                TicketRepository ticketRepository,
                                LuggageRepository luggageRepository) {
        this.passengerRepository = passengerRepository;
        this.passengerValidator = passengerValidator;
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
        this.luggageRepository = luggageRepository;
    }

    // ---------------- CREATE ----------------
    @Override
    public Passenger save(Passenger passenger) {
        passengerValidator.assertIdUnique(passenger.getId());
        return passengerRepository.save(passenger);
    }

    // ---------------- UPDATE ----------------
    @Override
    public Passenger update(String id, Passenger updated) {
        Passenger existing = passengerValidator.requireExisting(id);
        existing.setName(updated.getName());
        existing.setCurrency(updated.getCurrency());
        return passengerRepository.save(existing);
    }

    // ---------------- DELETE (MODIFICAT - CASCADE ROBUST) ----------------
    @Override
    public boolean delete(String id) {
        // 1. Validare (verifică existența și timpul zborurilor)
        Passenger passenger = passengerValidator.requireExisting(id);
        passengerValidator.assertCanBeDeleted(id);

        // 2. Cascade: Găsim biletele și lucrăm pe o copie
        List<Ticket> tickets = new ArrayList<>(ticketRepository.findByPassenger_Id(id));

        for (Ticket t : tickets) {
            // 2.1 Ștergem bagajele asociate (FIX: adăugăm Sort.unsorted())
            List<Luggage> luggages = luggageRepository.findByTicket_Id(t.getId(), Sort.unsorted());
            luggageRepository.deleteAll(luggages);

            // 2.2 FIX: Rupem referința bidirecțională (previne TransientObjectException)
            t.setPassenger(null);
        }

        // 3. Ștergem biletele
        ticketRepository.deleteAll(tickets);

        // 4. Curățăm colecția din obiectul Passenger (pentru consistența memoriei)
        if (passenger.getTickets() != null) {
            passenger.getTickets().clear();
        }

        // 5. Ștergem Pasagerul
        passengerRepository.delete(passenger);
        return true;
    }

    // ---------------- READ / SORT ----------------
    @Override
    @Transactional(readOnly = true)
    public List<Passenger> findAll() {
        return passengerRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Passenger> findAll(Sort sort) {
        return passengerRepository.findAll(sort);
    }

    @Override
    public Optional<Passenger> findById(String id) {
        return passengerRepository.findById(id);
    }

    @Override
    public Passenger getById(String id) {
        return passengerValidator.requireExisting(id);
    }

    // ---------------- SEARCH + SORT ----------------
    @Override
    @Transactional(readOnly = true)
    public List<Passenger> search(String name, String currency, Sort sort) {

        Sort safeSort = (sort != null) ? sort : Sort.by(Sort.Direction.ASC, "name");

        boolean hasName = name != null && !name.trim().isBlank();
        boolean hasCurrency = currency != null && !currency.trim().isBlank();

        String trimmedName = hasName ? name.trim() : null;
        String trimmedCurrency = hasCurrency ? currency.trim() : null;

        if (hasName && hasCurrency) {
            return passengerRepository.findByNameContainingIgnoreCaseAndCurrencyContainingIgnoreCase(trimmedName, trimmedCurrency, safeSort);
        }

        if (hasName) {
            return passengerRepository.findByNameContainingIgnoreCase(trimmedName, safeSort);
        }

        if (hasCurrency) {
            return passengerRepository.findByCurrencyContainingIgnoreCase(trimmedCurrency, safeSort);
        }

        return passengerRepository.findAll(safeSort);
    }

    // ---------------- HELPERS ----------------
    @Override
    public List<Passenger> findByName(String name) {
        return passengerRepository.findByNameIgnoreCase(name);
    }

    @Override
    public List<Passenger> findByCurrency(String currency) {
        return passengerRepository.findByCurrencyIgnoreCase(currency);
    }

    @Override
    public Optional<Passenger> findWithTickets(String id) {
        return passengerRepository.findById(id).map(p -> {
            p.setTickets(ticketService.findByPassengerId(id));
            return p;
        });
    }
}