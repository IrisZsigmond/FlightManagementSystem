package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Airplane;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface AirplaneRepository extends JpaRepository<Airplane, String> {

    Optional<Airplane> findByNumber(int number);

    boolean existsByNumber(int number);
}
