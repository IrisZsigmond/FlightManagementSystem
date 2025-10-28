package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.AirlineRole;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AirlineEmployeeServiceImpl implements AirlineEmployeeService {

    private final AbstractRepository<AirlineEmployee, String> repository;

    public AirlineEmployeeServiceImpl(AbstractRepository<AirlineEmployee, String> repository) {
        this.repository = repository;
    }


    @Override
    public AirlineEmployee save(AirlineEmployee entity) {
        return null;
    }

    @Override
    public List<AirlineEmployee> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<AirlineEmployee> findById(String id) {
        return repository.findById(id);
    }


    @Override
    public AirlineEmployee update(String id, AirlineEmployee updatedEntity) {
        boolean success = repository.update(id, updatedEntity);
        if (!success) {
            try {
                throw new Exception("AirlineEmployee not found: " + id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return updatedEntity;
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }


    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void clear() {
        repository.clear();
    }

    @Override
    public List<AirlineEmployee> findByRole(AirlineRole role) {
        if (role == null) return List.of();
        return repository.findAll().stream()
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
        return repository.findAll().stream()
                .filter(e -> e.getRole() != null && set.contains(e.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AirlineEmployee> findByAssignmentId(String assignmentId) {
        if (assignmentId == null || assignmentId.isBlank()) return List.of();
        return repository.findAll().stream()
                .filter(e -> e.getAssignments() != null
                        && e.getAssignments().stream()
                        .map(FlightAssignment::getId)
                        .anyMatch(assignmentId::equals))
                .collect(Collectors.toList());
    }
}
