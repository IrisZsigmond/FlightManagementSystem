package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirplaneServiceImpl implements AirplaneService {

    private final AbstractRepository<Airplane, String> repository;

    public AirplaneServiceImpl(AbstractRepository<Airplane, String> repository) {
        this.repository = repository;
    }


    @Override
    public Airplane save(Airplane entity) {
        return null;
    }

    @Override
    public List<Airplane> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Airplane> findById(String id) {
        return repository.findById(id);
    }


    @Override
    public Airplane update(String id, Airplane updatedEntity) {
        boolean success = repository.update(id, updatedEntity);
        if (!success) {
            try {
                throw new Exception("Airplane not found: " + id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return updatedEntity;
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }

    @Override
    public boolean existsById(String s) {
        return false;
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
