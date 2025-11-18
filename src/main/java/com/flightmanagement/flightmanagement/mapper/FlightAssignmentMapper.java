package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.FlightAssignmentForm;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import org.springframework.stereotype.Component;

/**
 * Mapper between FlightAssignment entity and FlightAssignmentForm DTO.
 */
@Component
public class FlightAssignmentMapper {

    public FlightAssignment toEntity(FlightAssignmentForm form) {
        if (form == null) return null;

        FlightAssignment assignment = new FlightAssignment();
        assignment.setId(form.getId());
        assignment.setFlightId(form.getFlightId());
        assignment.setStaffId(form.getStaffId());
        return assignment;
    }

    public FlightAssignmentForm toForm(FlightAssignment assignment) {
        if (assignment == null) return null;

        FlightAssignmentForm form = new FlightAssignmentForm();
        form.setId(assignment.getId());
        form.setFlightId(assignment.getFlightId());
        form.setStaffId(assignment.getStaffId());
        return form;
    }

    public void updateEntityFromForm(FlightAssignment existing, FlightAssignmentForm form) {
        if (existing == null || form == null) return;

        existing.setFlightId(form.getFlightId());
        existing.setStaffId(form.getStaffId());
    }
}
