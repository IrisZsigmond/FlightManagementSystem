package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import java.util.List;

/**
 * Service interface for managing Luggage entities.
 */
public interface LuggageService extends BaseService<Luggage, String> {

    /**
     * Finds all luggage items with the given status.
     *
     * @param status the target status
     * @return list of matching luggage items
     */
    List<Luggage> findByStatus(LuggageStatus status);

    /**
     * Finds all luggage items of a specific size.
     *
     * @param size the target luggage size
     * @return list of matching luggage items
     */
    List<Luggage> findBySize(LuggageSize size);

    /**
     * Finds all luggage items associated with a specific ticket.
     *
     * @param ticketId the ticket identifier
     * @return list of luggage items linked to that ticket
     */
    List<Luggage> findByTicketId(String ticketId);
}
