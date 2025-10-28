package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.NoticeBoard;

/**
 * Service interface for managing NoticeBoard entities.
 * Extends the generic BaseService interface to provide CRUD operations.
 */
public interface NoticeBoardService extends BaseService<NoticeBoard, String> {
    // You can add NoticeBoard-specific methods here later, e.g.:
    // List<NoticeBoard> findByDate(LocalDate date);
}
