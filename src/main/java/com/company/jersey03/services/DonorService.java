package com.company.jersey03.services;

import com.company.jersey03.common.FilterDesc;
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
        donors.add(new Donor(1, "John", "Smith"));
        donors.add(new Donor(2, "Bill", "Jones"));
        donors.add(new Donor(3, "Tom", "Kennedy"));
        donors.add(new Donor(4, "Jack", "Underhill"));
        donors.add(new Donor(5, "Sally", "Starr"));
        donors.add(new Donor(6, "Henry", "Adams"));
        donors.add(new Donor(7, "Paul", "Jones"));
        donors.add(new Donor(8, "Steven", "Hawking"));
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
                    switch (filterDesc.getField().getName()) {
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
