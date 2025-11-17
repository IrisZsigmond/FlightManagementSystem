package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.service.NoticeBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@RequestMapping("/noticeboards")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("noticeboards", noticeBoardService.findAll());
        return "noticeboards/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("noticeboard",
                new NoticeBoard(null, LocalDate.now(), new ArrayList<>()));
        return "noticeboards/new";
    }

    @PostMapping
    public String create(@ModelAttribute NoticeBoard board) {
        noticeBoardService.save(board);
        return "redirect:/noticeboards";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        NoticeBoard board = noticeBoardService.findById(id).orElseThrow();
        model.addAttribute("noticeboard", board);
        return "noticeboards/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute NoticeBoard board) {
        noticeBoardService.update(id, board);
        return "redirect:/noticeboards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        noticeBoardService.delete(id);
        return "redirect:/noticeboards";
    }
}
