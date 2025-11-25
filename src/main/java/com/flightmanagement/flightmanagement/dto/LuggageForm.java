package com.flightmanagement.flightmanagement.dto;

public class LuggageForm {

    private String id;
    private String ticketId;
    private String status;  // LuggageStatus as String
    private String size;    // LuggageSize as String

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
