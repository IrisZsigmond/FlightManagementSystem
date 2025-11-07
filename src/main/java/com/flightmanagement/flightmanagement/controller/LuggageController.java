package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.LuggageSize;
import com.flightmanagement.flightmanagement.model.LuggageStatus;
import com.flightmanagement.flightmanagement.service.LuggageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * MVC controller responsible for handling HTTP requests related to Luggage.
 * <p>
 * Provides form pages and CRUD operations at the endpoint path: /luggage
 */
@Controller
@RequestMapping("/luggage")
public class LuggageController {

    private final LuggageService luggageService;

    /**
     * Constructor based dependency injection.
     */
    public LuggageController(LuggageService luggageService) {
        this.luggageService = luggageService;
    }

    /**
     * GET /luggage
     * <p>
     * Displays all existing luggage items in the system.
     */
    @GetMapping
    public String index(Model model) {
        model.addAttribute("luggageList", luggageService.findAll());
        return "luggage/index";
    }

    /**
     * GET /luggage/new
     * <p>
     * Renders the form page for creating a new luggage entry.
     */
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("luggage", new Luggage());
        model.addAttribute("statuses", LuggageStatus.values());
        model.addAttribute("sizes", LuggageSize.values());
        return "luggage/form";
    }

    /**
     * POST /luggage
     * <p>
     * Saves a new luggage entity to the system.
     */
    @PostMapping
    public String create(@ModelAttribute Luggage luggage) {
        luggageService.save(luggage);
        return "redirect:/luggage";
    }

    /**
     * POST /luggage/{id}/delete
     * <p>
     * Deletes a luggage item by ID.
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        luggageService.delete(id);
        return "redirect:/luggage";
    }
}
