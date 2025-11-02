package com.flightmanagement.flightmanagement.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**In-memory repository for managing AirlineEmployee entities.
 * This class extends the generic BaseRepositoryInMemory and
 * provides operations specifically for AirlineEmployee objects.
 */
@Repository
@Primary
public class AirlineEmployee extends BaseRepositoryInMemory<com.flightmanagement.flightmanagement.model.AirlineEmployee, String> {
    public AirlineEmployee() {
        super(com.flightmanagement.flightmanagement.model.AirlineEmployee::getId);
    }
}
