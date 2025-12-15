package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.model.NoticeBoard;
import com.flightmanagement.flightmanagement.repository.FlightRepository;
import com.flightmanagement.flightmanagement.validations.FlightValidator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightValidator validator;

    public FlightServiceImpl(
            FlightRepository flightRepository,
            FlightValidator validator
    ) {
        this.flightRepository = flightRepository;
        this.validator = validator;
    }

    // CREATE
    @Override
    public Flight save(Flight flight) {

        if (flight == null) {
            throw new IllegalArgumentException("Flight cannot be null");
        }

        validator.assertIdUnique(flight.getId());
        validator.assertNameUnique(flight.getName());

        // airplane must exist
        String airplaneId = flight.getAirplane() != null ? flight.getAirplane().getId() : null;
        Airplane airplane = validator.requireExistingAirplane(airplaneId);

        // noticeBoard must exist
        String boardId = flight.getNoticeBoard() != null ? flight.getNoticeBoard().getId() : null;
        NoticeBoard board = validator.requireExistingNoticeBoard(boardId);

        flight.setAirplane(airplane);
        flight.setNoticeBoard(board);

        return flightRepository.save(flight);
    }

    // READ
    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    // UPDATE
    @Override
    public Flight update(String id, Flight updated) {
        Flight existing = validator.requireExisting(id);

        updated.setId(id);

        // name unique?
        if (updated.getName() != null) {
            validator.assertNameUniqueForUpdate(updated.getName(), id);
        }

        // airplane
        String newAirplaneId = updated.getAirplane().getId();
        Airplane airplane = validator.requireExistingAirplane(newAirplaneId);

        // noticeBoard
        String newBoardId = updated.getNoticeBoard().getId();
        NoticeBoard board = validator.requireExistingNoticeBoard(newBoardId);

        existing.setName(updated.getName());
        existing.setDepartureTime(updated.getDepartureTime());
        existing.setAirplane(airplane);
        existing.setNoticeBoard(board);

        return flightRepository.save(existing);
    }

    // DELETE
    @Override
    public boolean delete(String id) {

        validator.requireExisting(id);
        validator.assertCanBeDeleted(id);

        flightRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Flight> findByNameContainingIgnoreCase(String name, Sort sort) {
        return flightRepository.findByNameContainingIgnoreCase(name, sort);
    }

    @Override
    public List<Flight> findByDepartureTimeBetween(LocalTime startTime, LocalTime endTime, Sort sort) {
        return flightRepository.findByDepartureTimeBetween(startTime, endTime, sort);
    }

    @Override
    public List<Flight> findByNameContainingIgnoreCaseAndDepartureTimeBetween(String name, LocalTime startTime, LocalTime endTime, Sort sort) {
        return flightRepository.findByNameContainingIgnoreCaseAndDepartureTimeBetween(name, startTime, endTime, sort);
    }

    @Override
    public List<Flight> search(String name, LocalTime startTime, LocalTime endTime, Sort sort) {
        Sort safeSort = (sort != null) ? sort : Sort.by(Sort.Direction.ASC, "id");

        boolean hasName = name != null && !name.trim().isBlank();
        boolean hasstartTime = startTime != null;
        boolean hasendTime = endTime != null;

        if (hasName && hasstartTime && hasendTime) {
            return flightRepository.findByNameContainingIgnoreCaseAndDepartureTimeBetween(name, startTime, endTime, safeSort);
        } else if (hasName) {
            return flightRepository.findByNameContainingIgnoreCase(name, safeSort);
        } else if (hasstartTime && hasendTime) {
            return flightRepository.findByDepartureTimeBetween(startTime, endTime, safeSort);
        } else {
            return flightRepository.findAll(safeSort);
        }
    }

    @Override
    public Optional<Flight> findById(String id) {
        return flightRepository.findById(id);
    }

    @Override
    public List<Flight> findByNoticeBoardId(String noticeBoardId) {
        return flightRepository.findByAirplane_Id(noticeBoardId);
    }

    @Override
    public Flight getById(String id) {
        return validator.requireExisting(id);
    }

    @Override
    public List<Flight> findAll(Sort sort) {
        return flightRepository.findAll(sort);
    }
}
