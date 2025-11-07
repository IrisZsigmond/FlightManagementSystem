package com.flightmanagement.flightmanagement.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.flightmanagement.flightmanagement.model.AirportEmployee;

/**In-memory repository for managing AirportEmployee entities.
 * This class extends the generic BaseRepositoryInMemory and
 * provides CRUD operations specifically for AirportEmployee objects*/
@Repository
@Primary
public class AirportEmployeeRepo extends BaseRepositoryInMemory<AirportEmployee, String> {
    public AirportEmployeeRepo() {
        super(AirportEmployee::getId);
    }
}
