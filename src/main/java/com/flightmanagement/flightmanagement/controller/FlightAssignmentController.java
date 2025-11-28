package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.FlightAssignmentForm;
import com.flightmanagement.flightmanagement.mapper.FlightAssignmentMapper;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.service.FlightAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/flightassignments")
public class FlightAssignmentController {

    private final FlightAssignmentService service;
    private final FlightAssignmentMapper mapper;

    public FlightAssignmentController(
            FlightAssignmentService service,
            FlightAssignmentMapper mapper
    ) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("assignments", service.findAll());
        return "flightassignments/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("assignmentForm", new FlightAssignmentForm());
        return "flightassignments/new";
    }

    @PostMapping
    public String create(
            @ModelAttribute("assignmentForm") FlightAssignmentForm form,
            RedirectAttributes ra
    ) {
        FlightAssignment a = mapper.toEntity(form);
        service.save(a);

        ra.addFlashAttribute("success", "Assignment created.");
        return "redirect:/flightassignments";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        FlightAssignment a = service.findById(id).orElseThrow();
        model.addAttribute("assignmentForm", mapper.toForm(a));
        model.addAttribute("assignment", a);
        return "flightassignments/edit";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @ModelAttribute("assignmentForm") FlightAssignmentForm form,
            RedirectAttributes ra
    ) {
        FlightAssignment existing = service.findById(id).orElseThrow();
        mapper.updateEntityFromForm(existing, form);
        service.update(id, existing);

        ra.addFlashAttribute("success", "Assignment updated.");
        return "redirect:/flightassignments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("success", "Assignment deleted.");
        } catch (Exception ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/flightassignments";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        FlightAssignment assignment = service.findById(id).orElseThrow();
        model.addAttribute("assignment", assignment);
        return "flightassignments/view";
    }

}
