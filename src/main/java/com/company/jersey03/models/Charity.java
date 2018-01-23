package com.company.jersey03.models;

/**
 * @author Jeff Risberg
 * @since 10/26/17
 */
public class Charity {
    protected Integer id;
    protected String name;
    protected String ein;
    protected String website;

    public Charity(int id, String name, String ein, String website) {
        this.id = id;
        this.name = name;
        this.ein = ein;
        this.website = website;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEin() {
        return ein;
    }

    public String getWebsite() {
        return website;
    }
}
