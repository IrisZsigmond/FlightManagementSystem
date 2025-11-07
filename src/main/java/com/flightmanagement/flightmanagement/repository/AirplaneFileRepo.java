package com.flightmanagement.flightmanagement.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.flightmanagement.flightmanagement.config.AppDataProperties;
import com.flightmanagement.flightmanagement.model.Airplane;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * File-backed repository for Airplane.
 * Loads/saves airplanes into airplanes.json under the runtime data directory.
 */
@Repository
@Primary
public class AirplaneFileRepo extends InFileRepository<Airplane, String> {

    public AirplaneFileRepo(AppDataProperties props, ResourceLoader resourceLoader) {
        super(
                "airplanes.json",                          // file name
                new TypeReference<List<Airplane>>() {},    // type hint
                Airplane::getId,                           // id getter
                props,
                resourceLoader
        );
    }
}
