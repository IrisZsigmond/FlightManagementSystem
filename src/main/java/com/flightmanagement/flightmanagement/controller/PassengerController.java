package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.PassengerForm;
import com.flightmanagement.flightmanagement.mapper.PassengerMapper;
import com.flightmanagement.flightmanagement.model.Passenger;
import com.flightmanagement.flightmanagement.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Sort;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;

    public PassengerController(PassengerService passengerService,
                               PassengerMapper passengerMapper) {
        this.passengerService = passengerService;
        this.passengerMapper = passengerMapper;
    }

    // LIST
    @GetMapping
    public String index(
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model model
    ) {
        Sort.Direction direction = Sort.Direction.fromString(dir);

        model.addAttribute("passengers",
                passengerService.findAll(Sort.by(direction, sort)));

        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("reverseDir", dir.equals("asc") ? "desc" : "asc");

        return "passengers/index";
    }


    // CREATE FORM
    @GetMapping("/new")
    public String form(Model model) {
        if (!model.containsAttribute("passengerForm")) {
            model.addAttribute("passengerForm", new PassengerForm());
        }
        return "passengers/new";
    }

    // CREATE SUBMIT
    @PostMapping
    public String create(
            @Valid @ModelAttribute("passengerForm") PassengerForm form,
            BindingResult result,           // BindingResult captures validation errors triggered by @Valid
            Model model,
            RedirectAttributes ra
    ) {
        if (result.hasErrors()) {
            return "passengers/new";
        }

        try {
            Passenger passenger = passengerMapper.toEntity(form);
            passengerService.save(passenger);

            ra.addFlashAttribute("success", "Passenger created.");
            return "redirect:/passengers";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            result.rejectValue("id", "duplicate", ex.getMessage());
            return "passengers/new";
        }
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id,
                       Model model,
                       RedirectAttributes ra) {

        try {
            Passenger passenger = passengerService.getById(id);

            model.addAttribute("passengerForm", passengerMapper.toForm(passenger));
            model.addAttribute("passenger", passenger);

            return "passengers/edit";

        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/passengers";
        }
    }

    // EDIT SUBMIT
    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("passengerForm") PassengerForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        if (result.hasErrors()) {
            Passenger passenger = passengerService.getById(id);
            model.addAttribute("passenger", passenger);
            return "passengers/edit";
        }

        try {
            Passenger existing = passengerService.getById(id);
            passengerMapper.updateEntityFromForm(existing, form);

            passengerService.update(id, existing);

            ra.addFlashAttribute("success", "Passenger updated.");
            return "redirect:/passengers";

        } catch (RuntimeException ex) {
            result.reject("globalError", ex.getMessage());
            Passenger passenger = passengerService.getById(id);
            model.addAttribute("passenger", passenger);
            return "passengers/edit";
        }
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id,
                         RedirectAttributes ra) {

        try {
            boolean deleted = passengerService.delete(id);
            if (deleted) {
                ra.addFlashAttribute("success", "Passenger deleted.");
            } else {
                ra.addFlashAttribute("error", "Passenger not found.");
            }

        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/passengers";
    }

    // VIEW DETAILS
    @GetMapping("/{id}")
    public String view(@PathVariable String id,
                       Model model,
                       RedirectAttributes ra) {

        try {
            Passenger p = passengerService.getById(id);
            model.addAttribute("passenger", p);
            return "passengers/view";
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/passengers";
        }
    }
}
