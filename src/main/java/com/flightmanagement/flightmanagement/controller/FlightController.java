package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.FlightForm;
import com.flightmanagement.flightmanagement.mapper.FlightMapper;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.service.AirplaneService;
import com.flightmanagement.flightmanagement.service.FlightService;
import com.flightmanagement.flightmanagement.service.NoticeBoardService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalTime;

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
    public String index(Model model,
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false) String startTime,
                        @RequestParam(required = false) String endTime,
                        @RequestParam(defaultValue = "id") String sort,
                        @RequestParam(defaultValue = "asc") String dir
    ) {

        /* ================= SORT WHITELIST ================= */
        if (!sort.equals("id")
                && !sort.equals("name")
                && !sort.equals("departureTime")) {
            sort = "id";
        }

        if (!dir.equalsIgnoreCase("asc") && !dir.equalsIgnoreCase("desc")) {
            dir = "asc";
        }

        Sort.Direction direction =
                dir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort springSort = Sort.by(direction, sort);

        /* ================= TIME PARSING (SAFE) ================= */
        LocalTime start = null;
        LocalTime end = null;

        if (startTime != null && !startTime.isBlank()) {
            try {
                start = LocalTime.parse(startTime); // expects HH:mm
            } catch (Exception ex) {
                model.addAttribute("error", "Start time must be in format HH:mm.");
            }
        }

        if (endTime != null && !endTime.isBlank()) {
            try {
                end = LocalTime.parse(endTime);
            } catch (Exception ex) {
                model.addAttribute("error", "End time must be in format HH:mm.");
            }
        }

        /* ================= FILTER + SORT ================= */
        model.addAttribute(
                "flights",
                flightService.search(name, start, end, springSort)
        );

        /* ================= UI STATE ================= */
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("reverseDir",
                dir.equalsIgnoreCase("asc") ? "desc" : "asc");

        model.addAttribute("filterName", name);
        model.addAttribute("filterStartTime", startTime);
        model.addAttribute("filterEndTime", endTime);

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
