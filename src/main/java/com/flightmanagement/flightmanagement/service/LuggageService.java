package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.LuggageStatus;
import com.flightmanagement.flightmanagement.model.LuggageSize;
import java.util.List;

/**
 * Service interface for managing Luggage entities.
 */
public interface LuggageService extends BaseService<Luggage, String> {

    // Finds all luggage items with the given status.
    List<Luggage> findByStatus(LuggageStatus status);

    // Finds all luggage items of a specific size.
    List<Luggage> findBySize(LuggageSize size);

    // Finds all luggage items associated with a specific ticket.
    List<Luggage> findByTicketId(String ticketId);
}
