package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.FlightAssignment;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


@Repository
@Primary
public class FlightAssignmentRepositoryInMemory extends BaseRepositoryInMemory<FlightAssignment, String> {
    public FlightAssignmentRepositoryInMemory() {
        super(FlightAssignment::getId);
    }
}
