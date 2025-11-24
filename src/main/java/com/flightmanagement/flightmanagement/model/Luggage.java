package com.flightmanagement.flightmanagement.model;

import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "luggages")
public class Luggage {

    @Id
    private String id;

    // Many luggages belong to one ticket
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
                "id='" + id + '\'' +
                ", ticket=" + (ticket != null ? ticket.getId() : null) +
                ", status=" + status +
                ", size=" + size +
                '}';
    }
}
