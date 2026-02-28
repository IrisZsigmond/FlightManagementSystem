package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.repository.NoticeBoardRepository;
import com.flightmanagement.flightmanagement.validations.NoticeBoardValidator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final NoticeBoardValidator validator;
    private final FlightService flightService;

    public NoticeBoardServiceImpl(
            NoticeBoardRepository noticeBoardRepository,
            NoticeBoardValidator validator,
            FlightService flightService
    ) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.validator = validator;
        this.flightService = flightService;
    }

    @Override
    public NoticeBoard save(NoticeBoard noticeBoard) {
        if (noticeBoard == null) {
            throw new IllegalArgumentException("NoticeBoard cannot be null.");
        }
        validator.assertIdUnique(noticeBoard.getId());
        validator.assertDateUnique(noticeBoard.getDate());

        return noticeBoardRepository.save(noticeBoard);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticeBoard> findAll() {
        return noticeBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NoticeBoard> findById(String id) {
        return noticeBoardRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public NoticeBoard getCurrentDayBoard() {
        return noticeBoardRepository.findByDate(LocalDate.now())
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NoticeBoard> findWithFlights(String id) {
        return noticeBoardRepository.findById(id).map(board -> {
            board.setFlightsOfTheDay(flightService.findByNoticeBoardId(id));
            return board;
        });
    }

    @Override
    public NoticeBoard update(String id, NoticeBoard updated) {
        NoticeBoard existing = validator.requireExisting(id);
        validator.assertDateUniqueForUpdate(updated.getDate(), id);
        existing.setDate(updated.getDate());
        return noticeBoardRepository.save(existing);
    }

    @Override
    public boolean delete(String id) {
        validator.requireExisting(id);
        validator.assertCanBeDeleted(id);
        noticeBoardRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticeBoard> findByDate(LocalDate date) {
        if (date == null) return List.of();
        return noticeBoardRepository.findByDate(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticeBoard> findAll(Sort sort) {
        return noticeBoardRepository.findAll(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticeBoard> search(LocalDate date, Sort sort) {

        Sort safeSort = (sort != null) ? sort : Sort.by(Sort.Direction.DESC, "date");

        if (date != null) {
            return noticeBoardRepository.findByDate(date, safeSort);
        }

        return noticeBoardRepository.findAll(safeSort);
    }
}