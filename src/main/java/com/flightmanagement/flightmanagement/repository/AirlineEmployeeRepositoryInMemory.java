package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/*In-memory repository for managing AirlineEmployee entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for AirlineEmployee objects*/
@Repository
@Primary
public class AirlineEmployeeRepositoryInMemory extends BaseRepositoryInMemory<AirlineEmployee, String> {
    public AirlineEmployeeRepositoryInMemory() {
        super(AirlineEmployee::getId);
    }
}
