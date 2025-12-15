package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.NoticeBoard;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;

public interface NoticeBoardService {

    NoticeBoard save(NoticeBoard noticeBoard);

    List<NoticeBoard> findAll();
    List<NoticeBoard> findAll(Sort sort);

    Optional<NoticeBoard> findById(String id);

    NoticeBoard update(String id, NoticeBoard updated);

    boolean delete(String id);

    List<NoticeBoard> findByDate(LocalDate date);

    NoticeBoard getCurrentDayBoard();

    Optional<NoticeBoard> findWithFlights(String id);

    // NOU: Metoda de căutare (o singură dată)
    List<NoticeBoard> search(LocalDate date, Sort sort);
}