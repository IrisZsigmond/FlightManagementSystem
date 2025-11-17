package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.service.FlightAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/flightassignments")
public class FlightAssignmentController {

    private final FlightAssignmentService flightAssignmentService;

    public FlightAssignmentController(FlightAssignmentService flightAssignmentService) {
        this.flightAssignmentService = flightAssignmentService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("assignments", flightAssignmentService.findAll());
        return "flightassignments/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("assignment", new FlightAssignment(null, null, null));
        return "flightassignments/new";
    }

    @PostMapping
    public String create(@ModelAttribute FlightAssignment assignment) {
        flightAssignmentService.save(assignment);
        return "redirect:/flightassignments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        flightAssignmentService.delete(id);
        return "redirect:/flightassignments";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        FlightAssignment assignment = flightAssignmentService.findById(id).orElseThrow();
        model.addAttribute("assignment", assignment);
        return "flightassignments/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute FlightAssignment assignment) {
        flightAssignmentService.update(id, assignment);
        return "redirect:/flightassignments";
    }
}
