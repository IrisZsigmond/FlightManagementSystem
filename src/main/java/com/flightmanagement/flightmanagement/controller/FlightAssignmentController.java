package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.FlightAssignmentForm;
import com.flightmanagement.flightmanagement.mapper.FlightAssignmentMapper;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.service.FlightAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/flightassignments")
public class FlightAssignmentController {

    private final FlightAssignmentService flightAssignmentService;
    private final FlightAssignmentMapper flightAssignmentMapper;

    public FlightAssignmentController(FlightAssignmentService flightAssignmentService,
                                      FlightAssignmentMapper flightAssignmentMapper) {
        this.flightAssignmentService = flightAssignmentService;
        this.flightAssignmentMapper = flightAssignmentMapper;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("assignments", flightAssignmentService.findAll());
        return "flightassignments/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("assignmentForm", new FlightAssignmentForm());
        return "flightassignments/new";
    }

    @PostMapping
    public String create(@ModelAttribute("assignmentForm") FlightAssignmentForm form) {
        FlightAssignment assignment = flightAssignmentMapper.toEntity(form);
        flightAssignmentService.save(assignment);
        return "redirect:/flightassignments";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        FlightAssignment assignment = flightAssignmentService.findById(id).orElseThrow();
        FlightAssignmentForm form = flightAssignmentMapper.toForm(assignment);

        model.addAttribute("assignmentForm", form);
        model.addAttribute("assignment", assignment); // dacă vrei să afișezi read-only ceva în viitor
        return "flightassignments/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("assignmentForm") FlightAssignmentForm form) {

        FlightAssignment existing = flightAssignmentService.findById(id).orElseThrow();
        flightAssignmentMapper.updateEntityFromForm(existing, form);
        flightAssignmentService.update(id, existing);

        return "redirect:/flightassignments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        flightAssignmentService.delete(id);
        return "redirect:/flightassignments";
    }
}
