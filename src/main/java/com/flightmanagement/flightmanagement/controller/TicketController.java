package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.TicketForm;
import com.flightmanagement.flightmanagement.mapper.TicketMapper;
import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import com.flightmanagement.flightmanagement.service.TicketService;
import jakarta.validation.Valid;
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

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tickets", ticketService.findAll());
        return "tickets/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("ticketForm", new TicketForm());
        model.addAttribute("categories", TicketCategory.values());
        return "tickets/new";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("ticketForm") TicketForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        if (result.hasErrors()) {
            model.addAttribute("categories", TicketCategory.values());
            return "tickets/new";
        }

        try {
            Ticket t = mapper.toEntity(form);
            ticketService.save(t);

            ra.addFlashAttribute("success", "Ticket created.");
            return "redirect:/tickets";

        } catch (IllegalArgumentException | IllegalStateException ex) {

            String msg = ex.getMessage();

            if (msg.contains("ID")) {
                result.rejectValue("id", "duplicate", msg);
            }
            else if (msg.contains("Passenger")) {
                result.rejectValue("passengerId", "invalid", msg);
            }
            else if (msg.contains("Flight")) {
                result.rejectValue("flightId", "invalid", msg);
            }
            else if (msg.contains("Seat")) {
                result.rejectValue("seatNumber", "duplicate", msg);
            }
            else {
                result.reject("globalError", msg);
            }

            model.addAttribute("categories", TicketCategory.values());
            return "tickets/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {

        Ticket ticket = ticketService.findById(id).orElseThrow();

        model.addAttribute("ticketForm", mapper.toForm(ticket));
        model.addAttribute("ticket", ticket);
        model.addAttribute("categories", TicketCategory.values());

        return "tickets/edit";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("ticketForm") TicketForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {

        if (result.hasErrors()) {
            Ticket existing = ticketService.findById(id).orElseThrow();
            model.addAttribute("ticket", existing);
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
                result.rejectValue("passengerId", "invalid", msg);
            } else if (msg.contains("Flight")) {
                result.rejectValue("flightId", "invalid", msg);
            } else if (msg.contains("Seat")) {
                result.rejectValue("seatNumber", "duplicate", msg);
            } else {
                result.reject("globalError", msg);
            }

            Ticket existing = ticketService.findById(id).orElseThrow();
            model.addAttribute("ticket", existing);
            model.addAttribute("categories", TicketCategory.values());

            return "tickets/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            ticketService.delete(id);
            ra.addFlashAttribute("success", "Ticket deleted.");
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/tickets";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        Ticket ticket = ticketService.findById(id).orElseThrow();
        model.addAttribute("ticket", ticket);
        return "tickets/view";
    }
}
