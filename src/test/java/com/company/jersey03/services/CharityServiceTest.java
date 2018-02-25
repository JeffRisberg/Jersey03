package com.company.jersey03.services;

import com.company.jersey03.models.Charity;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Jeff Risberg
 * @since 11/03/17
 */
public class CharityServiceTest {

    private CharityService charityService;

    @Before
    public void setUp() throws Exception {
        charityService = new CharityService();
    }

    @Test
    public void testGetOne() {
        Charity charity = charityService.getCharity(1);

        assertEquals("Red Cross", charity.getName());
    }

    @Test
    public void testGetList() {
        List<Charity> charityList = charityService.getCharities(50, 0, null);

        assertNotNull(charityList);
        assertEquals(6, charityList.size());
    }
}
