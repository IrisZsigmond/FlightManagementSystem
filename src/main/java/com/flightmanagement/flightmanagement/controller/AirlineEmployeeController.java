package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.AirlineEmployeeForm;
import com.flightmanagement.flightmanagement.mapper.AirlineEmployeeMapper;
import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;
import com.flightmanagement.flightmanagement.service.AirlineEmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/airlineemployees")
public class AirlineEmployeeController {

    private final AirlineEmployeeService employeeService;
    private final AirlineEmployeeMapper employeeMapper;

    public AirlineEmployeeController(AirlineEmployeeService employeeService,
                                     AirlineEmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    // LIST
    @GetMapping
    public String index(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "airlineemployees/index";
    }

    // CREATE - form
    @GetMapping("/new")
    public String form(Model model) {
        if (!model.containsAttribute("airlineEmployeeForm")) {
            model.addAttribute("airlineEmployeeForm", new AirlineEmployeeForm());
        }
        model.addAttribute("roles", AirlineRole.values());
        return "airlineemployees/new";
    }

    // CREATE - submit
    @PostMapping
    public String create(
            @Valid @ModelAttribute("airlineEmployeeForm") AirlineEmployeeForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        if (result.hasErrors()) {
            model.addAttribute("roles", AirlineRole.values());
            return "airlineemployees/new";
        }

        try {
            AirlineEmployee employee = employeeMapper.toEntity(form);
            employeeService.save(employee);

            ra.addFlashAttribute("success", "Airline employee created.");
            return "redirect:/airlineemployees";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage() : "Could not create airline employee.";

            if (msg.contains("id")) {
                result.rejectValue("id", "duplicate", msg);
            } else {
                result.reject("globalError", msg);
            }

            model.addAttribute("roles", AirlineRole.values());
            return "airlineemployees/new";
        }
    }


    // EDIT - form
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id,
                       Model model,
                       RedirectAttributes ra) {
        try {
            AirlineEmployee employee = employeeService.getById(id);
            AirlineEmployeeForm form = employeeMapper.toForm(employee);

            model.addAttribute("employeeForm", form);
            model.addAttribute("employeeId", id);
            model.addAttribute("employee", employee);
            model.addAttribute("roles", AirlineRole.values());

            return "airlineemployees/edit";

        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/airlineemployees";
        }
    }

    // EDIT - submit
    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("employeeForm") AirlineEmployeeForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        if (result.hasErrors()) {
            AirlineEmployee employee = employeeService.getById(id);
            model.addAttribute("employeeId", id);
            model.addAttribute("employee", employee);
            model.addAttribute("roles", AirlineRole.values());
            return "airlineemployees/edit";
        }

        try {
            AirlineEmployee existing = employeeService.getById(id);
            employeeMapper.updateEntityFromForm(existing, form);

            employeeService.update(id, existing);

            ra.addFlashAttribute("success", "Airline employee updated.");
            return "redirect:/airlineemployees";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage() : "Could not update airline employee.";

            result.reject("globalError", msg);

            AirlineEmployee employee = employeeService.getById(id);
            model.addAttribute("employeeId", id);
            model.addAttribute("employee", employee);
            model.addAttribute("roles", AirlineRole.values());

            return "airlineemployees/edit";
        }
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id,
                         RedirectAttributes ra) {
        try {
            boolean deleted = employeeService.delete(id);
            if (deleted) {
                ra.addFlashAttribute("success", "Airline employee deleted.");
            } else {
                ra.addFlashAttribute("error", "Airline employee not found or already deleted.");
            }
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/airlineemployees";
    }

    // VIEW
    @GetMapping("/{id}")
    public String view(@PathVariable String id,
                       Model model,
                       RedirectAttributes ra) {
        try {
            AirlineEmployee employee = employeeService.getById(id);
            model.addAttribute("employee", employee);
            return "airlineemployees/view";
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/airlineemployees";
        }
    }
}
