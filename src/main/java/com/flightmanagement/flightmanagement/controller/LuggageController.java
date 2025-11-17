package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
import com.flightmanagement.flightmanagement.service.LuggageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/luggage")
public class LuggageController {

    private final LuggageService luggageService;

    public LuggageController(LuggageService luggageService) {
        this.luggageService = luggageService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("luggageList", luggageService.findAll());
        return "luggage/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("luggage", new Luggage(null, null, null, null));
        model.addAttribute("statuses", LuggageStatus.values());
        model.addAttribute("sizes", LuggageSize.values());
        return "luggage/new";
    }

    @PostMapping
    public String create(@ModelAttribute Luggage luggage) {
        luggageService.save(luggage);
        return "redirect:/luggage";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Luggage luggage = luggageService.findById(id).orElseThrow();
        model.addAttribute("luggage", luggage);
        model.addAttribute("statuses", LuggageStatus.values());
        model.addAttribute("sizes", LuggageSize.values());
        return "luggage/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Luggage luggage) {
        luggageService.update(id, luggage);
        return "redirect:/luggage";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        luggageService.delete(id);
        return "redirect:/luggage";
    }
}
