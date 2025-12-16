package com.flightmanagement.flightmanagement.service;

import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import com.flightmanagement.flightmanagement.repository.TicketRepository;
import com.flightmanagement.flightmanagement.repository.LuggageRepository;
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
    private final LuggageRepository luggageRepository; // Adăugat pentru Cascade Delete
    private final TicketValidator validator;

    // CONSTRUCTOR ACTUALIZAT
    public TicketServiceImpl(TicketRepository ticketRepository,
                             LuggageRepository luggageRepository,
                             TicketValidator validator) {
        this.ticketRepository = ticketRepository;
        this.luggageRepository = luggageRepository;
        this.validator = validator;
    }

    // ---------------- CREATE ----------------
    @Override
    public Ticket save(Ticket ticket) {
        validator.assertIdUnique(ticket.getId());
        // ... (alte validări)
        validator.assertSeatAvailable(ticket.getFlight().getId(), ticket.getSeatNumber(), null);
        return ticketRepository.save(ticket);
    }

    // ---------------- UPDATE ----------------
    @Override
    public Ticket update(String id, Ticket updated) {
        // ... (logica update)
        validator.assertSeatAvailable(updated.getFlight().getId(), updated.getSeatNumber(), id);
        updated.setId(id);
        return ticketRepository.save(updated);
    }

    // ---------------- DELETE (CASCADE) ----------------
    @Override
    public boolean delete(String id) {
        // 1. Validare (verifică dacă zborul este în viitor)
        validator.requireExisting(id);
        validator.assertCanBeDeleted(id);

        // 2. CASCADE: Ștergem bagajele asociate (Luggage -> Ticket)
        // Folosim Sort.unsorted() deoarece metoda din LuggageRepository așteaptă 2 argumente.
        List<Luggage> luggages = luggageRepository.findByTicket_Id(id, Sort.unsorted());
        luggageRepository.deleteAll(luggages);

        // 3. Ștergem biletul
        ticketRepository.deleteById(id);
        return true;
    }

    // ---------------- READ / SORT / HELPERS ----------------

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findAll() { return ticketRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findAll(Sort sort) { return ticketRepository.findAll(sort); }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ticket> findById(String id) { return ticketRepository.findById(id); }

    @Override
    public List<Ticket> findByPassengerId(String passengerId) { return ticketRepository.findByPassenger_Id(passengerId); }

    @Override
    public List<Ticket> findByFlightId(String flightId) { return ticketRepository.findByFlight_Id(flightId); }

    @Override
    public List<Ticket> findByCategory(TicketCategory category) { return ticketRepository.findByCategory(category); }

    @Override
    public double calculateTotalPriceForPassenger(String passengerId) {
        return findByPassengerId(passengerId).stream().mapToDouble(Ticket::getPrice).sum();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> search(Double minPrice, Double maxPrice, TicketCategory category, Sort sort) {
        Sort safeSort = (sort != null) ? sort : Sort.by(Sort.Direction.ASC, "id");
        Double min = (minPrice != null) ? minPrice : 0.0;
        Double max = (maxPrice != null) ? maxPrice : Double.MAX_VALUE;

        if (category != null) {
            return ticketRepository.findByCategoryAndPriceBetween(category, min, max, safeSort);
        }
        return ticketRepository.findByPriceBetween(min, max, safeSort);
    }
}