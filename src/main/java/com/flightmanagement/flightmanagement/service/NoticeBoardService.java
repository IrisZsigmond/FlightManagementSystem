package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.NoticeBoard;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NoticeBoardService {

    NoticeBoard save(NoticeBoard noticeBoard);

    List<NoticeBoard> findAll();

    Optional<NoticeBoard> findById(String id);

    NoticeBoard update(String id, NoticeBoard updated);

    boolean delete(String id);

    // custom:
    List<NoticeBoard> findByDate(LocalDate date);

    NoticeBoard getCurrentDayBoard();

    Optional<NoticeBoard> findWithFlights(String id);
}
