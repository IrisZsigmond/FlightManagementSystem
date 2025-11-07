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
    public String form(Model model) {
        model.addAttribute("passenger", new Passenger(null, null, null, new ArrayList<>()));
        return "passengers/form";
    }

    @PostMapping
    public String create(@ModelAttribute Passenger passenger) {
        passengerService.save(passenger);
        return "redirect:/passengers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        passengerService.delete(id);
        return "redirect:/passengers";
    }
}
