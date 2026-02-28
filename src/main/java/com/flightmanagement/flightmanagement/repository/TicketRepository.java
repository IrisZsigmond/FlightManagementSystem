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

    List<Ticket> findByPassenger_Id(String passengerId);
    List<Ticket> findByFlight_Id(String flightId);
    List<Ticket> findByCategory(TicketCategory category);

    boolean existsByPassenger_Id(String passengerId);
    boolean existsBySeatNumberAndFlight_Id(String seatNumber, String flightId);
    boolean existsBySeatNumberAndFlight_IdAndIdNot(String seatNumber, String flightId, String excludeId);

    List<Ticket> findByPriceBetween(Double minPrice, Double maxPrice, Sort sort);

    List<Ticket> findByCategoryAndPriceBetween(TicketCategory category, Double minPrice, Double maxPrice, Sort sort);
}