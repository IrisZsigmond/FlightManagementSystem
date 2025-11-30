package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.AirlineEmployeeForm;
import com.flightmanagement.flightmanagement.mapper.AirlineEmployeeMapper;
import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.model.enums.AirlineRole;
import com.flightmanagement.flightmanagement.service.AirlineEmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/airlineemployees")
public class AirlineEmployeeController {

    private final AirlineEmployeeService service;
    private final AirlineEmployeeMapper mapper;

    public AirlineEmployeeController(AirlineEmployeeService service,
                                     AirlineEmployeeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("employees", service.findAll());
        return "airlineemployees/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("airlineEmployeeForm", new AirlineEmployeeForm());
        model.addAttribute("roles", AirlineRole.values());
        return "airlineemployees/new";
    }


    @PostMapping
    public String create(
            @Valid @ModelAttribute("airlineEmployeeForm") AirlineEmployeeForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        if (result.hasErrors()) {
            model.addAttribute("roles", AirlineRole.values());
            return "airlineemployees/new";
        }

        AirlineEmployee e = mapper.toEntity(form);
        service.save(e);

        ra.addFlashAttribute("success", "Airline employee created.");
        return "redirect:/airlineemployees";
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        AirlineEmployee e = service.findById(id).orElseThrow();
        model.addAttribute("airlineEmployeeForm", mapper.toForm(e));
        model.addAttribute("employee", e);
        model.addAttribute("roles", AirlineRole.values());
        return "airlineemployees/edit";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("airlineEmployeeForm") AirlineEmployeeForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        if (result.hasErrors()) {
            AirlineEmployee existing = service.findById(id).orElseThrow();
            model.addAttribute("employee", existing);
            model.addAttribute("roles", AirlineRole.values());
            return "airlineemployees/edit";
        }

        AirlineEmployee existing = service.findById(id).orElseThrow();
        mapper.updateEntityFromForm(existing, form);
        service.update(id, existing);

        ra.addFlashAttribute("success", "Airline employee updated.");
        return "redirect:/airlineemployees";
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id,
                         RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("success", "Airline employee deleted.");
        } catch (Exception ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/airlineemployees";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        AirlineEmployee employee = service.findById(id).orElseThrow();
        model.addAttribute("employee", employee);
        return "airlineemployees/view";
    }

}
