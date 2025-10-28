package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if (entity == null || entity.getId() == null) {
            throw new IllegalArgumentException("NoticeBoard and its ID must not be null");
        }
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
        boolean success = repository.update(id, updatedEntity);
        if (!success) {
            throw new IllegalArgumentException("NoticeBoard not found: " + id);
        }
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
}