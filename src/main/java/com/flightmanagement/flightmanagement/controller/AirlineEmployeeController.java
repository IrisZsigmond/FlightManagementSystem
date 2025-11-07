package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;
import com.flightmanagement.flightmanagement.service.AirlineEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/airlineemployees")
public class AirlineEmployeeController {

    private final AirlineEmployeeService airlineEmployeeService;

    public AirlineEmployeeController(AirlineEmployeeService airlineEmployeeService) {
        this.airlineEmployeeService = airlineEmployeeService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("employees", airlineEmployeeService.findAll());
        return "airlineemployees/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("employee",
                new AirlineEmployee(null, null, null, new ArrayList<>()));
        model.addAttribute("roles", AirlineRole.values());
        return "airlineemployees/form";
    }

    @PostMapping
    public String create(@ModelAttribute AirlineEmployee employee) {
        airlineEmployeeService.save(employee);
        return "redirect:/airlineemployees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        airlineEmployeeService.delete(id);
        return "redirect:/airlineemployees";
    }
}
