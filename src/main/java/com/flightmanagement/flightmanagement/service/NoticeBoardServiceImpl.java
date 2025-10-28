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
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final AbstractRepository<NoticeBoard, String> repository;

    public NoticeBoardServiceImpl(AbstractRepository<NoticeBoard, String> repository) {
        this.repository = repository;
    }

    @Override
    public NoticeBoard save(NoticeBoard entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public List<NoticeBoard> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<NoticeBoard> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public NoticeBoard update(String id, NoticeBoard updatedEntity) {
        repository.update(id, updatedEntity);
        return updatedEntity;
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void clear() {
        repository.clear();
    }

    // Finds all notice boards that match the specified date
    @Override
    public List<NoticeBoard> findByDate(LocalDate date) {
        return repository.findAll().stream()
                .filter(board -> date != null && date.equals(board.getDate()))
                .collect(Collectors.toList());
    }

    // Returns the notice board for the current day
    @Override
    public NoticeBoard getCurrentDayBoard() {
        LocalDate today = LocalDate.now();
        return repository.findAll().stream()
                .filter(board -> today.equals(board.getDate()))
                .findFirst()
                .orElse(null);
    }
}
