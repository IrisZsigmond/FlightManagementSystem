package com.flightmanagement.flightmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    private String id;

    // Many tickets belong to one passenger
    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    // Many tickets belong to one flight
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Enumerated(EnumType.STRING)
    private TicketCategory category;

    private double price;

    private String seatNumber;

    // One ticket has many luggages
    @OneToMany(mappedBy = "ticket")
    @JsonIgnore
    private List<Luggage> luggages;

    public Ticket() {}

    public Ticket(String id,
                  Passenger passenger,
                  Flight flight,
                  TicketCategory category,
                  double price,
                  String seatNumber,
                  List<Luggage> luggages) {
        this.id = id;
        this.passenger = passenger;
        this.flight = flight;
        this.category = category;
        this.price = price;
        this.seatNumber = seatNumber;
        this.luggages = luggages;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Passenger getPassenger() { return passenger; }
    public void setPassenger(Passenger passenger) { this.passenger = passenger; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public TicketCategory getCategory() { return category; }
    public void setCategory(TicketCategory category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public List<Luggage> getLuggages() { return luggages; }
    public void setLuggages(List<Luggage> luggages) { this.luggages = luggages; }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", passenger=" + (passenger != null ? passenger.getId() : null) +
                ", flight=" + (flight != null ? flight.getId() : null) +
                ", category=" + category +
                ", price=" + price +
                ", seatNumber='" + seatNumber + '\'' +
                '}';
    }
}
