package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.service.AirplaneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/airplanes")
public class AirplaneController {

    private final AirplaneService airplaneService;

    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("airplanes", airplaneService.findAll());
        return "airplanes/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("airplane", new Airplane(null, 0, 0, new ArrayList<>()));
        return "airplanes/new";
    }


    @PostMapping
    public String create(@ModelAttribute Airplane airplane) {
        airplaneService.save(airplane);
        return "redirect:/airplanes";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        airplaneService.delete(id);
        return "redirect:/airplanes";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Airplane airplane = airplaneService.findById(id).orElseThrow();

        // Precompute CSV from existing flights
        String csv = (airplane.getFlights() == null) ? "" :
                airplane.getFlights().stream()
                        .filter(f -> f != null && f.getId() != null)
                        .map(f -> f.getId())
                        .collect(java.util.stream.Collectors.joining(", "));

        airplane.setFlightsCsv(csv);   // <= put it on the form-backing object
        model.addAttribute("airplane", airplane);
        return "airplanes/edit";
    }


    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Airplane airplane) {
        airplaneService.update(id, airplane);
        return "redirect:/airplanes";
    }
}


