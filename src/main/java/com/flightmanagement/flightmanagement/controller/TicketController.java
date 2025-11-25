package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.TicketForm;
import com.flightmanagement.flightmanagement.mapper.TicketMapper;
import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "tickets/new";
    }

    @PostMapping
    public String create(@ModelAttribute("ticketForm") TicketForm form,
                         RedirectAttributes ra) {

        Ticket t = mapper.toEntity(form);
        ticketService.save(t);

        ra.addFlashAttribute("success", "Ticket created.");
        return "redirect:/tickets";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Ticket t = ticketService.findById(id).orElseThrow();
        model.addAttribute("ticketForm", mapper.toForm(t));
        model.addAttribute("ticket", t);
        return "tickets/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("ticketForm") TicketForm form,
                         RedirectAttributes ra) {

        Ticket existing = ticketService.findById(id).orElseThrow();
        mapper.updateEntityFromForm(existing, form);
        ticketService.update(id, existing);

        ra.addFlashAttribute("success", "Ticket updated.");
        return "redirect:/tickets";
    }

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
}
