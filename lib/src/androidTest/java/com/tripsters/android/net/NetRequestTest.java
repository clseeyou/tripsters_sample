package com.tripsters.android.net;

import android.test.AndroidTestCase;

import com.tripsters.android.model.CityList;
import com.tripsters.android.model.CountryList;

/**
 * Created by chenli on 16/2/22.
 */
public class NetRequestTest extends AndroidTestCase {

    public void testGetSupportCountry() throws Exception {
        CountryList countryList = NetRequest.getSupportCountry(getContext());
        assertNotNull(countryList);
        assertTrue(countryList.isSuccessful());
        assertFalse(countryList.getList().isEmpty());
    }

    public void testGetSupportCity() throws Exception {
        CityList cityList = NetRequest.getSupportCity(getContext(), "th");
        assertNotNull(cityList);
        assertTrue(cityList.isSuccessful());
        assertFalse(cityList.getList().isEmpty());
    }
}