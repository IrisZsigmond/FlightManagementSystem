package com.flightmanagement.flightmanagement.model;

public abstract class Staff {
    private String id;
    private String name;

    public Staff(String Id, String name) {
        this.id = Id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String Id) {
        this.id = Id;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Staff {name = " + name + " }";
    }
}
