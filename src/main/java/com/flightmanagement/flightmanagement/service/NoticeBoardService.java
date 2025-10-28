package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.NoticeBoard;
import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing NoticeBoard entities.
 */
public interface NoticeBoardService extends BaseService<NoticeBoard, String> {

    // Finds all notice boards that match the specified date.
    List<NoticeBoard> findByDate(LocalDate date);

    // Returns the notice board for the current day.
    NoticeBoard getCurrentDayBoard();
}
