package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface LuggageService {

    Luggage save(Luggage luggage);
    List<Luggage> findAll();
    List<Luggage> findAll(Sort sort);
    Optional<Luggage> findById(String id);
    Luggage update(String id, Luggage updated);
    boolean delete(String id);

    List<Luggage> findByTicketId(String ticketId);
    List<Luggage> findByStatus(LuggageStatus status);
    List<Luggage> findBySize(LuggageSize size);

    List<Luggage> search(
            String ticketId,
            LuggageStatus status,
            LuggageSize size,
            Sort sort
    );
}