package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Ticket;
import com.flightmanagement.flightmanagement.model.enums.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

    List<Ticket> findByPassenger_Id(String passengerId);

    List<Ticket> findByFlight_Id(String flightId);

    List<Ticket> findByCategory(TicketCategory category);
}
