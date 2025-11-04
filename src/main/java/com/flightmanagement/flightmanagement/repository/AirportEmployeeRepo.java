package com.flightmanagement.flightmanagement.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/*In-memory repository for managing AirportEmployee entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for AirportEmployee objects*/
@Repository
@Primary
public class AirportEmployeeRepo extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.AirportEmployee, String> {
    public AirportEmployeeRepo() {
        super(com.flightmanagement.flightmanagement.model.AirportEmployee::getId);
    }
}
