package com.flightmanagement.flightmanagement.config;

import com.flightmanagement.flightmanagement.model.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalBindingConfig {

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        // --- Lists ---

        binder.registerCustomEditor(List.class, "luggages",
                new ListEditor<>(id -> new Luggage(id, null, null, null)));

        binder.registerCustomEditor(List.class, "assignments",
                new ListEditor<>(id -> new FlightAssignment(id, null, null)));

        binder.registerCustomEditor(List.class, "flights",
                new ListEditor<>(id -> new Flight(id, null, null, 0, null, null)));

        binder.registerCustomEditor(List.class, "flightsOfTheDay",
                new ListEditor<>(id -> new Flight(id, null, null, 0, null, null)));

        binder.registerCustomEditor(List.class, "tickets",
                new ListEditor<>(id -> new Ticket(id, null, null, null, 0, null, null)));


        // --- Single Ticket (required for Luggage.ticket) ---

        binder.registerCustomEditor(Ticket.class, "ticket",
                new PropertyEditorSupport() {
                    @Override
                    public void setAsText(String text) {
                        if (text == null || text.isBlank()) {
                            setValue(null);
                            return;
                        }
                        setValue(new Ticket(text.trim(), null, null, null, 0, null, null));
                    }

                    @Override
                    public String getAsText() {
                        Ticket t = (Ticket) getValue();
                        return (t == null || t.getId() == null) ? "" : t.getId();
                    }
                });
    }

    private static class ListEditor<T> extends PropertyEditorSupport {

        private final java.util.function.Function<String, T> creator;

        public ListEditor(java.util.function.Function<String, T> creator) {
            this.creator = creator;
        }

        @Override
        public void setAsText(String text) {
            if (text == null || text.isBlank()) {
                setValue(List.of());
                return;
            }

            List<T> list = Arrays.stream(text.split(","))
                    .map(String::trim)
                    .filter(id -> !id.isEmpty())
                    .map(creator)
                    .collect(Collectors.toList());

            setValue(list);
        }

        @Override
        public String getAsText() {
            List<?> list = (List<?>) getValue();
            if (list == null || list.isEmpty()) return "";

            return list.stream()
                    .map(obj -> {
                        try {
                            var method = obj.getClass().getMethod("getId");
                            Object val = method.invoke(obj);
                            return val != null ? val.toString() : "";
                        } catch (Exception e) {
                            return obj.toString();
                        }
                    })
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.joining(", "));
        }
    }
}
