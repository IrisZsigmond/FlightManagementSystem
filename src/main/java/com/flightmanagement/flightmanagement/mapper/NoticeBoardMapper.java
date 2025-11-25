package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.NoticeBoardForm;
import com.flightmanagement.flightmanagement.model.NoticeBoard;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class NoticeBoardMapper {

    public NoticeBoard toEntity(NoticeBoardForm form) {
        NoticeBoard nb = new NoticeBoard();
        nb.setId(form.getId());

        if (form.getDate() != null && !form.getDate().isBlank()) {
            nb.setDate(LocalDate.parse(form.getDate()));
        }

        return nb;
    }

    public NoticeBoardForm toForm(NoticeBoard nb) {
        NoticeBoardForm form = new NoticeBoardForm();
        form.setId(nb.getId());
        form.setDate(nb.getDate() != null ? nb.getDate().toString() : null);
        return form;
    }

    public void updateEntityFromForm(NoticeBoard existing, NoticeBoardForm form) {
        if (form.getDate() != null && !form.getDate().isBlank()) {
            existing.setDate(LocalDate.parse(form.getDate()));
        }
    }
}
