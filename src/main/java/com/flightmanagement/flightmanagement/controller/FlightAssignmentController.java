package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.FlightAssignmentForm;
import com.flightmanagement.flightmanagement.mapper.FlightAssignmentMapper;
import com.flightmanagement.flightmanagement.model.FlightAssignment;
import com.flightmanagement.flightmanagement.service.FlightAssignmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Sort;


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

    // LIST + SORT + FILTRE (INDEX)
    @GetMapping
    public String index(
            Model model,
            // NOU: Parametrii de filtrare
            @RequestParam(required = false) String filterFlightId,
            @RequestParam(required = false) String filterEmployeeId,

            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String dir
    ) {
        // 1. Whitelisting și pregătirea Sort
        String sortProperty = switch (sort) {
            case "id" -> "id";
            case "flight" -> "flight.id"; // Sortare pe relație
            case "employee" -> "airlineEmployee.id"; // Sortare pe relație
            default -> "id";
        };

        Sort.Direction direction =
                dir.equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        Sort sortObj = Sort.by(direction, sortProperty);

        // 2. FILTRARE + SORTARE (Apel la metoda search din Service)
        model.addAttribute("assignments", service.search(filterFlightId, filterEmployeeId, sortObj));

        // 3. Variabile pentru UI (păstrarea stării + toggle sort)
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("reverseDir", dir.equals("asc") ? "desc" : "asc");

        // NOU: Păstrarea valorilor filtrului în form
        model.addAttribute("filterFlightId", filterFlightId);
        model.addAttribute("filterEmployeeId", filterEmployeeId);

        return "flightassignments/index";
    }

    // CREATE - form
    @GetMapping("/new")
    public String form(Model model) {
        if (!model.containsAttribute("assignmentForm")) {
            model.addAttribute("assignmentForm", new FlightAssignmentForm());
        }
        return "flightassignments/new";
    }

    // CREATE - submit
    @PostMapping
    public String create(
            @Valid @ModelAttribute("assignmentForm") FlightAssignmentForm form,
            BindingResult result,
            RedirectAttributes ra,
            Model model
    ) {
        if (result.hasErrors()) { return "flightassignments/new"; }

        try {
            FlightAssignment a = mapper.toEntity(form);
            service.save(a);

            ra.addFlashAttribute("success", "Assignment created.");
            return "redirect:/flightassignments";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage() : "Could not create assignment.";
            if (msg.contains("id already exists") || msg.contains("Flight assignment id")) {
                result.rejectValue("id", "duplicate", msg);
            } else if (msg.contains("Flight not found") || msg.contains("Flight id")) {
                result.rejectValue("flightId", "notFound", msg);
            } else if (msg.contains("Airline employee not found") || msg.contains("employee id")) {
                result.rejectValue("airlineEmployeeId", "notFound", msg);
            } else if (msg.contains("already assigned")) {
                result.reject("globalError", msg);
            } else {
                result.reject("globalError", msg);
            }
            return "flightassignments/new";
        }
    }

    // EDIT - form
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model, RedirectAttributes ra) {
        try {
            FlightAssignment a = service.findById(id).orElseThrow();
            model.addAttribute("assignmentForm", mapper.toForm(a));
            model.addAttribute("assignmentId", id);
            model.addAttribute("assignment", a);
            return "flightassignments/edit";
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/flightassignments";
        }
    }

    // EDIT - submit
    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("assignmentForm") FlightAssignmentForm form,
            BindingResult result,
            RedirectAttributes ra,
            Model model
    ) {
        if (result.hasErrors()) {
            FlightAssignment a = service.findById(id).orElseThrow();
            model.addAttribute("assignmentId", id);
            model.addAttribute("assignment", a);
            return "flightassignments/edit";
        }

        try {
            FlightAssignment existing = service.findById(id).orElseThrow();
            mapper.updateEntityFromForm(existing, form);
            service.update(id, existing);

            ra.addFlashAttribute("success", "Assignment updated.");
            return "redirect:/flightassignments";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage() : "Could not update assignment.";
            if (msg.contains("Flight not found") || msg.contains("Flight id")) {
                result.rejectValue("flightId", "notFound", msg);
            } else if (msg.contains("Airline employee not found") || msg.contains("employee id")) {
                result.rejectValue("airlineEmployeeId", "notFound", msg);
            } else if (msg.contains("already assigned")) {
                result.reject("globalError", msg);
            } else {
                result.reject("globalError", msg);
            }

            FlightAssignment a = service.findById(id).orElseThrow();
            model.addAttribute("assignmentId", id);
            model.addAttribute("assignment", a);

            return "flightassignments/edit";
        }
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            boolean deleted = service.delete(id);
            if (deleted) {
                ra.addFlashAttribute("success", "Assignment deleted.");
            } else {
                ra.addFlashAttribute("error", "Assignment not found or already deleted.");
            }
        } catch (Exception ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/flightassignments";
    }

    // VIEW
    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model, RedirectAttributes ra) {
        try {
            FlightAssignment assignment = service.findById(id).orElseThrow();
            model.addAttribute("assignment", assignment);
            return "flightassignments/view";
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/flightassignments";
        }
    }
}