package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightAssignmentServiceImplImpl extends BaseServiceImpl<FlightAssignment, String> implements FlightAssignmentService {

    public FlightAssignmentServiceImplImpl(AbstractRepository<FlightAssignment, String> repository) {
        super(repository);
    }

    /// -------- FlightAssignment-specific methods --------

    @Override
    public List<FlightAssignment> findByFlightId(String flightId) {
        if (flightId == null || flightId.isBlank()) {
            throw new IllegalArgumentException("Flight ID cannot be null or empty");
        }
        return repo().findAll().stream()
                .filter(a -> flightId.equalsIgnoreCase(a.getFlightId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightAssignment> findByStaffId(String staffId) {
        if (staffId == null || staffId.isBlank()) {
            throw new IllegalArgumentException("Staff ID cannot be null or empty");
        }
        return repo().findAll().stream()
                .filter(a -> staffId.equalsIgnoreCase(a.getStaffId()))
                .collect(Collectors.toList());
    }
}
