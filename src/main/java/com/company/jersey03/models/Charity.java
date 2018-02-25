package com.company.jersey03.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Jeff Risberg
 * @since 10/26/17
 */
@Entity
@Table(name = "charities")
public class Charity extends AbstractDatedEntity {
    @Column(name = "name")
    protected String name;

    @Column(name = "ein")
    protected String ein;

    @Column(name = "website")
    protected String website;

    public Charity(Long id, String name, String ein, String website) {
        this.id = id;
        this.name = name;
        this.ein = ein;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEin() {
        return ein;
    }

    public void setEin(String ein) {
        this.ein = ein;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
