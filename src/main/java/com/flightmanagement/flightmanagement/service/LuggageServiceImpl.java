package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
import com.flightmanagement.flightmanagement.repository.LuggageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LuggageServiceImpl implements LuggageService {

    private final LuggageRepository luggageRepository;

    public LuggageServiceImpl(LuggageRepository luggageRepository) {
        this.luggageRepository = luggageRepository;
    }

    @Override
    public Luggage save(Luggage luggage) {
        return luggageRepository.save(luggage);
    }

    @Override
    public List<Luggage> findAll() {
        return luggageRepository.findAll();
    }

    @Override
    public Optional<Luggage> findById(String id) {
        return luggageRepository.findById(id);
    }

    @Override
    public Luggage update(String id, Luggage updated) {
        if (!luggageRepository.existsById(id))
            throw new IllegalArgumentException("Luggage not found: " + id);

        updated.setId(id);
        return luggageRepository.save(updated);
    }

    @Override
    public boolean delete(String id) {
        luggageRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Luggage> findByTicketId(String ticketId) {
        return luggageRepository.findByTicket_Id(ticketId);
    }

    @Override
    public List<Luggage> findByStatus(LuggageStatus status) {
        return luggageRepository.findByStatus(status);
    }

    @Override
    public List<Luggage> findBySize(LuggageSize size) {
        return luggageRepository.findBySize(size);
    }
}
