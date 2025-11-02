package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.AirlineRole;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AirlineEmployeeServiceImplImpl extends BaseServiceImpl<AirlineEmployee, String> implements AirlineEmployeeService {

    public AirlineEmployeeServiceImplImpl(AbstractRepository<AirlineEmployee, String> repository) {
        super(repository);
    }
    
    @Override
    public List<AirlineEmployee> findByRole(AirlineRole role) {
        if (role == null) return List.of();
        return repo().findAll().stream()
                .filter(e -> e.getRole() == role)
                .collect(Collectors.toList());
    }

    @Override
    public List<AirlineEmployee> findByAnyRole(Set<AirlineRole> roles) {
        if (roles == null || roles.isEmpty()) return List.of();
        // Normalize to EnumSet for speed/safety
        EnumSet<AirlineRole> set = (roles instanceof EnumSet<AirlineRole> es)
                ? es
                : EnumSet.copyOf(roles);
        return repo().findAll().stream()
                .filter(e -> e.getRole() != null && set.contains(e.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AirlineEmployee> findByAssignmentId(String assignmentId) {
        if (assignmentId == null || assignmentId.isBlank()) return List.of();
        return repo().findAll().stream()
                .filter(e -> e.getAssignments() != null
                        && e.getAssignments().stream()
                        .map(FlightAssignment::getId)
                        .anyMatch(assignmentId::equals))
                .collect(Collectors.toList());
    }
}
