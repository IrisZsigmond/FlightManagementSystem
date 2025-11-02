package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of NoticeBoardService that provides business logic
 * and interacts with the NoticeBoard repository to perform CRUD operations.
 */
@Service
public class NoticeBoardServiceImplImpl extends BaseServiceImpl<NoticeBoard, String> implements NoticeBoardService {

    public NoticeBoardServiceImplImpl(AbstractRepository<NoticeBoard, String> repository) {
        super(repository);
    }

    /// -------- NoticeBoard-specific methods --------

    @Override
    public List<NoticeBoard> findByDate(LocalDate date) {
        return repo().findAll().stream()
                .filter(board -> date != null && date.equals(board.getDate()))
                .collect(Collectors.toList());
    }

    @Override
    public NoticeBoard getCurrentDayBoard() {
        LocalDate today = LocalDate.now();
        return repo().findAll().stream()
                .filter(board -> today.equals(board.getDate()))
                .findFirst()
                .orElse(null);
    }
}
