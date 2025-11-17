package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.model.AirportEmployee;
import com.flightmanagement.flightmanagement.service.AirportEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airportemployees")
public class AirportEmployeeController {

    private final AirportEmployeeService airportEmployeeService;

    public AirportEmployeeController(AirportEmployeeService airportEmployeeService) {
        this.airportEmployeeService = airportEmployeeService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("employees", airportEmployeeService.findAll());
        return "airportemployees/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("employee",
                new AirportEmployee(null, null, null, null));
        return "airportemployees/new";
    }

    @PostMapping
    public String create(@ModelAttribute AirportEmployee employee) {
        airportEmployeeService.save(employee);
        return "redirect:/airportemployees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        airportEmployeeService.delete(id);
        return "redirect:/airportemployees";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        AirportEmployee employee = airportEmployeeService.findById(id).orElseThrow();
        model.addAttribute("employee", employee);
        return "airportemployees/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute AirportEmployee employee) {
        airportEmployeeService.update(id, employee);
        return "redirect:/airportemployees";
    }
}
