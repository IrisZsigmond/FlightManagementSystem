package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.AirlineEmployeeForm;
import com.flightmanagement.flightmanagement.mapper.AirlineEmployeeMapper;
import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.service.AirlineEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/airline-employees")
public class AirlineEmployeeController {

    private final AirlineEmployeeService service;
    private final AirlineEmployeeMapper mapper;

    public AirlineEmployeeController(AirlineEmployeeService service,
                                     AirlineEmployeeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("employees", service.findAll());
        return "airline_employees/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("airlineEmployeeForm", new AirlineEmployeeForm());
        return "airline_employees/new";
    }

    @PostMapping
    public String create(@ModelAttribute("airlineEmployeeForm") AirlineEmployeeForm form,
                         RedirectAttributes ra) {

        AirlineEmployee e = mapper.toEntity(form);
        service.save(e);

        ra.addFlashAttribute("success", "Airline employee created.");
        return "redirect:/airline-employees";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        AirlineEmployee e = service.findById(id).orElseThrow();
        model.addAttribute("airlineEmployeeForm", mapper.toForm(e));
        model.addAttribute("employee", e);
        return "airline_employees/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("airlineEmployeeForm") AirlineEmployeeForm form,
                         RedirectAttributes ra) {

        AirlineEmployee existing = service.findById(id).orElseThrow();
        mapper.updateEntityFromForm(existing, form);
        service.update(id, existing);

        ra.addFlashAttribute("success", "Airline employee updated.");
        return "redirect:/airline-employees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id,
                         RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("success", "Airline employee deleted.");
        } catch (Exception ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/airline-employees";
    }
}
