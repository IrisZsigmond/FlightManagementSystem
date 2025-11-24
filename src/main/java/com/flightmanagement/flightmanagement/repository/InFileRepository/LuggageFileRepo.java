package com.flightmanagement.flightmanagement.repository.InFileRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.flightmanagement.flightmanagement.config.AppDataProperties;
import com.flightmanagement.flightmanagement.model.Luggage;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * File-backed repository for managing Luggage entities.
 * Replaces the in-memory repository and persists data into luggages.json.
 */
@Repository
public class LuggageFileRepo extends InFileRepository<Luggage, String> {

    public LuggageFileRepo(AppDataProperties props, ResourceLoader resourceLoader) {
        super(
                "luggages.json",
                new TypeReference<List<Luggage>>() {},
                Luggage::getId,
                props,
                resourceLoader
        );
    }
}
