package com.flightmanagement.flightmanagement.repository.InMemoryRepositories;

import com.flightmanagement.flightmanagement.model.Airplane;
import org.springframework.stereotype.Repository;

/**In-memory repository for managing Airplane entities.
 * This class extends the generic BaseRepositoryInMemory
 * and provides CRUD operations specifically for Airplane objects*/
//@Repository
public class AirplaneRepo
        extends BaseRepositoryInMemory<Airplane, String> {

    public AirplaneRepo() {
        super(Airplane::getId);
    }
}
