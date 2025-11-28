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
    private final PassengerMapper mapper;

    public PassengerController(PassengerService passengerService,
                               PassengerMapper mapper) {
        this.passengerService = passengerService;
        this.mapper = mapper;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("passengers", passengerService.findAll());
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

        Passenger p = mapper.toEntity(form);
        passengerService.save(p);

        ra.addFlashAttribute("success", "Passenger created.");
        return "redirect:/passengers";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Passenger p = passengerService.findById(id).orElseThrow();
        model.addAttribute("passengerForm", mapper.toForm(p));
        model.addAttribute("passenger", p);
        return "passengers/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("passengerForm") PassengerForm form,
                         RedirectAttributes ra) {

        Passenger existing = passengerService.findById(id).orElseThrow();
        mapper.updateEntityFromForm(existing, form);
        passengerService.update(id, existing);

        ra.addFlashAttribute("success", "Passenger updated.");
        return "redirect:/passengers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            passengerService.delete(id);
            ra.addFlashAttribute("success", "Passenger deleted.");
        } catch (Exception ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/passengers";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        Passenger passenger = passengerService.findById(id).orElseThrow();
        model.addAttribute("passenger", passenger);
        return "passengers/view";
    }
}
