package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LuggageServiceImpl extends BaseServiceImpl<Luggage, String>
        implements LuggageService {

    public LuggageServiceImpl(AbstractRepository<Luggage, String> repository) {
        super(repository);
    }

    @Override
    public List<Luggage> findByTicketId(String ticketId) {
        if (ticketId == null || ticketId.isBlank()) return List.of();
        return repo().findAll().stream()
                .filter(l -> ticketId.equals(l.getTicketId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Luggage> findByStatus(LuggageStatus status) {
        if (status == null) return List.of();
        return repo().findAll().stream()
                .filter(l -> status.equals(l.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Luggage> findBySize(LuggageSize size) {
        if (size == null) return List.of();
        return repo().findAll().stream()
                .filter(l -> size.equals(l.getSize()))
                .collect(Collectors.toList());
    }
}
