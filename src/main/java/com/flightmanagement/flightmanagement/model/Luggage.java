package com.flightmanagement.flightmanagement.model;

import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;

/**
 * Represents a luggage in the flight management system.
 * Uses ticketId as the single source of truth link to Ticket.
 */
public class Luggage {

    private String id;
    private String ticketId;        // <-- în loc de Ticket ticket
    private LuggageStatus status;
    private LuggageSize size;

    public Luggage() {}

    public Luggage(String id, String ticketId, LuggageStatus status, LuggageSize size) {
        this.id = id;
        this.ticketId = ticketId;
        this.status = status;
        this.size = size;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }

    public LuggageStatus getStatus() { return status; }
    public void setStatus(LuggageStatus status) { this.status = status; }

    public LuggageSize getSize() { return size; }
    public void setSize(LuggageSize size) { this.size = size; }

    @Override
    public String toString() {
        return "Luggage{" +
                "id='" + id + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", status=" + status +
                ", size=" + size +
                '}';
    }
}
