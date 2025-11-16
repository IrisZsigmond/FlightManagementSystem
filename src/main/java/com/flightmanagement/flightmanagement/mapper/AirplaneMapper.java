package com.flightmanagement.flightmanagement.mapper;

import com.flightmanagement.flightmanagement.dto.AirplaneForm;
import com.flightmanagement.flightmanagement.model.Airplane;
import com.flightmanagement.flightmanagement.service.FlightService;
import org.springframework.stereotype.Component;

@Component
public class AirplaneMapper {

    private final FlightService flightService;

    public AirplaneMapper(FlightService flightService) {
        this.flightService = flightService;
    }

    /** Form -> Entity (no relationship mapping) */
    public Airplane toEntity(AirplaneForm form) {
        Airplane airplane = new Airplane();
        airplane.setId(form.getId());
        airplane.setNumber(form.getNumber());
        airplane.setCapacity(form.getCapacity());
        // DO NOT set flights here (projection only, @JsonIgnore)
        return airplane;
    }

    /** Update existing entity from form (no relationship mapping) */
    public void updateEntityFromForm(Airplane airplane, AirplaneForm form) {
        airplane.setNumber(form.getNumber());
        airplane.setCapacity(form.getCapacity());
        // DO NOT set flights here
    }

    /** Entity -> Form (only scalar fields) */
    public AirplaneForm toForm(Airplane airplane) {
        AirplaneForm form = new AirplaneForm();
        form.setId(airplane.getId());
        form.setNumber(airplane.getNumber());
        form.setCapacity(airplane.getCapacity());
        // No flightIds on the form anymore
        return form;
    }

    /** Optional: projection helper if you want to enrich an airplane with its flights for views */
    public Airplane withFlights(Airplane airplane) {
        if (airplane == null) return null;
        airplane.setFlights(flightService.findByAirplaneId(airplane.getId()));
        return airplane;
    }
}
