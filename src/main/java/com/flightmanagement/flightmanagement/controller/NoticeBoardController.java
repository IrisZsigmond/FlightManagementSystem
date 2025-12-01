package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.dto.NoticeBoardForm;
import com.flightmanagement.flightmanagement.mapper.NoticeBoardMapper;
import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.service.NoticeBoardService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping
    public String index(Model model) {
        model.addAttribute("noticeboards", noticeBoardService.findAll());
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

            // curățăm câmpurile invalide
            result.getFieldErrors().forEach(error -> {
                switch (error.getField()) {
                    case "id" -> form.setId("");
                    case "date" -> form.setDate("");
                }
            });

            // resetăm BindingResult-ul
            model.asMap().remove("org.springframework.validation.BindingResult.noticeBoardForm");

            BindingResult newResult =
                    new org.springframework.validation.BeanPropertyBindingResult(form, "noticeBoardForm");

            result.getFieldErrors().forEach(error ->
                    newResult.rejectValue(error.getField(), "", error.getDefaultMessage())
            );

            model.addAttribute("org.springframework.validation.BindingResult.noticeBoardForm", newResult);
            model.addAttribute("noticeBoardForm", form);

            return "noticeboards/new";
        }

        NoticeBoard nb = mapper.toEntity(form);
        noticeBoardService.save(nb);
        ra.addFlashAttribute("success", "Notice board created.");
        return "redirect:/noticeboards";
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

            result.getFieldErrors().forEach(error -> {
                switch (error.getField()) {
                    case "date" -> form.setDate("");
                    // ID este readonly → nu îl golim
                }
            });

            model.asMap().remove("org.springframework.validation.BindingResult.noticeBoardForm");

            BindingResult newResult =
                    new org.springframework.validation.BeanPropertyBindingResult(form, "noticeBoardForm");

            result.getFieldErrors().forEach(error ->
                    newResult.rejectValue(error.getField(), "", error.getDefaultMessage())
            );

            NoticeBoard existing = noticeBoardService.findById(id).orElseThrow();
            model.addAttribute("noticeBoard", existing);
            model.addAttribute("org.springframework.validation.BindingResult.noticeBoardForm", newResult);
            model.addAttribute("noticeBoardForm", form);

            return "noticeboards/edit";
        }

        NoticeBoard existing = noticeBoardService.findById(id).orElseThrow();
        mapper.updateEntityFromForm(existing, form);
        noticeBoardService.update(id, existing);

        ra.addFlashAttribute("success", "Notice board updated.");
        return "redirect:/noticeboards";
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
