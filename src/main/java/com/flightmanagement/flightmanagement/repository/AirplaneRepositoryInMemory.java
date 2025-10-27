package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Airplane;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary // optional: prefer this impl when multiple beans match the interface
public class AirplaneRepositoryInMemory
        extends BaseRepositoryInMemory<Airplane, String> {

    public AirplaneRepositoryInMemory() {
        super(Airplane::getId); // tell the base how to read the ID
    }
}
