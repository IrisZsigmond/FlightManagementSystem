package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


@Repository
@Primary
public class AirlineEmployeeRepositoryInMemory extends BaseRepositoryInMemory<AirlineEmployee, String> {
    public AirlineEmployeeRepositoryInMemory() {
        super(AirlineEmployee::getId);
    }
}
