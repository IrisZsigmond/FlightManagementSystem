package com.flightmanagement.flightmanagement.model;

/** Represents an airport employee in the flight management system.
 * Inherits common attributes from Staff (such as ID and name)
 * and adds specific details like designation and department. */

public class AirportEmployee extends Staff {
    private String designation;
    private String department;

    public AirportEmployee() {
        super();
    }

    public AirportEmployee(String Id, String name, String designation, String department) {
        super(Id, name);
        this.designation = designation;
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "AirportEmplyees{" +
                " name='" + getName() + '\'' +
                ", designation='" + designation + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
