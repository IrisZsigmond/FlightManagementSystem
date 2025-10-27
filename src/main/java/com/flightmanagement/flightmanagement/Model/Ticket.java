package com.flightmanagement.flightmanagement.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Ticket {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    private String flightId;

    @Enumerated(EnumType.STRING)
    private TicketCategory category;

    private double price;
    private String seatNumber;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Luggage> luggages;

    public Ticket() {}

    public Ticket(String id, Passenger passenger, String flightId, TicketCategory category,
                  double price, String seatNumber, List<Luggage> luggages) {
        this.id = id;
        this.passenger = passenger;
        this.flightId = flightId;
        this.category = category;
        this.price = price;
        this.seatNumber = seatNumber;
        this.luggages = luggages;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Passenger getPassenger() { return passenger; }
    public void setPassenger(Passenger passenger) { this.passenger = passenger; }

    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }

    public TicketCategory getCategory() { return category; }
    public void setCategory(TicketCategory category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public List<Luggage> getLuggages() { return luggages; }
    public void setLuggages(List<Luggage> luggages) { this.luggages = luggages; }
}
