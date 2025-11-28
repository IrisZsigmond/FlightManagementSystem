package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.AirplaneForm;
import com.flightmanagement.flightmanagement.mapper.AirplaneMapper;
import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.service.AirplaneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/airplanes")
public class AirplaneController {

    private final AirplaneService airplaneService;
    private final AirplaneMapper airplaneMapper;

    public AirplaneController(AirplaneService airplaneService, AirplaneMapper airplaneMapper) {
        this.airplaneService = airplaneService;
        this.airplaneMapper = airplaneMapper;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("airplanes", airplaneService.findAll());
        return "airplanes/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("airplaneForm", new AirplaneForm());
        return "airplanes/new";
    }

    @PostMapping
    public String create(@ModelAttribute("airplaneForm") AirplaneForm form,
                         RedirectAttributes ra) {
        Airplane a = airplaneMapper.toEntity(form);
        airplaneService.save(a);
        ra.addFlashAttribute("success", "Airplane created.");
        return "redirect:/airplanes";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Airplane a = airplaneService.findById(id).orElseThrow();
        model.addAttribute("airplaneForm", airplaneMapper.toForm(a));
        model.addAttribute("airplane", a);
        return "airplanes/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("airplaneForm") AirplaneForm form,
                         RedirectAttributes ra) {

        Airplane existing = airplaneService.findById(id).orElseThrow();
        airplaneMapper.updateEntityFromForm(existing, form);
        airplaneService.update(id, existing);

        ra.addFlashAttribute("success", "Airplane updated.");
        return "redirect:/airplanes";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            airplaneService.delete(id);
            ra.addFlashAttribute("success", "Airplane deleted.");
        } catch (Exception ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/airplanes";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        Airplane airplane = airplaneService.findById(id).orElseThrow();
        model.addAttribute("airplane", airplane);
        return "airplanes/view";
    }
}
