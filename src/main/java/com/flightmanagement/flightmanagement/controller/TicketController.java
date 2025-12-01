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

            // 1. Curățăm câmpurile invalide
            result.getFieldErrors().forEach(error -> {
                switch (error.getField()) {
                    case "id" -> form.setId("");
                    case "passengerId" -> form.setPassengerId("");
                    case "flightId" -> form.setFlightId("");
                    case "category" -> form.setCategory("");
                    case "price" -> form.setPrice("");
                    case "seatNumber" -> form.setSeatNumber("");
                }
            });

            // 2. Eliminăm BindingResult-ul vechi
            model.asMap().remove("org.springframework.validation.BindingResult.ticketForm");

            // 3. Creăm unul nou
            BindingResult newResult =
                    new org.springframework.validation.BeanPropertyBindingResult(form, "ticketForm");

            result.getFieldErrors().forEach(error ->
                    newResult.rejectValue(error.getField(), "", error.getDefaultMessage())
            );

            // 4. Punem noul BindingResult și formularul
            model.addAttribute("org.springframework.validation.BindingResult.ticketForm", newResult);
            model.addAttribute("ticketForm", form);

            // 5. Dropdown categories
            model.addAttribute("categories", TicketCategory.values());

            return "tickets/new";
        }

        Ticket t = mapper.toEntity(form);
        ticketService.save(t);
        ra.addFlashAttribute("success", "Ticket created.");
        return "redirect:/tickets";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Ticket t = ticketService.findById(id).orElseThrow();
        model.addAttribute("ticketForm", mapper.toForm(t));
        model.addAttribute("ticket", t); // pentru panel read-only
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

            // 1. Curățăm doar câmpurile editabile invalide (ID este readonly)
            result.getFieldErrors().forEach(error -> {
                switch (error.getField()) {
                    case "passengerId" -> form.setPassengerId("");
                    case "flightId" -> form.setFlightId("");
                    case "category" -> form.setCategory("");
                    case "price" -> form.setPrice("");
                    case "seatNumber" -> form.setSeatNumber("");
                }
            });

            // 2. Resetăm BindingResult
            model.asMap().remove("org.springframework.validation.BindingResult.ticketForm");

            BindingResult newResult =
                    new org.springframework.validation.BeanPropertyBindingResult(form, "ticketForm");

            result.getFieldErrors().forEach(error ->
                    newResult.rejectValue(error.getField(), "", error.getDefaultMessage())
            );

            // 3. Reafișăm datele read-only + dropdown
            Ticket existing = ticketService.findById(id).orElseThrow();
            model.addAttribute("ticket", existing);
            model.addAttribute("categories", TicketCategory.values());

            model.addAttribute("org.springframework.validation.BindingResult.ticketForm", newResult);
            model.addAttribute("ticketForm", form);

            return "tickets/edit";
        }

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

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        Ticket ticket = ticketService.findById(id).orElseThrow();
        model.addAttribute("ticket", ticket);
        return "tickets/view";
    }
}
