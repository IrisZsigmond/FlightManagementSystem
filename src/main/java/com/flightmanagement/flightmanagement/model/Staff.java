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

    //getters and setters
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

    //returns a string representation of the Staff object
    @Override
    public String toString() {
        return "Staff {name = " + name + " }";
    }
}
