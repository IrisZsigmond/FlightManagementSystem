package com.flightmanagement.flightmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "staff")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "staff_type")
public abstract class Staff {

    @Id
    private String id;

    private String name;

    public Staff() {}

    public Staff(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
