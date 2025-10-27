package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Luggage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LuggageRepositoryInMemory implements AbstractRepository<Luggage> {

    private final List<Luggage> luggages = new ArrayList<>();

    @Override
    public void save(Luggage luggage) {
        luggages.add(luggage);
    }

    @Override
    public List<Luggage> findAll() {
        return luggages;
    }

    @Override
    public Optional<Luggage> findById(String id) {
        return luggages.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(String id) {
        luggages.removeIf(l -> l.getId().equals(id));
    }

    @Override
    public void update(String id, Luggage updatedLuggage) {
        for (Luggage luggage : luggages) {
            if (luggage.getId().equals(id)) {
                luggage.setTicket(updatedLuggage.getTicket());
                luggage.setStatus(updatedLuggage.getStatus());
                luggage.setSize(updatedLuggage.getSize());
                break;
            }
        }
    }

}
