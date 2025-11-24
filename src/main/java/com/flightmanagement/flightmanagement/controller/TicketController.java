package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.TicketForm;
import com.flightmanagement.flightmanagement.mapper.TicketMapper;
import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import com.flightmanagement.flightmanagement.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    public TicketController(TicketService ticketService,
                            TicketMapper ticketMapper) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
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
    public String create(@ModelAttribute("ticketForm") TicketForm form) {
        Ticket ticket = ticketMapper.toEntity(form);
        ticketService.save(ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Ticket ticket = ticketService.findById(id).orElseThrow();
        TicketForm form = ticketMapper.toForm(ticket);

        model.addAttribute("ticketForm", form);   // editable fields (IDs + scalars)
        model.addAttribute("ticket", ticket);     // for read-only luggage display
        model.addAttribute("categories", TicketCategory.values());

        return "tickets/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("ticketForm") TicketForm form) {

        Ticket existing = ticketService.findById(id).orElseThrow();
        ticketMapper.updateEntityFromForm(existing, form);
        ticketService.update(id, existing);

        return "redirect:/tickets";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        ticketService.delete(id);
        return "redirect:/tickets";
    }
}
