package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.AirlineEmployee;
import com.flightmanagement.flightmanagement.repository.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirlineEmployeeServiceImpl implements AirlineEmployeeService {

    private final AbstractRepository<AirlineEmployee, String> repository;

    public AirlineEmployeeServiceImpl(AbstractRepository<AirlineEmployee, String> repository) {
        this.repository = repository;
    }


    @Override
    public AirlineEmployee save(AirlineEmployee entity) {
        return null;
    }

    @Override
    public List<AirlineEmployee> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<AirlineEmployee> findById(String id) {
        return repository.findById(id);
    }


    @Override
    public AirlineEmployee update(String id, AirlineEmployee updatedEntity) {
        boolean success = repository.update(id, updatedEntity);
        if (!success) {
            try {
                throw new Exception("AirlineEmployee not found: " + id);
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
