package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.PassengerForm;
import com.flightmanagement.flightmanagement.mapper.PassengerMapper;
import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.service.PassengerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;

    public PassengerController(PassengerService passengerService,
                               PassengerMapper passengerMapper) {
        this.passengerService = passengerService;
        this.passengerMapper = passengerMapper;
    }

    @GetMapping
    public String index(Model model) {
        var passengers = passengerService.findAll().stream()
                .map(p -> passengerService.findWithTickets(p.getId()).orElse(p))
                .toList();

        model.addAttribute("passengers", passengers);
        return "passengers/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("passengerForm", new PassengerForm());
        return "passengers/new";
    }

    @PostMapping
    public String create(@ModelAttribute("passengerForm") PassengerForm form,
                         RedirectAttributes ra) {
        Passenger passenger = passengerMapper.toEntity(form);
        passengerService.save(passenger);
        ra.addFlashAttribute("success", "Passenger created.");
        return "redirect:/passengers";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Passenger passenger = passengerService.findWithTickets(id).orElseThrow();
        PassengerForm form = passengerMapper.toForm(passenger);

        model.addAttribute("passengerForm", form);  // editabile
        model.addAttribute("passenger", passenger); // conține lista de tickets pentru display
        return "passengers/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("passengerForm") PassengerForm form,
                         RedirectAttributes ra) {

        Passenger existing = passengerService.findById(id).orElseThrow();
        passengerMapper.updateEntityFromForm(existing, form);
        passengerService.update(id, existing);

        ra.addFlashAttribute("success", "Passenger updated.");
        return "redirect:/passengers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id,
                         RedirectAttributes ra) {
        try {
            passengerService.delete(id);
            ra.addFlashAttribute("success", "Passenger deleted.");
        } catch (IllegalStateException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/passengers";
    }
}
