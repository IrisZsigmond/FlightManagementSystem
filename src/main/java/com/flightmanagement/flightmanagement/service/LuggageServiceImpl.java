package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
import com.flightmanagement.flightmanagement.repository.LuggageRepository;
import com.flightmanagement.flightmanagement.validations.LuggageValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LuggageServiceImpl implements LuggageService {

    private final LuggageRepository luggageRepository;
    private final LuggageValidator validator;

    public LuggageServiceImpl(LuggageRepository luggageRepository,
                              LuggageValidator validator) {
        this.luggageRepository = luggageRepository;
        this.validator = validator;
    }

    @Override
    public Luggage save(Luggage luggage) {

        validator.assertIdUnique(luggage.getId());
        validator.requireExistingTicket(luggage.getTicket().getId());

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

        Luggage existing = validator.requireExisting(id);

        validator.requireExistingTicket(updated.getTicket().getId());

        updated.setId(id);
        return luggageRepository.save(updated);
    }

    @Override
    public boolean delete(String id) {

        Luggage existing = validator.requireExisting(id);

        // Dez-atașăm bagajul de la ticket înainte de delete
        if (existing.getTicket() != null) {
            existing.setTicket(null);
            luggageRepository.save(existing); // salvăm actualizarea
        }

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