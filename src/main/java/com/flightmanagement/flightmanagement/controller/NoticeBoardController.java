package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.NoticeBoardForm;
import com.flightmanagement.flightmanagement.mapper.NoticeBoardMapper;
import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.service.NoticeBoardService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@Controller
@RequestMapping("/noticeboards")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;
    private final NoticeBoardMapper mapper;

    public NoticeBoardController(NoticeBoardService noticeBoardService,
                                 NoticeBoardMapper mapper) {
        this.noticeBoardService = noticeBoardService;
        this.mapper = mapper;
    }

    // LIST + SORT + FILTRE (INDEX)
    @GetMapping
    public String index(
            Model model,
            // MODIFICAT: O singură dată pentru filtrare
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate filterDate,

            @RequestParam(defaultValue = "date") String sort,
            @RequestParam(defaultValue = "asc") String dir
    ) {

        String sortProperty = (sort.equals("id") || sort.equals("date")) ? sort : "date";

        Sort.Direction direction =
                dir.equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        Sort sortObj = Sort.by(direction, sortProperty);

        // Apel search cu o singură dată
        model.addAttribute("noticeboards", noticeBoardService.search(filterDate, sortObj));

        // Variabile UI
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("reverseDir", dir.equals("asc") ? "desc" : "asc");

        // Păstrăm filtrul
        model.addAttribute("filterDate", filterDate);

        return "noticeboards/index";
    }


    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("noticeBoardForm", new NoticeBoardForm());
        return "noticeboards/new";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("noticeBoardForm") NoticeBoardForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        if (result.hasErrors()) {
            return "noticeboards/new";
        }

        try {
            NoticeBoard nb = mapper.toEntity(form);
            noticeBoardService.save(nb);

            ra.addFlashAttribute("success", "Notice board created.");
            return "redirect:/noticeboards";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage() : "Cannot create NoticeBoard.";
            if (msg.contains("ID")) {
                result.rejectValue("id", "duplicate", msg);
            } else if (msg.toLowerCase().contains("date")) {
                result.rejectValue("date", "duplicate", msg);
            } else {
                result.reject("globalError", msg);
            }
            return "noticeboards/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        NoticeBoard nb = noticeBoardService.findById(id).orElseThrow();
        model.addAttribute("noticeBoardForm", mapper.toForm(nb));
        model.addAttribute("noticeBoard", nb);
        return "noticeboards/edit";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("noticeBoardForm") NoticeBoardForm form,
            BindingResult result,
            Model model,
            RedirectAttributes ra
    ) {
        if (result.hasErrors()) {
            NoticeBoard existing = noticeBoardService.findById(id).orElseThrow();
            model.addAttribute("noticeBoard", existing);
            return "noticeboards/edit";
        }

        try {
            NoticeBoard existing = noticeBoardService.findById(id).orElseThrow();
            mapper.updateEntityFromForm(existing, form);
            noticeBoardService.update(id, existing);

            ra.addFlashAttribute("success", "Notice board updated.");
            return "redirect:/noticeboards";

        } catch (IllegalArgumentException | IllegalStateException ex) {
            String msg = ex.getMessage();
            if (msg.contains("ID")) {
                result.rejectValue("id", "duplicate", msg);
            } else if (msg.toLowerCase().contains("date")) {
                result.rejectValue("date", "duplicate", msg);
            } else {
                result.reject("globalError", msg);
            }
            NoticeBoard existing = noticeBoardService.findById(id).orElseThrow();
            model.addAttribute("noticeBoard", existing);
            return "noticeboards/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            noticeBoardService.delete(id);
            ra.addFlashAttribute("success", "Notice board deleted.");
        } catch (Exception ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/noticeboards";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        NoticeBoard board = noticeBoardService.findById(id).orElseThrow();
        model.addAttribute("board", board);
        return "noticeboards/view";
    }
}