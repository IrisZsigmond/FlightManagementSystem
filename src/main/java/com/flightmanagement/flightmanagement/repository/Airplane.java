package com.flightmanagement.flightmanagement.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/*In-memory repository for managing Airplane entities.
* This class extends the generic BaseRepositoryInMemory and provides CRUD operations
* specifically for Airplane objects*/
@Repository
@Primary // optional: prefer this impl when multiple beans match the interface
public class Airplane
        extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.Airplane, String> {

    public Airplane() {
        super(com.flightmanagement.flightmanagement.model.Airplane::getId); // tell the base how to read the ID
    }
}
