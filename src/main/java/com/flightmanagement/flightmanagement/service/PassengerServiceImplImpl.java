package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of PassengerService that provides business logic
 * and interacts with the Passenger repository to perform CRUD operations.
 */
@Service
public class PassengerServiceImplImpl extends BaseServiceImpl<Passenger, String> implements PassengerService {

    public PassengerServiceImplImpl(AbstractRepository<Passenger, String> repository) {
        super(repository);
    }

    /// -------- Passenger-specific methods --------

    @Override
    public List<Passenger> findByName(String name) {
        return repo().findAll().stream()
                .filter(p -> name != null && name.equalsIgnoreCase(p.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Passenger> findByCurrency(String currency) {
        return repo().findAll().stream()
                .filter(p -> currency != null && currency.equalsIgnoreCase(p.getCurrency()))
                .collect(Collectors.toList());
    }

    // Returns the number of tickets associated with a given passenger
    @Override
    public int countTickets(String passengerId) {
        return repo().findById(passengerId)
                .map(p -> p.getTickets() != null ? p.getTickets().size() : 0)
                .orElse(0);
    }
}
