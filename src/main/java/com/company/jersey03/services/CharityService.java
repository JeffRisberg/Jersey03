package com.company.jersey03.services;

import com.company.jersey03.common.FilterDesc;
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
        charities.add(new Charity(1L, "Red Cross", "66-555555", "www.redcross.org"));
        charities.add(new Charity(2L, "ASPCA", "99-555555", "www.aspca.org"));
        charities.add(new Charity(3L, "United Way", "33-555555", "www.unitedway.org"));
        charities.add(new Charity(4L, "American Heart Assoc", "55-555555", "www.aha.org"));
        charities.add(new Charity(5L, "Polar Bear Assoc", "45-555555", "www.polarbears.org"));
        charities.add(new Charity(6L, "Stanford University", "37-555555", "www.stanford.edu"));
        charities.add(new Charity(7L, "University of Southern California", "88-555555", "www.usc.edu"));
        charities.add(new Charity(8L, "Scripps College", "88-321458", "www.scrippscollege.edu"));
    }

    public Charity getCharity(int id) {
        for (Charity charity : charities) {
            if (charity.getId() == id)
                return charity;
        }
        return null;
    }

    public List<Charity> getCharities(int limit, int offset, List<FilterDesc> filterDescs) {
        List<Charity> result = applyFilter(filterDescs);

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
                    switch (filterDesc.getField()) {
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
