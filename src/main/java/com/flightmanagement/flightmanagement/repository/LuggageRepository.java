package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface LuggageRepository extends JpaRepository<Luggage, String> {

    // Existing (extins pentru Sort)
    List<Luggage> findByTicket_Id(String ticketId, Sort sort);
    List<Luggage> findByStatus(LuggageStatus status, Sort sort);
    List<Luggage> findBySize(LuggageSize size, Sort sort);

    // Filtrare combinată (2 criterii)
    List<Luggage> findByStatusAndSize(LuggageStatus status, LuggageSize size, Sort sort);
    List<Luggage> findByTicket_IdAndStatus(String ticketId, LuggageStatus status, Sort sort);
    List<Luggage> findByTicket_IdAndSize(String ticketId, LuggageSize size, Sort sort);

    // Filtrare combinată (3 criterii) - CEL MAI COMPLEX
    List<Luggage> findByTicket_IdAndStatusAndSize(
            String ticketId,
            LuggageStatus status,
            LuggageSize size,
            Sort sort
    );

    boolean existsByTicket_Id(String id);
}