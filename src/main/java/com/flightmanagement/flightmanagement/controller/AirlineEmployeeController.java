package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.AirlineEmployeeForm;
import com.flightmanagement.flightmanagement.mapper.AirlineEmployeeMapper;
import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;
import com.flightmanagement.flightmanagement.service.AirlineEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/airlineemployees")
public class AirlineEmployeeController {

    private final AirlineEmployeeService airlineEmployeeService;
    private final AirlineEmployeeMapper airlineEmployeeMapper;

    public AirlineEmployeeController(AirlineEmployeeService airlineEmployeeService,
                                     AirlineEmployeeMapper airlineEmployeeMapper) {
        this.airlineEmployeeService = airlineEmployeeService;
        this.airlineEmployeeMapper = airlineEmployeeMapper;
    }

    @GetMapping
    public String index(Model model) {
        var employees = airlineEmployeeService.findAll().stream()
                .map(e -> airlineEmployeeService.findWithAssignments(e.getId()).orElse(e))
                .toList();

        model.addAttribute("employees", employees);
        return "airlineemployees/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("airlineEmployeeForm", new AirlineEmployeeForm());
        model.addAttribute("roles", AirlineRole.values());
        return "airlineemployees/new";
    }

    @PostMapping
    public String create(@ModelAttribute("airlineEmployeeForm") AirlineEmployeeForm form,
                         RedirectAttributes ra) {
        AirlineEmployee employee = airlineEmployeeMapper.toEntity(form);
        airlineEmployeeService.save(employee);
        ra.addFlashAttribute("success", "Airline employee created.");
        return "redirect:/airlineemployees";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        AirlineEmployee employee = airlineEmployeeService.findWithAssignments(id).orElseThrow();
        AirlineEmployeeForm form = airlineEmployeeMapper.toForm(employee);

        model.addAttribute("airlineEmployeeForm", form);
        model.addAttribute("employee", employee); // are lista de assignments pentru afișare
        model.addAttribute("roles", AirlineRole.values());
        return "airlineemployees/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("airlineEmployeeForm") AirlineEmployeeForm form,
                         RedirectAttributes ra) {

        AirlineEmployee existing = airlineEmployeeService.findById(id).orElseThrow();
        airlineEmployeeMapper.updateEntityFromForm(existing, form);
        airlineEmployeeService.update(id, existing);

        ra.addFlashAttribute("success", "Airline employee updated.");
        return "redirect:/airlineemployees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id,
                         RedirectAttributes ra) {
        try {
            airlineEmployeeService.delete(id);
            ra.addFlashAttribute("success", "Airline employee deleted.");
        } catch (IllegalStateException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/airlineemployees";
    }
}
