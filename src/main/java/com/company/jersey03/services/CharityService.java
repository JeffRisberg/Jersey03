package com.company.jersey03.services;

import com.company.jersey03.common.model.jooq.query.FilterDesc;
import com.company.jersey03.common.model.jooq.query.SortDesc;
import com.company.jersey03.common.model.jooq.query.SortDirection;
import com.company.jersey03.models.Charity;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeff Risberg
 * @since 10/26/17
 */
@Singleton
public class CharityService {

    protected List<Charity> charities = new ArrayList<Charity>();

    public CharityService() {
        charities.add(new Charity(1, "Red Cross", "66-555555", "www.redcross.org"));
        charities.add(new Charity(2, "ASPCA", "99-555555", "www.aspca.org"));
        charities.add(new Charity(3, "United Way", "33-555555", "www.unitedway.org"));
        charities.add(new Charity(4, "American Heart Assoc", "55-555555", "www.aha.org"));
        charities.add(new Charity(5, "Polar Bear Assoc", "45-555555", "www.polarbears.org"));
        charities.add(new Charity(6, "Stanford University", "37-555555", "www.stanford.edu"));
    }

    public Charity getCharity(int id) {
        for (Charity charity : charities) {
            if (charity.getId() == id)
                return charity;
        }
        return null;
    }

    public List<Charity> getCharities(int limit, int offset, List<FilterDesc> filterDescs, List<SortDesc> sortDescs) {
        List<Charity> result = applyFilter(filterDescs);

        if (sortDescs != null && sortDescs.size() > 0) {
            SortDesc sortDesc = sortDescs.get(0);

            if (sortDesc.getDirection() == SortDirection.Ascending) {
                if (sortDesc.getField().getName().equals("name"))
                    result.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
                if (sortDesc.getField().getName().equals("ein"))
                    result.sort((p1, p2) -> p1.getEin().compareTo(p2.getEin()));
                if (sortDesc.getField().getName().equals("website"))
                    result.sort((p1, p2) -> p1.getWebsite().compareTo(p2.getWebsite()));
            } else {
                if (sortDesc.getField().getName().equals("name"))
                    result.sort((p1, p2) -> p2.getName().compareTo(p1.getName()));
                if (sortDesc.getField().getName().equals("ein"))
                    result.sort((p1, p2) -> p2.getEin().compareTo(p1.getEin()));
                if (sortDesc.getField().getName().equals("website"))
                    result.sort((p1, p2) -> p2.getWebsite().compareTo(p1.getWebsite()));
            }
        }

        if (offset > 0 && offset >= result.size())
            result = new ArrayList<Charity>();
        else if (offset > 0)
            result = result.subList(offset, result.size());

        return result;
    }

    public long getCharitiesCount(List<FilterDesc> filterDescs) {
        List<Charity> result = applyFilter(filterDescs);

        return (long) result.size();
    }

    private List<Charity> applyFilter(List<FilterDesc> filterDescs) {
        List<Charity> result = new ArrayList<Charity>();

        for (Charity charity : charities) {
            boolean accepted = true;

            if (filterDescs != null) {
                for (FilterDesc filterDesc : filterDescs) {
                    switch (filterDesc.getField().getName()) {
                        case "name":
                            if (!charity.getName().equalsIgnoreCase((String) filterDesc.getValue()))
                                accepted = false;
                            break;
                        case "ein":
                            if (!charity.getEin().equals(filterDesc.getValue()))
                                accepted = false;
                            break;
                        case "website":
                            if (!charity.getWebsite().equalsIgnoreCase((String) filterDesc.getValue()))
                                accepted = false;
                            break;
                    }
                }
            }

            if (accepted)
                result.add(charity);
        }
        return result;
    }
}
