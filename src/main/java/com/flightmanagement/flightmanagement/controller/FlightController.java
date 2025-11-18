package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.FlightForm;
import com.flightmanagement.flightmanagement.mapper.FlightMapper;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final FlightMapper flightMapper;

    public FlightController(FlightService flightService,
                            FlightMapper flightMapper) {
        this.flightService = flightService;
        this.flightMapper = flightMapper;
    }

    @GetMapping
    public String index(Model model) {
        var flights = flightService.findAll().stream()
                .map(f -> flightService.findWithTicketsAndAssignments(f.getId()).orElse(f))
                .toList();

        model.addAttribute("flights", flights);
        return "flights/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("flightForm", new FlightForm());
        return "flights/new";
    }

    @PostMapping
    public String create(@ModelAttribute("flightForm") FlightForm form,
                         RedirectAttributes ra) {
        Flight flight = flightMapper.toEntity(form);
        flightService.save(flight);
        ra.addFlashAttribute("success", "Flight created.");
        return "redirect:/flights";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id,
                         RedirectAttributes ra) {
        try {
            flightService.delete(id);
            ra.addFlashAttribute("success", "Flight deleted.");
        } catch (IllegalStateException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/flights";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Flight flight = flightService.findWithTicketsAndAssignments(id).orElseThrow();
        FlightForm form = flightMapper.toForm(flight);

        model.addAttribute("flightForm", form);
        model.addAttribute("flight", flight);
        return "flights/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("flightForm") FlightForm form,
                         RedirectAttributes ra) {

        Flight existing = flightService.findById(id).orElseThrow();
        flightMapper.updateEntityFromForm(existing, form);
        flightService.update(id, existing);

        ra.addFlashAttribute("success", "Flight updated.");
        return "redirect:/flights";
    }
}
