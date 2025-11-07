package com.flightmanagement.flightmanagement.model;

import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;

/** Represents a luggage in the flight management system.
 * Contains details such as ticket, status, and size */

public class Luggage {

    private String id;
    private Ticket ticket;
    private LuggageStatus status;
    private LuggageSize size;

    public Luggage() {}

    public Luggage(String id, Ticket ticket, LuggageStatus status, LuggageSize size) {
        this.id = id;
        this.ticket = ticket;
        this.status = status;
        this.size = size;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }

    public LuggageStatus getStatus() { return status; }
    public void setStatus(LuggageStatus status) { this.status = status; }

    public LuggageSize getSize() { return size; }
    public void setSize(LuggageSize size) { this.size = size; }

    @Override
    public String toString() {
        return "Luggage{" +
                "ticket=" + ticket +
                ", status=" + status +
                ", size=" + size +
                '}';
    }
}
