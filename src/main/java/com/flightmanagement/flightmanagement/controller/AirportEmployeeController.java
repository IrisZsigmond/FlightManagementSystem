package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.AirportEmployeeForm;
import com.flightmanagement.flightmanagement.mapper.AirportEmployeeMapper;
import com.flightmanagement.flightmanagement.model.AirportEmployee;
import com.flightmanagement.flightmanagement.service.AirportEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/airportemployees")
public class AirportEmployeeController {

    private final AirportEmployeeService service;
    private final AirportEmployeeMapper mapper;

    public AirportEmployeeController(AirportEmployeeService service,
                                     AirportEmployeeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("employees", service.findAll());
        return "airportemployees/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("airportEmployeeForm", new AirportEmployeeForm());
        return "airportemployees/new";
    }

    @PostMapping
    public String create(@ModelAttribute("airportEmployeeForm") AirportEmployeeForm form,
                         RedirectAttributes ra) {

        AirportEmployee e = mapper.toEntity(form);
        service.save(e);

        ra.addFlashAttribute("success", "Airport employee created.");
        return "redirect:/airportemployees";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        AirportEmployee e = service.findById(id).orElseThrow();
        model.addAttribute("airportEmployeeForm", mapper.toForm(e));
        model.addAttribute("employee", e);
        return "airportemployees/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("airportEmployeeForm") AirportEmployeeForm form,
                         RedirectAttributes ra) {

        AirportEmployee existing = service.findById(id).orElseThrow();
        mapper.updateEntityFromForm(existing, form);
        service.update(id, existing);

        ra.addFlashAttribute("success", "Airport employee updated.");
        return "redirect:/airportemployees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id,
                         RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("success", "Airport employee deleted.");
        } catch (Exception ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/airportemployees";
    }
}

