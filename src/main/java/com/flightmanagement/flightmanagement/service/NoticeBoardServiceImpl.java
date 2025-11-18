package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of NoticeBoardService that provides business logic
 * and interacts with the NoticeBoard repository to perform CRUD operations.
 */
@Service
public class NoticeBoardServiceImpl extends BaseServiceImpl<NoticeBoard, String> implements NoticeBoardService {

    private final FlightService flightService;

    public NoticeBoardServiceImpl(AbstractRepository<NoticeBoard, String> repository,
                                  FlightService flightService) {
        super(repository);
        this.flightService = flightService;
    }

    /// -------- NoticeBoard-specific methods --------

    @Override
    public List<NoticeBoard> findByDate(LocalDate date) {
        if (date == null) return List.of();
        return repo().findAll().stream()
                .filter(board -> date.equals(board.getDate()))
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

    @Override
    public Optional<NoticeBoard> findWithFlights(String id) {
        return repo().findById(id).map(board -> {
            board.setFlightsOfTheDay(flightService.findByNoticeBoardId(id));
            return board;
        });
    }

    @Override
    public boolean delete(String id) {
        var attachedFlights = flightService.findByNoticeBoardId(id);
        if (!attachedFlights.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete notice board '" + id + "' because flights are still attached (" +
                            attachedFlights.size() + ").");
        }
        return super.delete(id);
    }
}
