package com.flightmanagement.flightmanagement.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.flightmanagement.flightmanagement.model.Airplane;

/**In-memory repository for managing Airplane entities.
 * This class extends the generic BaseRepositoryInMemory
 * and provides CRUD operations specifically for Airplane objects*/
@Repository
@Primary // optional: prefer this implementation when multiple beans match the interface
public class AirplaneRepo
        extends BaseRepositoryInMemory<Airplane, String> {

    public AirplaneRepo() {
        super(Airplane::getId); // tell the base how to read the ID
    }
}
