package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Luggage;
import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface LuggageRepository extends JpaRepository<Luggage, String> {

    List<Luggage> findByTicket_Id(String ticketId);

    List<Luggage> findByStatus(LuggageStatus status);

    List<Luggage> findBySize(LuggageSize size);
}
