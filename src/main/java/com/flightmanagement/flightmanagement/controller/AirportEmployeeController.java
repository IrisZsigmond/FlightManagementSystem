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

    private final AirportEmployeeService airportEmployeeService;
    private final AirportEmployeeMapper airportEmployeeMapper;

    public AirportEmployeeController(AirportEmployeeService airportEmployeeService,
                                     AirportEmployeeMapper airportEmployeeMapper) {
        this.airportEmployeeService = airportEmployeeService;
        this.airportEmployeeMapper = airportEmployeeMapper;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("employees", airportEmployeeService.findAll());
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
        AirportEmployee e = airportEmployeeMapper.toEntity(form);
        airportEmployeeService.save(e);
        ra.addFlashAttribute("success", "Airport employee created.");
        return "redirect:/airportemployees";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        AirportEmployee e = airportEmployeeService.findById(id).orElseThrow();
        AirportEmployeeForm form = airportEmployeeMapper.toForm(e);

        model.addAttribute("airportEmployeeForm", form);
        model.addAttribute("employee", e);
        return "airportemployees/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("airportEmployeeForm") AirportEmployeeForm form,
                         RedirectAttributes ra) {

        AirportEmployee existing = airportEmployeeService.findById(id).orElseThrow();
        airportEmployeeMapper.updateEntityFromForm(existing, form);
        airportEmployeeService.update(id, existing);

        ra.addFlashAttribute("success", "Airport employee updated.");
        return "redirect:/airportemployees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id,
                         RedirectAttributes ra) {
        airportEmployeeService.delete(id);
        ra.addFlashAttribute("success", "Airport employee deleted.");
        return "redirect:/airportemployees";
    }
}
