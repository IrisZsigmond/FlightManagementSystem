package com.flightmanagement.flightmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class NoticeBoardForm {

    @NotBlank(message = "NoticeBoard ID is required.")
    @Pattern(regexp = "^N\\d{3}$", message = "ID must be in format N001.")
    private String id;

    @NotBlank(message = "Date is required.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in format yyyy-MM-dd.")
    private String date; // stored as String

    public NoticeBoardForm() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
