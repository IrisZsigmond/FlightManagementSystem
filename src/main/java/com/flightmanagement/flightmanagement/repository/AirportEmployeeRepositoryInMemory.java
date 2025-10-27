package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/*In-memory repository for managing AirportEmployee entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for AirportEmployee objects*/
@Repository
@Primary
public class AirportEmployeeRepositoryInMemory extends BaseRepositoryInMemory<AirportEmployee, String> {
    public AirportEmployeeRepositoryInMemory() {
        super(AirportEmployee::getId);
    }
}
