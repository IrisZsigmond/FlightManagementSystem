package com.flightmanagement.flightmanagement.repository.InMemoryRepositories;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import org.springframework.stereotype.Repository;

/**In-memory repository for managing AirportEmployee entities.
 * This class extends the generic BaseRepositoryInMemory and
 * provides CRUD operations specifically for AirportEmployee objects*/
//@Repository
public class AirportEmployeeRepo extends BaseRepositoryInMemory<AirportEmployee, String> {
    public AirportEmployeeRepo() {
        super(AirportEmployee::getId);
    }
}
