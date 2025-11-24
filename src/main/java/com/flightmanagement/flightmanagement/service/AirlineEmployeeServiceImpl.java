package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AirlineEmployeeServiceImpl extends BaseServiceImpl<AirlineEmployee, String>
        implements AirlineEmployeeService {

    private final FlightAssignmentService flightAssignmentService;

    public AirlineEmployeeServiceImpl(AbstractRepository<AirlineEmployee, String> repository,
                                      FlightAssignmentService flightAssignmentService) {
        super(repository);
        this.flightAssignmentService = flightAssignmentService;
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

        Optional<FlightAssignment> assignmentOpt = flightAssignmentService.findById(assignmentId);
        if (assignmentOpt.isEmpty()) {
            return List.of();
        }

        String staffId = assignmentOpt.get().getStaffId();
        return repo().findById(staffId)
                .map(List::of)
                .orElseGet(List::of);
    }

    @Override
    public Optional<AirlineEmployee> findWithAssignments(String id) {
        return repo().findById(id).map(e -> {
            e.setAssignments(flightAssignmentService.findByStaffId(id));
            return e;
        });
    }

    @Override
    public boolean delete(String id) {
        var assignments = flightAssignmentService.findByStaffId(id);
        if (!assignments.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete airline employee '" + id +
                            "' because assignments still exist (" + assignments.size() + ").");
        }
        return super.delete(id);
    }
}
