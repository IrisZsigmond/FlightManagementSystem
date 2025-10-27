package com.flightmanagement.flightmanagement.repository;

import com.flightmanagement.flightmanagement.model.Luggage;
import org.springframework.stereotype.Repository;

@Repository
public class LuggageRepositoryInMemory extends BaseRepositoryInMemory<Luggage, String> {

    public LuggageRepositoryInMemory() {
        super(Luggage::getId);
    }
}

