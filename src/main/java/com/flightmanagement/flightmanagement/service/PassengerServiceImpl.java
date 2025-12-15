package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.repository.PassengerRepository;
import com.flightmanagement.flightmanagement.validations.PassengerValidator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerValidator passengerValidator;
    private final TicketService ticketService;

    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                PassengerValidator passengerValidator,
                                TicketService ticketService) {
        this.passengerRepository = passengerRepository;
        this.passengerValidator = passengerValidator;
        this.ticketService = ticketService;
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

    // ---------------- DELETE ----------------
    @Override
    public boolean delete(String id) {
        passengerValidator.requireExisting(id);
        passengerValidator.assertCanBeDeleted(id);
        passengerRepository.deleteById(id);
        return true;
    }

    // ---------------- READ / SORT ----------------
    @Override
    @Transactional(readOnly = true)
    public List<Passenger> findAll() {
        return passengerRepository.findAll(Sort.by(Sort.Direction.ASC, "name")); // Sortare implicită
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

    // ---------------- SEARCH + SORT (NOU) ----------------
    @Override
    @Transactional(readOnly = true)
    public List<Passenger> search(String name, String currency, Sort sort) {

        Sort safeSort = (sort != null) ? sort : Sort.by(Sort.Direction.ASC, "name");

        boolean hasName = name != null && !name.trim().isBlank();
        boolean hasCurrency = currency != null && !currency.trim().isBlank();

        String trimmedName = hasName ? name.trim() : null;
        String trimmedCurrency = hasCurrency ? currency.trim() : null;

        // Cazul 1: Filtrare pe ambele criterii
        if (hasName && hasCurrency) {
            return passengerRepository.findByNameContainingIgnoreCaseAndCurrencyContainingIgnoreCase(trimmedName, trimmedCurrency, safeSort);
        }

        // Cazul 2: Filtrare doar după Nume
        if (hasName) {
            return passengerRepository.findByNameContainingIgnoreCase(trimmedName, safeSort);
        }

        // Cazul 3: Filtrare doar după Monedă
        if (hasCurrency) {
            return passengerRepository.findByCurrencyContainingIgnoreCase(trimmedCurrency, safeSort);
        }

        // Cazul 4: Fără filtre (doar sortare)
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