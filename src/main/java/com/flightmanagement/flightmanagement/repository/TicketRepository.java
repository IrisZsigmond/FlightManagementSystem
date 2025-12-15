package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface TicketRepository extends JpaRepository<Ticket, String> {

    // 1. Existing Methods (fără Sort)
    List<Ticket> findByPassenger_Id(String passengerId);
    List<Ticket> findByFlight_Id(String flightId);
    List<Ticket> findByCategory(TicketCategory category);

    // 2. Existing Validations
    boolean existsByPassenger_Id(String passengerId);
    boolean existsBySeatNumberAndFlight_Id(String seatNumber, String flightId);
    boolean existsBySeatNumberAndFlight_IdAndIdNot(String seatNumber, String flightId, String excludeId);

    // 3. METODE NOI PENTRU SEARCH + SORT

    // Căutare doar după interval de preț + Sort
    List<Ticket> findByPriceBetween(Double minPrice, Double maxPrice, Sort sort);

    // Căutare combinată: Categorie + Interval de preț + Sort
    List<Ticket> findByCategoryAndPriceBetween(TicketCategory category, Double minPrice, Double maxPrice, Sort sort);
}