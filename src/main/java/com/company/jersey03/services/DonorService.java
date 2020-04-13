package com.company.jersey03.services;

import com.company.jersey03.models.Donor;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeff Risberg
 * @since 10/26/17
 */
@Singleton
public class DonorService {

    protected List<Donor> donors = new ArrayList<Donor>();

    public DonorService() {
        donors.add(new Donor(1L, "John", "Smith"));
        donors.add(new Donor(2L, "Bill", "Jones"));
        donors.add(new Donor(3L, "Tom", "Kennedy"));
        donors.add(new Donor(4L, "Jack", "Underhill"));
        donors.add(new Donor(5L, "Sally", "Starr"));
        donors.add(new Donor(6L, "Henry", "Adams"));
        donors.add(new Donor(7L, "Paul", "Jones"));
        donors.add(new Donor(8L, "Steven", "Hawking"));
    }

    public Donor getDonor(int id) {
        for (Donor donor : donors) {
            if (donor.getId() == id)
                return donor;
        }
        return null;
    }

    public List<Donor> getDonors(int limit, int offset, List<FilterDesc> filterDescs) {
        List<Donor> result = applyFilter(filterDescs);

        if (offset > 0 && offset >= result.size())
            result = new ArrayList<Donor>();
        else if (offset > 0)
            result = result.subList(offset, result.size());

        return result;
    }

    public long getDonorsCount(List<FilterDesc> filterDescs) {
        List<Donor> result = applyFilter(filterDescs);

        return (long) result.size();
    }

    private List<Donor> applyFilter(List<FilterDesc> filterDescs) {
        List<Donor> result = new ArrayList<Donor>();

        for (Donor donor : donors) {
            boolean accepted = true;

            if (filterDescs != null) {
                for (FilterDesc filterDesc : filterDescs) {
                    switch (filterDesc.getField()) {
                        case "firstName":
                            if (!donor.getFirstName().equalsIgnoreCase((String) filterDesc.getValue()))
                                accepted = false;
                            break;
                        case "lastName":
                            if (!donor.getLastName().equalsIgnoreCase((String) filterDesc.getValue()))
                                accepted = false;
                            break;
                    }
                }
            }

            if (accepted)
                result.add(donor);
        }

        return result;
    }
}
