package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.FlightAssignment;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/*In-memory repository for managing FlightAssignment entities.
 * This class extends the generic BaseRepositoryInMemory and provides CRUD operations
 * specifically for FlightAssignment objects*/
@Repository
@Primary
public class FlightAssignmentRepositoryInMemory extends BaseRepositoryInMemory<FlightAssignment, String> {
    public FlightAssignmentRepositoryInMemory() {
        super(FlightAssignment::getId);
    }
}
