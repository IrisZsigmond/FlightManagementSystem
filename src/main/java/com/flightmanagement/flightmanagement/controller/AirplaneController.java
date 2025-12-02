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


    /* ===================================
                LIST
       =================================== */

    @GetMapping
    public String index(Model model) {
        model.addAttribute("airplanes", airplaneService.findAll());
        return "airplanes/index";
    }


    /* ===================================
                CREATE FORM
       =================================== */

    @GetMapping("/new")
    public String form(Model model) {
        if (!model.containsAttribute("airplaneForm")) {
            model.addAttribute("airplaneForm", new AirplaneForm());
        }
        return "airplanes/new";
    }


    /* ===================================
                CREATE SUBMIT
       =================================== */

    @PostMapping
    public String create(
            @Valid @ModelAttribute("airplaneForm") AirplaneForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        if (result.hasErrors()) {
            return "airplanes/new";
        }

        try {
            Airplane airplane = airplaneMapper.toEntity(form);
            airplaneService.save(airplane);

            ra.addFlashAttribute("success", "Airplane created successfully.");
            return "redirect:/airplanes";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage() : "Could not create airplane.";

            if (msg.contains("id")) {
                result.rejectValue("id", "duplicate", msg);
            } else if (msg.contains("number")) {
                result.rejectValue("number", "duplicate", msg);
            } else {
                result.reject("globalError", msg);
            }

            return "airplanes/new";
        }
    }


    /* ===================================
                EDIT FORM
       =================================== */

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id,
                       Model model,
                       RedirectAttributes ra) {
        try {
            Airplane airplane = airplaneService.getById(id);
            AirplaneForm form = airplaneMapper.toForm(airplane);

            model.addAttribute("airplaneForm", form);
            model.addAttribute("airplaneId", id);
            model.addAttribute("airplane", airplane);

            return "airplanes/edit";

        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/airplanes";
        }
    }


    /* ===================================
                UPDATE SUBMIT
       =================================== */

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("airplaneForm") AirplaneForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        /* --- Bean validation errors (DTO-level) --- */
        if (result.hasErrors()) {
            model.addAttribute("airplaneId", id);
            model.addAttribute("airplane", airplaneService.getById(id));
            model.addAttribute("airplaneForm", form);
            return "airplanes/edit";
        }

        try {
            Airplane existing = airplaneService.getById(id);

            // Update entity from the form
            airplaneMapper.updateEntityFromForm(existing, form);

            // Service-level business validation
            airplaneService.update(id, existing);

            ra.addFlashAttribute("success", "Airplane updated successfully.");
            return "redirect:/airplanes";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage() : "Could not update airplane.";

            if (msg.contains("number")) {
                result.rejectValue("number", "duplicate", msg);
            } else {
                result.reject("globalError", msg);
            }

            model.addAttribute("airplaneId", id);
            model.addAttribute("airplane", airplaneService.getById(id));
            model.addAttribute("airplaneForm", form);

            return "airplanes/edit";
        }
    }


    /* ===================================
                DELETE
       =================================== */

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            boolean deleted = airplaneService.delete(id);

            if (deleted) {
                ra.addFlashAttribute("success", "Airplane deleted.");
            } else {
                ra.addFlashAttribute("error", "Airplane not found or could not be deleted.");
            }

        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/airplanes";
    }


    /* ===================================
                VIEW DETAILS
       =================================== */

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model, RedirectAttributes ra) {
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
