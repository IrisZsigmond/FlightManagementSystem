package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.AirplaneForm;
import com.flightmanagement.flightmanagement.mapper.AirplaneMapper;
import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.service.AirplaneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/airplanes")
public class AirplaneController {

    private final AirplaneService airplaneService;
    private final AirplaneMapper airplaneMapper;

    public AirplaneController(AirplaneService airplaneService,
                              AirplaneMapper airplaneMapper) {
        this.airplaneService = airplaneService;
        this.airplaneMapper = airplaneMapper;
    }

    @GetMapping
    public String index(Model model) {
        var airplanes = airplaneService.findAll().stream()
                .map(a -> airplaneService.findAirplaneWithFlights(a.getId()).orElse(a))
                .toList();

        model.addAttribute("airplanes", airplanes);
        return "airplanes/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("airplaneForm", new AirplaneForm());
        // No flights list here; flights are assigned only from the Flight side
        return "airplanes/new";
    }

    @PostMapping
    public String create(@ModelAttribute("airplaneForm") AirplaneForm form,
                         RedirectAttributes ra) {
        Airplane airplane = airplaneMapper.toEntity(form);
        airplaneService.save(airplane);
        ra.addFlashAttribute("success", "Airplane created.");
        return "redirect:/airplanes";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            airplaneService.delete(id); // will throw if flights exist (block policy)
            ra.addFlashAttribute("success", "Airplane deleted.");
        } catch (IllegalStateException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/airplanes";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        // Load airplane and attach a read-only projection of its flights
        Airplane airplane = airplaneService.findAirplaneWithFlights(id).orElseThrow();

        // Populate the form with scalar fields only
        AirplaneForm form = airplaneMapper.toForm(airplane);

        model.addAttribute("airplaneForm", form);
        model.addAttribute("airplane", airplane); // contains airplane.flights for read-only display
        return "airplanes/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("airplaneForm") AirplaneForm form,
                         RedirectAttributes ra) {

        Airplane existing = airplaneService.findById(id).orElseThrow();
        airplaneMapper.updateEntityFromForm(existing, form);
        airplaneService.update(id, existing);

        ra.addFlashAttribute("success", "Airplane updated.");
        return "redirect:/airplanes";
    }
}
