package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


@Repository
@Primary
public class AirportEmployeeRepositoryInMemory extends BaseRepositoryInMemory<AirportEmployee, String> {
    public AirportEmployeeRepositoryInMemory() {
        super(AirportEmployee::getId);
    }
}
