package com.company.jersey03.models;

/**
 * @author Jeff Risberg
 * @since 10/26/17
 */
public class Donor {
    protected Integer id;
    protected String firstName;
    protected String lastName;

    public Donor(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
