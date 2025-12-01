package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.AirplaneForm;
import com.flightmanagement.flightmanagement.mapper.AirplaneMapper;
import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.service.AirplaneService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/airplanes")
public class AirplaneController {

    private final AirplaneService airplaneService;
    private final AirplaneMapper airplaneMapper;

    public AirplaneController(AirplaneService airplaneService,
                              AirplaneMapper airplaneMapper) {
        this.airplaneService = airplaneService;
        this.airplaneMapper = airplaneMapper;
    }

    // LIST
    @GetMapping
    public String index(Model model) {
        model.addAttribute("airplanes", airplaneService.findAll());
        return "airplanes/index";
    }

    // CREATE - form
    @GetMapping("/new")
    public String form(Model model) {
        if (!model.containsAttribute("airplaneForm")) {
            model.addAttribute("airplaneForm", new AirplaneForm());
        }
        return "airplanes/new";
    }

    // CREATE - submit
    @PostMapping
    public String create(
            @Valid @ModelAttribute("airplaneForm") AirplaneForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        // 1) Bean Validation pe DTO
        if (result.hasErrors()) {
            return "airplanes/new";
        }

        try {
            // 2) Form -> Entity
            Airplane airplane = airplaneMapper.toEntity(form);

            // 3) Service (include validator)
            airplaneService.save(airplane);

            // 4) Succes
            ra.addFlashAttribute("success", "Airplane created.");
            return "redirect:/airplanes";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage() : "Could not create airplane.";

            // Mapăm mesajul pe câmpul corect, dacă putem
            if (msg.contains("id already exists") || msg.contains("Airplane id")) {
                result.rejectValue("id", "duplicate", msg);
            } else if (msg.contains("number")) {
                result.rejectValue("number", "duplicate", msg);
            } else {
                // alt tip de eroare -> global
                result.reject("globalError", msg);
            }

            return "airplanes/new";
        }
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id,
                       Model model,
                       RedirectAttributes ra) {
        try {
            Airplane airplane = airplaneService.getById(id);
            AirplaneForm form = airplaneMapper.toForm(airplane);

            model.addAttribute("airplaneForm", form);
            model.addAttribute("airplaneId", id);
            model.addAttribute("airplane", airplane); // REQUIRED

            return "airplanes/edit";

        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/airplanes";
        }
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("airplaneForm") AirplaneForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        // 1) Bean Validation pe DTO
        if (result.hasErrors()) {
            Airplane airplane = airplaneService.getById(id);
            model.addAttribute("airplaneId", id);
            model.addAttribute("airplane", airplane);
            return "airplanes/edit";
        }

        try {
            // 2) luăm entitatea existentă
            Airplane existing = airplaneService.getById(id);

            // 3) actualizăm din form (fără să schimbăm ID-ul)
            airplaneMapper.updateEntityFromForm(existing, form);

            // 4) apelăm service-ul (care verifică unicitatea number-ului)
            airplaneService.update(id, existing);

            ra.addFlashAttribute("success", "Airplane updated.");
            return "redirect:/airplanes";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage() : "Could not update airplane.";

            // dacă e conflict pe number -> legăm eroarea de câmpul number
            if (msg.contains("number")) {
                result.rejectValue("number", "duplicate", msg);
            } else {
                result.reject("globalError", msg);
            }

            Airplane airplane = airplaneService.getById(id);
            model.addAttribute("airplaneId", id);
            model.addAttribute("airplane", airplane);

            return "airplanes/edit";
        }
    }



    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id,
                         RedirectAttributes ra) {
        try {
            boolean deleted = airplaneService.delete(id);
            if (deleted) {
                ra.addFlashAttribute("success", "Airplane deleted.");
            } else {
                ra.addFlashAttribute("error", "Airplane not found or already deleted.");
            }
        } catch (RuntimeException ex) {
            // ex: assertCanBeDeleted -> "Cannot delete airplane ... because it has flights"
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/airplanes";
    }

    // VIEW - Details
    @GetMapping("/{id}")
    public String view(@PathVariable String id,
                       Model model,
                       RedirectAttributes ra) {
        try {
            Airplane airplane = airplaneService.getById(id);
            model.addAttribute("airplane", airplane);
            return "airplanes/view";
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/airplanes";
        }
    }
}
