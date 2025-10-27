package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Passenger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryPassengerRepository extends AbstractRepository<Passenger> {

    private final List<Passenger> passengers = new ArrayList<>();

    @Override
    public void save(Passenger passenger) {
        passengers.add(passenger);
    }

    @Override
    public List<Passenger> findAll() {
        return passengers;
    }

    @Override
    public Optional<Passenger> findById(String id) {
        return passengers.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(String id) {
        passengers.removeIf(p -> p.getId().equals(id));
    }

    @Override
    public void update(String id, Passenger updatedPassenger) {
        for (Passenger passenger : passengers) {
            if (passenger.getId().equals(id)) {
                passenger.setName(updatedPassenger.getName());
                passenger.setCurrency(updatedPassenger.getCurrency());
                passenger.setTickets(updatedPassenger.getTickets());
                break; // termină după ce găsește pasagerul
            }
        }
    }

}
