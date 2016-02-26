package com.tripsters.android.model;

import java.util.ArrayList;
import java.util.List;

public class CityList extends ListNetResult<City> {

    private List<City> data;

    public List<City> getList() {
        if (data == null) {
            return new ArrayList<City>();
        }

        return data;
    }
}
