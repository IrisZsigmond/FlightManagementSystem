package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.service.PassengerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("passengers", passengerService.findAll());
        return "passengers/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("passenger",
                new Passenger(null, null, null, new ArrayList<>()));
        return "passengers/new";
    }

    @PostMapping
    public String create(@ModelAttribute Passenger passenger) {
        passengerService.save(passenger);
        return "redirect:/passengers";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Passenger passenger = passengerService.findById(id).orElseThrow();
        model.addAttribute("passenger", passenger);
        return "passengers/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Passenger passenger) {
        passengerService.update(id, passenger);
        return "redirect:/passengers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        passengerService.delete(id);
        return "redirect:/passengers";
    }
}
