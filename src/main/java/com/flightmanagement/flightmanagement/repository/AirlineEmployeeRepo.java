package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**In-memory repository for managing AirlineEmployee entities.
 * This class extends the generic BaseRepositoryInMemory and
 * provides operations specifically for AirlineEmployee objects.
 */
@Repository
@Primary
public class AirlineEmployeeRepo extends BaseRepositoryInMemory<AirlineEmployee, String> {
    public AirlineEmployeeRepo() {
        super(AirlineEmployee::getId);
    }
}
