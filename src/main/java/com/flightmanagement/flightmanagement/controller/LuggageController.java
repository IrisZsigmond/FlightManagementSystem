package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.LuggageForm;
import com.flightmanagement.flightmanagement.mapper.LuggageMapper;
import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
import com.flightmanagement.flightmanagement.service.LuggageService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Sort;


@Controller
@RequestMapping("/luggages")
public class LuggageController {

    private final LuggageService luggageService;
    private final LuggageMapper mapper;

    public LuggageController(LuggageService luggageService,
                             LuggageMapper mapper) {
        this.luggageService = luggageService;
        this.mapper = mapper;
    }

    @GetMapping
    public String index(
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model model
    ) {
        String sortProperty = switch (sort) {
            case "id" -> "id";
            case "ticket" -> "ticket.id";
            case "status" -> "status";
            case "size" -> "size";
            default -> "id";
        };

        Sort.Direction direction =
                dir.equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        Sort sortObj = Sort.by(direction, sortProperty);

        model.addAttribute("luggages", luggageService.findAll(sortObj));
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("reverseDir", dir.equals("asc") ? "desc" : "asc");

        return "luggage/index";
    }


    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("luggageForm", new LuggageForm());
        model.addAttribute("statuses", LuggageStatus.values());
        model.addAttribute("sizes", LuggageSize.values());
        return "luggage/new";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("luggageForm") LuggageForm form,
            BindingResult result,
            RedirectAttributes ra,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("statuses", LuggageStatus.values());
            model.addAttribute("sizes", LuggageSize.values());
            return "luggage/new";
        }

        try {
            Luggage luggage = mapper.toEntity(form);
            luggageService.save(luggage);

            ra.addFlashAttribute("success", "Luggage created.");
            return "redirect:/luggages";

        } catch (IllegalArgumentException | IllegalStateException ex) {

            String msg = ex.getMessage();

            if (msg.contains("ID")) {
                result.rejectValue("id", "duplicate", msg);
            }
            else if (msg.contains("Ticket")) {
                result.rejectValue("ticketId", "invalid", msg);
            }
            else {
                result.reject("globalError", msg);
            }

            model.addAttribute("statuses", LuggageStatus.values());
            model.addAttribute("sizes", LuggageSize.values());
            return "luggage/new";
        }
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Luggage luggage = luggageService.findById(id).orElseThrow();
        model.addAttribute("luggageForm", mapper.toForm(luggage));
        model.addAttribute("luggage", luggage);
        model.addAttribute("statuses", LuggageStatus.values());
        model.addAttribute("sizes", LuggageSize.values());
        return "luggage/edit";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("luggageForm") LuggageForm form,
            BindingResult result,
            RedirectAttributes ra,
            Model model
    ) {

        // --- VALIDĂRI DE FORM ---
        if (result.hasErrors()) {

            // curățăm câmpurile invalide
            result.getFieldErrors().forEach(error -> {
                switch (error.getField()) {
                    case "ticketId" -> form.setTicketId("");
                    case "status"   -> form.setStatus("");
                    case "size"     -> form.setSize("");
                }
            });

            // înlocuim BindingResult-ul
            model.asMap().remove("org.springframework.validation.BindingResult.luggageForm");

            BindingResult newResult =
                    new org.springframework.validation.BeanPropertyBindingResult(form, "luggageForm");

            result.getFieldErrors().forEach(error ->
                    newResult.rejectValue(error.getField(), "", error.getDefaultMessage())
            );

            Luggage existing = luggageService.findById(id).orElseThrow();
            model.addAttribute("luggage", existing);
            model.addAttribute("org.springframework.validation.BindingResult.luggageForm", newResult);
            model.addAttribute("luggageForm", form);
            model.addAttribute("statuses", LuggageStatus.values());
            model.addAttribute("sizes", LuggageSize.values());

            return "luggage/edit";
        }


        // --- SERVICIUL + VALIDATORUL ---
        try {

            Luggage existing = luggageService.findById(id).orElseThrow();

            mapper.updateEntityFromForm(existing, form);

            luggageService.update(id, existing);

            ra.addFlashAttribute("success", "Luggage updated.");
            return "redirect:/luggages";

        } catch (IllegalArgumentException | IllegalStateException ex) {

            String msg = ex.getMessage() != null ? ex.getMessage() : "Could not update luggage.";

            // mapăm mesajele către field-urile corecte
            if (msg.contains("Ticket")) {
                result.rejectValue("ticketId", "invalid", msg);
            } else if (msg.contains("ID")) {
                result.rejectValue("id", "duplicate", msg);  // în mod normal nu se modifică ID la update
            } else {
                result.reject("globalError", msg);
            }

            Luggage existing = luggageService.findById(id).orElseThrow();

            model.addAttribute("luggage", existing);
            model.addAttribute("luggageForm", form);
            model.addAttribute("statuses", LuggageStatus.values());
            model.addAttribute("sizes", LuggageSize.values());

            return "luggage/edit";
        }
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            luggageService.delete(id);
            ra.addFlashAttribute("success", "Luggage deleted.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/luggages";
    }


    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        Luggage luggage = luggageService.findById(id).orElseThrow();
        model.addAttribute("luggage", luggage);
        return "luggage/view";
    }
}