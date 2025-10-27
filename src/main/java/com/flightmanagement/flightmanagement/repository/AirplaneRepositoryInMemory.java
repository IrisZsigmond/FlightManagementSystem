package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Airplane;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AirplaneRepositoryInMemory implements AbstractRepository<Airplane> {

    private final List<Airplane> airplanes = new ArrayList<>();

    @Override
    public void save(Airplane airplane) {
        airplanes.add(airplane);
    }

    @Override
    public List<Airplane> findAll() {
        return airplanes;
    }

    @Override
    public Optional<Airplane> findById(String id) {
        return airplanes.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(String id) {
        airplanes.removeIf(a -> a.getId().equals(id));
    }

    @Override
    public void update(String id, Airplane updatedAirplane) {
        for (Airplane airplane : airplanes) {
            if (airplane.getId().equals(id)) {
                airplane.setNumber(updatedAirplane.getNumber());
                airplane.setCapacity(updatedAirplane.getCapacity());
                airplane.setFlights(updatedAirplane.getFlights());
                break;
            }
        }
    }

}
