package com.flightmanagement.flightmanagement.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Passenger {

    @Id
    private String id;

    private String name;
    private String currency;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public Passenger() {}

    public Passenger(String id, String name, String currency, List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.tickets = tickets;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }
}
