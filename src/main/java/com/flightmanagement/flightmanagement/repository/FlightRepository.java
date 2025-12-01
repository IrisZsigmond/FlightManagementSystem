package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Flight;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface FlightRepository extends JpaRepository<Flight, String> {

    boolean existsByAirplane_Id(String airplaneId);

    boolean existsByName(String name);

    List<Flight> findByAirplane_Id(String airplaneId);

    boolean existsByNoticeBoard_Id(String noticeBoardId);
}
