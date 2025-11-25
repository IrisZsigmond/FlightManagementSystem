package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;

import java.util.List;
import java.util.Optional;

public interface LuggageService {

    Luggage save(Luggage luggage);

    List<Luggage> findAll();

    Optional<Luggage> findById(String id);

    Luggage update(String id, Luggage updated);

    boolean delete(String id);

    List<Luggage> findByTicketId(String ticketId);

    List<Luggage> findByStatus(LuggageStatus status);

    List<Luggage> findBySize(LuggageSize size);
}
