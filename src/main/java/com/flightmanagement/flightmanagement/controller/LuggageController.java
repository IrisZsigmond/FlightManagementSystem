package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.LuggageSize;
import com.flightmanagement.flightmanagement.model.LuggageStatus;
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

    // ✅ GET all – afișează toate bagajele existente
    @GetMapping
    public String index(Model model) {
        model.addAttribute("luggageList", luggageService.findAll());
        return "luggage/index";
    }

    // ✅ GET /new – afișează formularul de creare
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("luggage", new Luggage());
        // pentru dropdown-uri (status și size)
        model.addAttribute("statuses", LuggageStatus.values());
        model.addAttribute("sizes", LuggageSize.values());
        return "luggage/form";
    }

    // ✅ POST – salvează un nou bagaj
    @PostMapping
    public String create(@ModelAttribute Luggage luggage) {
        luggageService.save(luggage);
        return "redirect:/luggage";
    }

    // ✅ POST /{id}/delete – șterge un bagaj după ID
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        luggageService.delete(id);
        return "redirect:/luggage";
    }
}
