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

    @GetMapping
    public String index(
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model model
    ) {
        Sort.Direction direction = Sort.Direction.fromString(dir);

        model.addAttribute("tickets",
                ticketService.findAll(Sort.by(direction, sort)));

        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("reverseDir", dir.equals("asc") ? "desc" : "asc");

        return "tickets/index";
    }

    // restul controllerului rămâne IDENTIC
}
