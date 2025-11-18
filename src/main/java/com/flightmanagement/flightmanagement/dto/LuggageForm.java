package com.flightmanagement.flightmanagement.dto;

import com.flightmanagement.flightmanagement.model.enums.LuggageSize;
import com.flightmanagement.flightmanagement.model.enums.LuggageStatus;

/**
 * Form DTO for creating/updating Luggage.
 */
public class LuggageForm {

    private String id;
    private String ticketId;
    private LuggageStatus status;
    private LuggageSize size;

    public LuggageForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public LuggageStatus getStatus() {
        return status;
    }

    public void setStatus(LuggageStatus status) {
        this.status = status;
    }

    public LuggageSize getSize() {
        return size;
    }

    public void setSize(LuggageSize size) {
        this.size = size;
    }
}
