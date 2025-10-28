package com.flightmanagement.flightmanagement.model;

/* Represents a staff member in the flight management system.
 * Contains details such as id and name */

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
    public void setId(String Id) {
        this.id = Id;
    }

    public String getName() {
        return name;
    }
    public void setName (String name) {
        this.name = name;
    }

    @Override
    public abstract String toString();
}
