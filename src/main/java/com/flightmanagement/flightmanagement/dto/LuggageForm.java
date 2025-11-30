package com.flightmanagement.flightmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LuggageForm {

    @NotBlank(message = "ID is required")
    @Pattern(regexp = "^L\\d{3}$", message = "ID must follow format Lxxx (e.g., L001)")
    private String id;

    @NotBlank(message = "Ticket ID is required")
    @Pattern(regexp = "^T\\d{3}$", message = "Ticket ID must follow format Txxx (e.g., T001)")
    private String ticketId;

    @NotBlank(message = "Status is required")
    private String status;

    @NotBlank(message = "Size is required")
    private String size;

    public LuggageForm() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
}
