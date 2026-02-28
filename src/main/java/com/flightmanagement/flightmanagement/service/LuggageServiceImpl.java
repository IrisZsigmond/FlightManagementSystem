package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
import com.flightmanagement.flightmanagement.repository.LuggageRepository;
import com.flightmanagement.flightmanagement.validations.LuggageValidator;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
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
    public Luggage update(String id, Luggage updated) {
        Luggage existing = validator.requireExisting(id);
        validator.requireExistingTicket(updated.getTicket().getId());
        updated.setId(id);
        return luggageRepository.save(updated);
    }

    @Override
    public boolean delete(String id) {
        Luggage existing = validator.requireExisting(id);
        if (existing.getTicket() != null) {
            existing.setTicket(null);
            luggageRepository.save(existing);
        }
        luggageRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Luggage> findAll() {
        return luggageRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Luggage> findById(String id) {
        return luggageRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Luggage> findAll(Sort sort) {
        return luggageRepository.findAll(sort);
    }

    @Override
    public List<Luggage> findByTicketId(String ticketId) {
        return luggageRepository.findByTicket_Id(ticketId, Sort.unsorted());
    }

    @Override
    public List<Luggage> findByStatus(LuggageStatus status) {
        return luggageRepository.findByStatus(status, Sort.unsorted());
    }

    @Override
    public List<Luggage> findBySize(LuggageSize size) {
        return luggageRepository.findBySize(size, Sort.unsorted());
    }


    @Override
    @Transactional(readOnly = true)
    public List<Luggage> search(String ticketId, LuggageStatus status, LuggageSize size, Sort sort) {

        Sort safeSort = (sort != null) ? sort : Sort.by(Sort.Direction.ASC, "id");

        boolean hasTicket = ticketId != null && !ticketId.trim().isBlank();
        boolean hasStatus = status != null;
        boolean hasSize = size != null;

        if (hasTicket && hasStatus && hasSize) {
            return luggageRepository.findByTicket_IdAndStatusAndSize(ticketId.trim(), status, size, safeSort);
        }

        if (hasTicket && hasStatus) {
            return luggageRepository.findByTicket_IdAndStatus(ticketId.trim(), status, safeSort);
        }
        if (hasTicket && hasSize) {
            return luggageRepository.findByTicket_IdAndSize(ticketId.trim(), size, safeSort);
        }
        if (hasStatus && hasSize) {
            return luggageRepository.findByStatusAndSize(status, size, safeSort);
        }

        if (hasTicket) {
            return luggageRepository.findByTicket_Id(ticketId.trim(), safeSort);
        }
        if (hasStatus) {
            return luggageRepository.findByStatus(status, safeSort);
        }
        if (hasSize) {
            return luggageRepository.findBySize(size, safeSort);
        }

        return luggageRepository.findAll(safeSort);
    }
}