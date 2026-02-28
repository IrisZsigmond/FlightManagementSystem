package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.*;
import com.flightmanagement.flightmanagement.repository.*;
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
    private final FlightAssignmentRepository flightAssignmentRepository;
    private final TicketRepository ticketRepository;
    private final LuggageRepository luggageRepository;
    private final FlightValidator validator;

    public FlightServiceImpl(
            FlightRepository flightRepository,
            FlightAssignmentRepository flightAssignmentRepository,
            TicketRepository ticketRepository,
            LuggageRepository luggageRepository,
            FlightValidator validator
    ) {
        this.flightRepository = flightRepository;
        this.flightAssignmentRepository = flightAssignmentRepository;
        this.ticketRepository = ticketRepository;
        this.luggageRepository = luggageRepository;
        this.validator = validator;
    }

    @Override
    public Flight save(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Flight cannot be null");
        }
        validator.assertIdUnique(flight.getId());
        validator.assertNameUnique(flight.getName());

        String airplaneId = flight.getAirplane() != null ? flight.getAirplane().getId() : null;
        validator.requireExistingAirplane(airplaneId);

        String boardId = flight.getNoticeBoard() != null ? flight.getNoticeBoard().getId() : null;
        validator.requireExistingNoticeBoard(boardId);

        return flightRepository.save(flight);
    }

    @Override
    public Flight update(String id, Flight updated) {
        Flight existing = validator.requireExisting(id);
        updated.setId(id);

        if (updated.getName() != null) {
            validator.assertNameUniqueForUpdate(updated.getName(), id);
        }

        validator.requireExistingAirplane(updated.getAirplane().getId());
        validator.requireExistingNoticeBoard(updated.getNoticeBoard().getId());

        existing.setName(updated.getName());
        existing.setDepartureTime(updated.getDepartureTime());
        existing.setAirplane(updated.getAirplane());
        existing.setNoticeBoard(updated.getNoticeBoard());

        return flightRepository.save(existing);
    }

    @Override
    public boolean delete(String id) {
        validator.requireExisting(id);

        List<FlightAssignment> assignments = flightAssignmentRepository.findByFlight_Id(id);
        flightAssignmentRepository.deleteAll(assignments);

        List<Ticket> tickets = ticketRepository.findByFlight_Id(id);
        for (Ticket t : tickets) {
            List<Luggage> luggages = luggageRepository.findByTicket_Id(t.getId(), Sort.unsorted());
            luggageRepository.deleteAll(luggages);
        }
        ticketRepository.deleteAll(tickets);

        flightRepository.deleteById(id);
        return true;
    }


    @Override
    public List<Flight> findAll() { return flightRepository.findAll(); }

    @Override
    public List<Flight> findAll(Sort sort) { return flightRepository.findAll(sort); }

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
    public Optional<Flight> findById(String id) { return flightRepository.findById(id); }

    @Override
    public List<Flight> findByNoticeBoardId(String noticeBoardId) {
        return flightRepository.findByNoticeBoard_Id(noticeBoardId);
    }

    @Override
    public Flight getById(String id) { return validator.requireExisting(id); }
}