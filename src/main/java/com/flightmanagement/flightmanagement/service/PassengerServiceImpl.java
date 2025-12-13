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
        passengerValidator.requireExisting(id);
        passengerValidator.assertCanBeDeleted(id);
        passengerRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    // 🔥 SORT
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
