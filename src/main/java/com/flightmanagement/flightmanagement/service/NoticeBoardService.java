package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.NoticeBoard;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing NoticeBoard entities.
 */
public interface NoticeBoardService extends BaseService<NoticeBoard, String> {

    /**
     * Projection: load notice board and attach flightsOfTheDay list
     * from Flight.noticeBoardId.
     */
    Optional<NoticeBoard> findWithFlights(String id);

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
