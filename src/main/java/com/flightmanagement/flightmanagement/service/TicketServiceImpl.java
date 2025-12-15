package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import com.flightmanagement.flightmanagement.repository.TicketRepository;
import com.flightmanagement.flightmanagement.validations.TicketValidator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketValidator validator;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             TicketValidator validator) {
        this.ticketRepository = ticketRepository;
        this.validator = validator;
    }

    // ---------------- CREATE ----------------
    @Override
    public Ticket save(Ticket ticket) {
        validator.assertIdUnique(ticket.getId());
        validator.requireExistingPassenger(ticket.getPassenger().getId());
        validator.requireExistingFlight(ticket.getFlight().getId());

        validator.assertSeatAvailable(
                ticket.getFlight().getId(),
                ticket.getSeatNumber(),
                null
        );

        return ticketRepository.save(ticket);
    }

    // ---------------- READ / SORT ----------------
    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findAll(Sort sort) {
        return ticketRepository.findAll(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ticket> findById(String id) {
        return ticketRepository.findById(id);
    }

    // ---------------- UPDATE ----------------
    @Override
    public Ticket update(String id, Ticket updated) {
        Ticket existing = validator.requireExisting(id);

        validator.requireExistingPassenger(updated.getPassenger().getId());
        validator.requireExistingFlight(updated.getFlight().getId());

        validator.assertSeatAvailable(
                updated.getFlight().getId(),
                updated.getSeatNumber(),
                id
        );

        updated.setId(id);
        return ticketRepository.save(updated);
    }

    // ---------------- DELETE ----------------
    @Override
    public boolean delete(String id) {
        validator.requireExisting(id);
        validator.assertCanBeDeleted(id);
        ticketRepository.deleteById(id);
        return true;
    }

    // ---------------- HELPERS ----------------
    @Override
    public List<Ticket> findByPassengerId(String passengerId) {
        return ticketRepository.findByPassenger_Id(passengerId);
    }

    @Override
    public List<Ticket> findByFlightId(String flightId) {
        return ticketRepository.findByFlight_Id(flightId);
    }

    @Override
    public List<Ticket> findByCategory(TicketCategory category) {
        return ticketRepository.findByCategory(category);
    }

    @Override
    public double calculateTotalPriceForPassenger(String passengerId) {
        return findByPassengerId(passengerId)
                .stream()
                .mapToDouble(Ticket::getPrice)
                .sum();
    }

    // ---------------- SEARCH + SORT (NOU) ----------------
    @Override
    @Transactional(readOnly = true)
    public List<Ticket> search(Double minPrice, Double maxPrice, TicketCategory category, Sort sort) {

        Sort safeSort = (sort != null) ? sort : Sort.by(Sort.Direction.ASC, "id");

        // Tratăm null-urile pentru preț ca fiind interval maxim
        Double min = (minPrice != null) ? minPrice : 0.0;
        Double max = (maxPrice != null) ? maxPrice : Double.MAX_VALUE;

        // Cazul 1: Filtrare și după Categorie și după Preț
        if (category != null) {
            return ticketRepository.findByCategoryAndPriceBetween(category, min, max, safeSort);
        }

        // Cazul 2: Filtrare doar după Preț (Categorie e null, deci "Toate categoriile")
        return ticketRepository.findByPriceBetween(min, max, safeSort);
    }
}