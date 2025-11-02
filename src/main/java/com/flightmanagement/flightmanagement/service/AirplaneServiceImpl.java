package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirplaneServiceImpl extends BaseServiceImpl<Airplane, String> implements AirplaneService {

    public AirplaneServiceImpl(AbstractRepository<Airplane, String> repository) {
        super(repository);
    }

    /// -------- AirplaneEmployee-specific methods --------

    @Override
    public List<Airplane> findByMinCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new IllegalArgumentException("Minimum capacity cannot be negative");
        }
        return repo().findAll().stream()
                .filter(a -> a.getCapacity() >= minCapacity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Airplane> findByFlightId(String flightId) {
        if (flightId == null || flightId.isBlank()) {
            throw new IllegalArgumentException("Flight ID cannot be null or empty");
        }
        return repo().findAll().stream()
                .filter(a -> a.getFlights() != null && a.getFlights().contains(flightId))
                .collect(Collectors.toList());
    }
}
