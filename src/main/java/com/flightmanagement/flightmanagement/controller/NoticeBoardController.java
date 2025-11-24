package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.NoticeBoardForm;
import com.flightmanagement.flightmanagement.mapper.NoticeBoardMapper;
import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.service.NoticeBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/noticeboards")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;
    private final NoticeBoardMapper noticeBoardMapper;

    public NoticeBoardController(NoticeBoardService noticeBoardService,
                                 NoticeBoardMapper noticeBoardMapper) {
        this.noticeBoardService = noticeBoardService;
        this.noticeBoardMapper = noticeBoardMapper;
    }

    @GetMapping
    public String index(Model model) {
        var boards = noticeBoardService.findAll().stream()
                .map(b -> noticeBoardService.findWithFlights(b.getId()).orElse(b))
                .toList();

        model.addAttribute("noticeboards", boards);
        return "noticeboards/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        NoticeBoardForm form = new NoticeBoardForm();
        form.setDate(LocalDate.now().toString()); // default: azi
        model.addAttribute("noticeBoardForm", form);
        return "noticeboards/new";
    }

    @PostMapping
    public String create(@ModelAttribute("noticeBoardForm") NoticeBoardForm form,
                         RedirectAttributes ra) {
        NoticeBoard board = noticeBoardMapper.toEntity(form);
        noticeBoardService.save(board);
        ra.addFlashAttribute("success", "Notice board created.");
        return "redirect:/noticeboards";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        NoticeBoard board = noticeBoardService.findWithFlights(id).orElseThrow();
        NoticeBoardForm form = noticeBoardMapper.toForm(board);

        model.addAttribute("noticeBoardForm", form); // editable fields
        model.addAttribute("noticeBoard", board);    // conține flightsOfTheDay pentru afișare read-only
        return "noticeboards/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("noticeBoardForm") NoticeBoardForm form,
                         RedirectAttributes ra) {

        NoticeBoard existing = noticeBoardService.findById(id).orElseThrow();
        noticeBoardMapper.updateEntityFromForm(existing, form);
        noticeBoardService.update(id, existing);

        ra.addFlashAttribute("success", "Notice board updated.");
        return "redirect:/noticeboards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            noticeBoardService.delete(id);
            ra.addFlashAttribute("success", "Notice board deleted.");
        } catch (IllegalStateException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/noticeboards";
    }
}
