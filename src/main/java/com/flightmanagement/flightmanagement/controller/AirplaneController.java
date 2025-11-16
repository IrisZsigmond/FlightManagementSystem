package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.AirplaneForm;
import com.flightmanagement.flightmanagement.mapper.AirplaneMapper;
import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.service.AirplaneService;
import com.flightmanagement.flightmanagement.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airplanes")
public class AirplaneController {

    private final AirplaneService airplaneService;
    private final FlightService flightService;
    private final AirplaneMapper airplaneMapper;

    public AirplaneController(AirplaneService airplaneService,
                              FlightService flightService,
                              AirplaneMapper airplaneMapper) {
        this.airplaneService = airplaneService;
        this.flightService = flightService;
        this.airplaneMapper = airplaneMapper;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("airplanes", airplaneService.findAll());
        return "airplanes/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("airplaneForm", new AirplaneForm());
        model.addAttribute("allFlights", flightService.findAll());
        return "airplanes/new";
    }

    @PostMapping
    public String create(@ModelAttribute("airplaneForm") AirplaneForm form) {
        Airplane airplane = airplaneMapper.toEntity(form);
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
        AirplaneForm form = airplaneMapper.toForm(airplane);

        model.addAttribute("airplaneForm", form);
        model.addAttribute("allFlights", flightService.findAll());
        return "airplanes/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("airplaneForm") AirplaneForm form) {

        Airplane existing = airplaneService.findById(id).orElseThrow();
        airplaneMapper.updateEntityFromForm(existing, form);
        airplaneService.update(id, existing);

        return "redirect:/airplanes";
    }
}
