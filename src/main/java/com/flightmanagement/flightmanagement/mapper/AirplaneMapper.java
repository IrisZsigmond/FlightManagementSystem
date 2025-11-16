package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.AirplaneForm;
import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.service.FlightService;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AirplaneMapper {

    private final FlightService flightService;

    public AirplaneMapper(FlightService flightService) {
        this.flightService = flightService;
    }

    public Airplane toEntity(AirplaneForm form) {
        Airplane airplane = new Airplane();
        airplane.setId(form.getId());
        airplane.setNumber(form.getNumber());
        airplane.setCapacity(form.getCapacity());

        List<Flight> flights = resolveFlights(form.getFlightIds());
        airplane.setFlights(flights);

        return airplane;
    }

    public void updateEntityFromForm(Airplane airplane, AirplaneForm form) {
        airplane.setNumber(form.getNumber());
        airplane.setCapacity(form.getCapacity());

        List<Flight> flights = resolveFlights(form.getFlightIds());
        airplane.setFlights(flights);
    }

    public AirplaneForm toForm(Airplane airplane) {
        AirplaneForm form = new AirplaneForm();
        form.setId(airplane.getId());
        form.setNumber(airplane.getNumber());
        form.setCapacity(airplane.getCapacity());

        if (airplane.getFlights() != null) {
            List<String> ids = airplane.getFlights().stream()
                    .filter(Objects::nonNull)
                    .map(Flight::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            form.setFlightIds(ids);
        }

        return form;
    }

    private List<Flight> resolveFlights(List<String> flightIds) {
        if (flightIds == null) return List.of();
        return flightIds.stream()
                .filter(id -> id != null && !id.isBlank())
                .map(id -> flightService.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
