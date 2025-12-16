package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.repository.AirplaneRepository;
import com.flightmanagement.flightmanagement.validations.AirplaneValidator;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AirplaneServiceImpl implements AirplaneService {

    private final AirplaneRepository airplaneRepository;
    private final AirplaneValidator airplaneValidator;

    public AirplaneServiceImpl(AirplaneRepository airplaneRepository,
                               AirplaneValidator airplaneValidator) {
        this.airplaneRepository = airplaneRepository;
        this.airplaneValidator = airplaneValidator;
    }

    // ---------------- CRUD ----------------

    // CREATE
    @Override
    public Airplane save(Airplane airplane) {
        if (airplane == null) {
            throw new IllegalArgumentException("Airplane cannot be null");
        }

        // ID unic
        airplaneValidator.assertIdUnique(airplane.getId());

        // Number unic - CREATE
        airplaneValidator.assertNumberUniqueForCreate(airplane.getNumber());

        return airplaneRepository.save(airplane);
    }

    // UPDATE
    @Override
    public Airplane update(String id, Airplane updated) {
        if (updated == null) {
            throw new IllegalArgumentException("Updated airplane cannot be null");
        }

        // există avionul?
        Airplane existing = airplaneValidator.requireExisting(id);

        // verificăm number unic, excluzând avionul curent
        airplaneValidator.assertNumberUniqueForUpdate(updated.getNumber(), id);

        // actualizăm doar câmpurile editabile
        existing.setNumber(updated.getNumber());
        existing.setCapacity(updated.getCapacity());

        return airplaneRepository.save(existing);
    }



    // READ
    @Override
    @Transactional(readOnly = true)
    public List<Airplane> findAll() {
        return airplaneRepository.findAll();
    }

    // DELETE
    @Override
    public boolean delete(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }

        Optional<Airplane> optional = airplaneRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        airplaneValidator.assertCanBeDeleted(id);
        airplaneRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Airplane> findAll(Sort sort) {
        return airplaneRepository.findAll(sort);
    }

    @Override
    public List<Airplane> findByNumber(Integer number, Sort sort) {
        return airplaneRepository.findByNumber(number, sort);
    }

    @Override
    public List<Airplane> findByCapacity(Integer capacity, Sort sort) {
        return airplaneRepository.findByCapacity(capacity, sort);
    }

    @Override
    public List<Airplane> findByNumberAndCapacity(Integer number, Integer capacity, Sort sort) {
        return airplaneRepository.findByNumberAndCapacity(number, capacity, sort);
    }

    @Override
    public List<Airplane> search(Integer number, Integer capacity, Sort sort) {
        Sort safeSort = sort != null ? sort : Sort.by(Sort.Direction.ASC, "id");

        boolean hasNumber = number != null;
        boolean hasCapacity = capacity != null;

        if (hasNumber && hasCapacity) {
            return findByNumberAndCapacity(number, capacity, safeSort);
        } else if (hasNumber) {
            return findByNumber(number, safeSort);
        } else if (hasCapacity) {
            return findByCapacity(capacity, safeSort);
        } else {
            return findAll(safeSort);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Airplane getById(String id) {
        return airplaneValidator.requireExisting(id);
    }

    @Override
    public Optional<Airplane> findById(String id) {
        return airplaneRepository.findById(id);
    }
}
