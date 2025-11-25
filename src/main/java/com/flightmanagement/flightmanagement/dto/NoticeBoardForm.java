package com.flightmanagement.flightmanagement.dto;

public class NoticeBoardForm {

    private String id;
    private String date; // store as String in form

    public NoticeBoardForm() {}

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
