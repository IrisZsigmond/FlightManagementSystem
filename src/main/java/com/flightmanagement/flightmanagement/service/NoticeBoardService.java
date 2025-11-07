package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.NoticeBoard;
import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing NoticeBoard entities.
 */
public interface NoticeBoardService extends BaseService<NoticeBoard, String> {

    /**
     * Finds all notice boards that match the specified date.
     *
     * @param date the target date
     * @return list of notice boards for that date
     */
    List<NoticeBoard> findByDate(LocalDate date);

    /**
     * Returns the notice board for the current day.
     *
     * @return today's notice board
     */
    NoticeBoard getCurrentDayBoard();
}
