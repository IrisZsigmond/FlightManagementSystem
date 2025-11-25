package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.repository.NoticeBoardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final FlightService flightService;

    public NoticeBoardServiceImpl(NoticeBoardRepository noticeBoardRepository,
                                  FlightService flightService) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.flightService = flightService;
    }

    // ------------------- CRUD -------------------

    @Override
    public NoticeBoard save(NoticeBoard noticeBoard) {
        return noticeBoardRepository.save(noticeBoard);
    }

    @Override
    public List<NoticeBoard> findAll() {
        return noticeBoardRepository.findAll();
    }

    @Override
    public Optional<NoticeBoard> findById(String id) {
        return noticeBoardRepository.findById(id);
    }

    @Override
    public NoticeBoard update(String id, NoticeBoard updated) {
        if (!noticeBoardRepository.existsById(id))
            throw new IllegalArgumentException("NoticeBoard not found: " + id);

        updated.setId(id);
        return noticeBoardRepository.save(updated);
    }

    @Override
    public boolean delete(String id) {
        var attachedFlights = flightService.findByNoticeBoardId(id);
        if (!attachedFlights.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete notice board '" + id +
                            "' because flights are still attached (" +
                            attachedFlights.size() + ")."
            );
        }
        noticeBoardRepository.deleteById(id);
        return true;
    }

    // ------------------- CUSTOM -------------------

    @Override
    public List<NoticeBoard> findByDate(LocalDate date) {
        if (date == null) return List.of();
        return noticeBoardRepository.findByDate(date);
    }

    @Override
    public NoticeBoard getCurrentDayBoard() {
        return noticeBoardRepository.findByDate(LocalDate.now())
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<NoticeBoard> findWithFlights(String id) {
        return noticeBoardRepository.findById(id).map(board -> {
            board.setFlightsOfTheDay(flightService.findByNoticeBoardId(id));
            return board;
        });
    }
}
