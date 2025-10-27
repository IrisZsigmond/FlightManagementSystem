package com.flightmanagement.flightmanagement.model;

import java.util.List;

/*Represents a ticket in the management system.
* Contains details such as passenger, flight ID, category, price, seat number, and luggage */
public class Ticket {


    private String id;
    private Passenger passenger;
    private String flightId;
    private TicketCategory category;
    private double price;
    private String seatNumber;
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

    //getters and setters
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

    //returns a string representation of the Ticket object
    @Override
    public String toString() {
        return "Ticket{" +
                "passenger=" + passenger +
                ", flightId='" + flightId + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", seatNumber='" + seatNumber + '\'' +
                ", luggages=" + luggages +
                '}';
    }
}
