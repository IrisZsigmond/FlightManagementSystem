package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.NoticeBoardForm;
import com.flightmanagement.flightmanagement.model.NoticeBoard;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Mapper between NoticeBoard entity and NoticeBoardForm DTO.
 */
@Component
public class NoticeBoardMapper {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    public NoticeBoard toEntity(NoticeBoardForm form) {
        if (form == null) return null;

        NoticeBoard board = new NoticeBoard();
        board.setId(form.getId());
        board.setDate(parseDate(form.getDate()));
        return board;
    }

    public NoticeBoardForm toForm(NoticeBoard board) {
        if (board == null) return null;

        NoticeBoardForm form = new NoticeBoardForm();
        form.setId(board.getId());
        form.setDate(formatDate(board.getDate()));
        return form;
    }

    public void updateEntityFromForm(NoticeBoard existing, NoticeBoardForm form) {
        if (existing == null || form == null) return;

        existing.setDate(parseDate(form.getDate()));
        // flightsOfTheDay rămâne gestionat în service.
    }

    // ---- helpers ----

    private LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(value, DATE_FMT);
        } catch (DateTimeParseException e) {
            return null; // sau poți arunca o excepție de validare
        }
    }

    private String formatDate(LocalDate date) {
        return date == null ? "" : date.format(DATE_FMT);
    }
}
