package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of LuggageService that provides business logic
 * and interacts with the Luggage repository to perform CRUD operations.
 */
@Service
public class LuggageServiceImpl extends BaseServiceImpl<Luggage, String> implements LuggageService {

    public LuggageServiceImpl(AbstractRepository<Luggage, String> repository) {
        super(repository);
    }

    /// -------- Luggage-specific methods --------

    @Override
    public List<Luggage> findByStatus(LuggageStatus status) {
        return repo().findAll().stream()
                .filter(luggage -> status != null && status.equals(luggage.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Luggage> findBySize(LuggageSize size) {
        return repo().findAll().stream()
                .filter(luggage -> size != null && size.equals(luggage.getSize()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Luggage> findByTicketId(String ticketId) {
        return repo().findAll().stream()
                .filter(luggage -> luggage.getTicket() != null && ticketId.equals(luggage.getTicket().getId()))
                .collect(Collectors.toList());
    }
}
