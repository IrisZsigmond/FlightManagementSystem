package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Passenger;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface PassengerRepository extends JpaRepository<Passenger, String> {

    // 1. METODE EXISTENTE (Fără Sort - pentru compatibilitate cu cod vechi, dacă e cazul)
    List<Passenger> findByNameIgnoreCase(String name);
    List<Passenger> findByCurrencyIgnoreCase(String currency);

    // 2. METODE NOI PENTRU SEARCH (CU Sort)

    // Căutare după nume (parțială) + Sort
    List<Passenger> findByNameContainingIgnoreCase(String name, Sort sort);

    // Căutare după monedă (parțială) + Sort
    List<Passenger> findByCurrencyContainingIgnoreCase(String currency, Sort sort);

    // FILTRARE COMBINATĂ (Nume AND Monedă) + Sort
    List<Passenger> findByNameContainingIgnoreCaseAndCurrencyContainingIgnoreCase(
            String name,
            String currency,
            Sort sort
    );
}