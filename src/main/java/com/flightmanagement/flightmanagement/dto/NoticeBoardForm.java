package com.flightmanagement.flightmanagement.dto;

/**
 * Form DTO for creating/updating NoticeBoard.
 * Uses a String date (yyyy-MM-dd) to bind nicely to HTML.
 */
public class NoticeBoardForm {

    private String id;
    private String date; // ISO date like "2025-11-18"

    public NoticeBoardForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
