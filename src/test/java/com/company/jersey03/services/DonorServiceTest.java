package com.company.jersey03.services;

import com.company.jersey03.models.Donor;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Jeff Risberg
 * @since 11/03/17
 */
public class DonorServiceTest {

    private DonorService donorService;

    @Before
    public void setUp() throws Exception {
        donorService = new DonorService();
    }

    @Test
    public void testGetOne() {
        Donor donor = donorService.getDonor(1);

        assertEquals("John", donor.getFirstName());
        assertEquals("Smith", donor.getLastName());
    }

    @Test
    public void testGetList() {
        List<Donor> donorList = donorService.getDonors(50, 0, null);

        assertNotNull(donorList);
        assertEquals(8, donorList.size());
    }
}
