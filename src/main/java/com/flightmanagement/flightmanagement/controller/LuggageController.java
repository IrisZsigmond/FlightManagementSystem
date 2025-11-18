package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.LuggageForm;
import com.flightmanagement.flightmanagement.mapper.LuggageMapper;
import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
import com.flightmanagement.flightmanagement.service.LuggageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/luggage")
public class LuggageController {

    private final LuggageService luggageService;
    private final LuggageMapper luggageMapper;

    public LuggageController(LuggageService luggageService,
                             LuggageMapper luggageMapper) {
        this.luggageService = luggageService;
        this.luggageMapper = luggageMapper;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("luggages", luggageService.findAll());
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
    public String create(@ModelAttribute("luggageForm") LuggageForm form,
                         RedirectAttributes ra) {
        Luggage luggage = luggageMapper.toEntity(form);
        luggageService.save(luggage);
        ra.addFlashAttribute("success", "Luggage created.");
        return "redirect:/luggage";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Luggage luggage = luggageService.findById(id).orElseThrow();
        LuggageForm form = luggageMapper.toForm(luggage);

        model.addAttribute("luggageForm", form);
        model.addAttribute("luggage", luggage); // dacă vrei să arăți ceva read-only
        model.addAttribute("statuses", LuggageStatus.values());
        model.addAttribute("sizes", LuggageSize.values());
        return "luggage/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("luggageForm") LuggageForm form,
                         RedirectAttributes ra) {

        Luggage existing = luggageService.findById(id).orElseThrow();
        luggageMapper.updateEntityFromForm(existing, form);
        luggageService.update(id, existing);

        ra.addFlashAttribute("success", "Luggage updated.");
        return "redirect:/luggage";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id,
                         RedirectAttributes ra) {
        luggageService.delete(id);
        ra.addFlashAttribute("success", "Luggage deleted.");
        return "redirect:/luggage";
    }
}
