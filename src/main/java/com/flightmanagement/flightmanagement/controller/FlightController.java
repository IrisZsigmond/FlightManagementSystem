package com.flightmanagement.flightmanagement.controller;

import com.flightmanagement.flightmanagement.model.Flight;
import com.flightmanagement.flightmanagement.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
@Validated
public class FlightController {

    @GetMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Information regarding flights!";
    }

    private final FlightService flights;

    public FlightController(FlightService flights) {
        this.flights = flights;
    }

    // --- CRUD ---

    @PostMapping
    public ResponseEntity<Flight> create(@RequestBody Flight body) {
        Flight created = flights.save(body);
        // Build Location: /api/v1/flights/{id}
        return ResponseEntity.created(URI.create("/api/v1/flights/" + created.getId()))
                .body(created);
    }

    @GetMapping
    public ResponseEntity<List<Flight>> findAll(
            @RequestParam(value = "minScore", required = false) Integer minScore,
            @RequestParam(value = "airplaneId", required = false) String airplaneId,
            @RequestParam(value = "noticeBoardId", required = false) String noticeBoardId) {

        // simple filter shortcuts; for richer needs, create explicit endpoints
        if (minScore != null) return ResponseEntity.ok(flights.topRated(minScore));
        if (airplaneId != null) return ResponseEntity.ok(flights.findByAirplaneId(airplaneId));
        if (noticeBoardId != null) return ResponseEntity.ok(flights.findByNoticeBoardId(noticeBoardId));
        return ResponseEntity.ok(flights.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> findOne(@PathVariable String id) {
        return flights.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> update(@PathVariable String id, @RequestBody Flight body) {
        Flight updated = flights.update(id, body);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean removed = flights.delete(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // --- Domain-specific endpoints ---

    @GetMapping("/search/by-name")
    public ResponseEntity<List<Flight>> searchByName(@RequestParam("q") String term) {
        return ResponseEntity.ok(flights.findByNameContains(term));
    }

    @GetMapping("/search/by-departure")
    public ResponseEntity<List<Flight>> byDeparture(
            @RequestParam("from") String from,
            @RequestParam("to") String to) {
        // expect HH:mm; delegate parsing here for simplicity
        return ResponseEntity.ok(
                flights.findByDepartureBetween(LocalTime.parse(from), LocalTime.parse(to))
        );
    }

    @GetMapping("/search/by-ticket/{ticketId}")
    public ResponseEntity<List<Flight>> byTicket(@PathVariable String ticketId) {
        return ResponseEntity.ok(flights.findByTicketId(ticketId));
    }

    @GetMapping("/search/by-staff/{staffId}")
    public ResponseEntity<List<Flight>> byStaff(@PathVariable String staffId) {
        return ResponseEntity.ok(flights.findByStaffId(staffId));
    }
}
