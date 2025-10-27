package com.flightmanagement.flightmanagement.Model;

import jakarta.persistence.*;

@Entity
public class Luggage {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    private LuggageStatus status;

    @Enumerated(EnumType.STRING)
    private LuggageSize size;

    public Luggage() {}

    public Luggage(String id, Ticket ticket, LuggageStatus status, LuggageSize size) {
        this.id = id;
        this.ticket = ticket;
        this.status = status;
        this.size = size;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }

    public LuggageStatus getStatus() { return status; }
    public void setStatus(LuggageStatus status) { this.status = status; }

    public LuggageSize getSize() { return size; }
    public void setSize(LuggageSize size) { this.size = size; }
}
