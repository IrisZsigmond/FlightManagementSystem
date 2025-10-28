package com.flightmanagement.flightmanagement.model;

import java.util.List;

/* Represents a passenger in the management system.
* Contains details such as name, currency, and tickets*/

public class Passenger {

    private String id;
    private String name;
    private String currency;
    private List<Ticket> tickets;

    public Passenger() {}

    public Passenger(String id, String name, String currency, List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.tickets = tickets;
    }

    //getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }


    @Override
    public String toString() {
        return "Passenger{" +
                "currency='" + currency + '\'' +
                ", tickets=" + tickets +
                ", name='" + name + '\'' +
                '}';
    }

}
