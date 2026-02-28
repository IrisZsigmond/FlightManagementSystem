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
    private final TicketRepository ticketRepository;
    private final LuggageRepository luggageRepository;

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

    @Override
    public Passenger save(Passenger passenger) {
        passengerValidator.assertIdUnique(passenger.getId());
        return passengerRepository.save(passenger);
    }

    @Override
    public Passenger update(String id, Passenger updated) {
        Passenger existing = passengerValidator.requireExisting(id);
        existing.setName(updated.getName());
        existing.setCurrency(updated.getCurrency());
        return passengerRepository.save(existing);
    }

    @Override
    public boolean delete(String id) {
        Passenger passenger = passengerValidator.requireExisting(id);
        passengerValidator.assertCanBeDeleted(id);

        List<Ticket> tickets = new ArrayList<>(ticketRepository.findByPassenger_Id(id));

        for (Ticket t : tickets) {
            List<Luggage> luggages = luggageRepository.findByTicket_Id(t.getId(), Sort.unsorted());
            luggageRepository.deleteAll(luggages);

            t.setPassenger(null);
        }

        ticketRepository.deleteAll(tickets);

        if (passenger.getTickets() != null) {
            passenger.getTickets().clear();
        }

        passengerRepository.delete(passenger);
        return true;
    }

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