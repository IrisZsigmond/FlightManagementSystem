package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flights;

    public FlightController(FlightService flights) {
        this.flights = flights;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("flights", flights.findAll());
        return "flights/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("flight", new Flight(null, null, null, 0, null, null));
        return "flights/new";
    }

    @PostMapping
    public String create(@ModelAttribute Flight flight) {
        flights.save(flight);
        return "redirect:/flights";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        flights.delete(id);
        return "redirect:/flights";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Flight flight = flights.findById(id).orElseThrow();
        model.addAttribute("flight", flight);
        return "flights/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Flight flight) {
        flights.update(id, flight);
        return "redirect:/flights";
    }
}
