package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.TicketForm;
import com.flightmanagement.flightmanagement.mapper.TicketMapper;
import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import com.flightmanagement.flightmanagement.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper mapper;

    public TicketController(TicketService ticketService,
                            TicketMapper mapper) {
        this.ticketService = ticketService;
        this.mapper = mapper;
    }

    // LIST + SORT + FILTER (INDEX)
    @GetMapping
    public String index(
            Model model,
            // Parametrii de filtrare
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) TicketCategory category,

            // Parametrii de sortare
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String dir
    ) {
        // 1. Whitelist Sort
        String sortProperty = switch (sort) {
            case "id" -> "id";
            case "category" -> "category";
            case "price" -> "price";
            case "seatNumber" -> "seatNumber";
            // Opțional, dacă vrei să sortezi după relații în viitor:
            case "passenger" -> "passenger.id";
            case "flight" -> "flight.id";
            default -> "id";
        };

        Sort.Direction direction = dir.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Sort sortObj = Sort.by(direction, sortProperty);

        // 2. Apel Service Search
        model.addAttribute("tickets", ticketService.search(minPrice, maxPrice, category, sortObj));

        // 3. UI Variables
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("reverseDir", dir.equals("asc") ? "desc" : "asc");

        // Păstrare filtre
        model.addAttribute("filterMinPrice", minPrice);
        model.addAttribute("filterMaxPrice", maxPrice);
        model.addAttribute("filterCategory", category);

        // Pentru dropdown-ul de categorii
        model.addAttribute("categories", TicketCategory.values());

        return "tickets/index";
    }

    // CREATE FORM
    @GetMapping("/new")
    public String form(Model model) {
        if (!model.containsAttribute("ticketForm")) {
            model.addAttribute("ticketForm", new TicketForm());
        }
        model.addAttribute("categories", TicketCategory.values());
        return "tickets/new";
    }

    // CREATE SUBMIT
    @PostMapping
    public String create(
            @Valid @ModelAttribute("ticketForm") TicketForm form,
            BindingResult result,
            RedirectAttributes ra,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("categories", TicketCategory.values());
            return "tickets/new";
        }

        try {
            Ticket ticket = mapper.toEntity(form);
            ticketService.save(ticket);
            ra.addFlashAttribute("success", "Ticket created.");
            return "redirect:/tickets";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage();
            if (msg.contains("ID")) {
                result.rejectValue("id", "duplicate", msg);
            } else if (msg.contains("Passenger")) {
                result.rejectValue("passengerId", "notFound", msg);
            } else if (msg.contains("Flight")) {
                result.rejectValue("flightId", "notFound", msg);
            } else if (msg.contains("Seat")) {
                result.rejectValue("seatNumber", "occupied", msg);
            } else {
                result.reject("globalError", msg);
            }
            model.addAttribute("categories", TicketCategory.values());
            return "tickets/new";
        }
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model, RedirectAttributes ra) {
        try {
            Ticket ticket = ticketService.findById(id).orElseThrow();
            model.addAttribute("ticketForm", mapper.toForm(ticket));
            model.addAttribute("ticket", ticket);
            model.addAttribute("categories", TicketCategory.values());
            return "tickets/edit";
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/tickets";
        }
    }

    // EDIT SUBMIT
    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("ticketForm") TicketForm form,
            BindingResult result,
            RedirectAttributes ra,
            Model model
    ) {
        if (result.hasErrors()) {
            Ticket t = ticketService.findById(id).orElseThrow();
            model.addAttribute("ticket", t);
            model.addAttribute("categories", TicketCategory.values());
            return "tickets/edit";
        }

        try {
            Ticket existing = ticketService.findById(id).orElseThrow();
            mapper.updateEntityFromForm(existing, form);
            ticketService.update(id, existing);

            ra.addFlashAttribute("success", "Ticket updated.");
            return "redirect:/tickets";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage();
            if (msg.contains("Passenger")) {
                result.rejectValue("passengerId", "notFound", msg);
            } else if (msg.contains("Flight")) {
                result.rejectValue("flightId", "notFound", msg);
            } else if (msg.contains("Seat")) {
                result.rejectValue("seatNumber", "occupied", msg);
            } else {
                result.reject("globalError", msg);
            }

            Ticket t = ticketService.findById(id).orElseThrow();
            model.addAttribute("ticket", t);
            model.addAttribute("categories", TicketCategory.values());
            return "tickets/edit";
        }
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            ticketService.delete(id);
            ra.addFlashAttribute("success", "Ticket deleted.");
        } catch (Exception ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/tickets";
    }

    // VIEW
    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model, RedirectAttributes ra) {
        try {
            Ticket ticket = ticketService.findById(id).orElseThrow();
            model.addAttribute("ticket", ticket);
            return "tickets/view";
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/tickets";
        }
    }
}