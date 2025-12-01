package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.FlightForm;
import com.flightmanagement.flightmanagement.mapper.FlightMapper;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.service.AirplaneService;
import com.flightmanagement.flightmanagement.service.FlightService;
import com.flightmanagement.flightmanagement.service.NoticeBoardService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final FlightMapper mapper;
    private final NoticeBoardService noticeBoardService;
    private final AirplaneService airplaneService;

    public FlightController(FlightService flightService,
                            FlightMapper mapper,
                            NoticeBoardService noticeBoardService,
                            AirplaneService airplaneService) {
        this.flightService = flightService;
        this.mapper = mapper;
        this.noticeBoardService = noticeBoardService;
        this.airplaneService = airplaneService;
    }

    // INDEX
    @GetMapping
    public String index(Model model) {
        model.addAttribute("flights", flightService.findAll());
        return "flights/index";
    }

    // CREATE form
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("flightForm", new FlightForm());
        model.addAttribute("noticeBoards", noticeBoardService.findAll());
        model.addAttribute("airplanes", airplaneService.findAll());
        return "flights/new";
    }

    // CREATE submit
    @PostMapping
    public String create(
            @Valid @ModelAttribute("flightForm") FlightForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {

        if (result.hasErrors()) {
            model.addAttribute("noticeBoards", noticeBoardService.findAll());
            model.addAttribute("airplanes", airplaneService.findAll());
            return "flights/new";
        }

        try {
            Flight f = mapper.toEntity(form);
            flightService.save(f);
            ra.addFlashAttribute("success", "Flight created.");
            return "redirect:/flights";

        } catch (IllegalArgumentException | IllegalStateException ex) {

            String msg = ex.getMessage();

            if (msg.contains("ID")) {
                result.rejectValue("id", "duplicate", msg);
            } else if (msg.contains("name")) {
                result.rejectValue("name", "duplicate", msg);
            } else if (msg.contains("Airplane")) {
                result.rejectValue("airplaneId", "invalid", msg);
            } else if (msg.contains("NoticeBoard")) {
                result.rejectValue("noticeBoardId", "invalid", msg);
            } else {
                result.reject("globalError", msg);
            }

            model.addAttribute("noticeBoards", noticeBoardService.findAll());
            model.addAttribute("airplanes", airplaneService.findAll());
            return "flights/new";
        }
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            flightService.delete(id);
            ra.addFlashAttribute("success", "Flight deleted.");

        } catch (IllegalStateException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/flights";
    }

    // EDIT form
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {

        Flight flight = flightService.findById(id).orElseThrow();
        FlightForm form = mapper.toForm(flight);

        model.addAttribute("flightForm", form);
        model.addAttribute("flight", flight);

        model.addAttribute("noticeBoards", noticeBoardService.findAll());
        model.addAttribute("airplanes", airplaneService.findAll());

        return "flights/edit";
    }

    // UPDATE submit
    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("flightForm") FlightForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {

        if (result.hasErrors()) {
            model.addAttribute("flight", flightService.findById(id).orElseThrow());
            model.addAttribute("noticeBoards", noticeBoardService.findAll());
            model.addAttribute("airplanes", airplaneService.findAll());
            return "flights/edit";
        }

        try {
            Flight existing = flightService.findById(id).orElseThrow();
            mapper.updateEntityFromForm(existing, form);
            flightService.update(id, existing);

            ra.addFlashAttribute("success", "Flight updated.");
            return "redirect:/flights";

        } catch (IllegalArgumentException | IllegalStateException ex) {

            String msg = ex.getMessage();

            if (msg.contains("name")) {
                result.rejectValue("name", "duplicate", msg);
            } else if (msg.contains("Airplane")) {
                result.rejectValue("airplaneId", "invalid", msg);
            } else if (msg.contains("NoticeBoard")) {
                result.rejectValue("noticeBoardId", "invalid", msg);
            } else {
                result.reject("globalError", msg);
            }

            model.addAttribute("flight", flightService.findById(id).orElseThrow());
            model.addAttribute("noticeBoards", noticeBoardService.findAll());
            model.addAttribute("airplanes", airplaneService.findAll());

            return "flights/edit";
        }
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        model.addAttribute("flight", flightService.findById(id).orElseThrow());
        return "flights/view";
    }
}
