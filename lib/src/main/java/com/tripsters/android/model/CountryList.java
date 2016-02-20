package com.tripsters.android.model;

import java.util.ArrayList;
import java.util.List;

public class CountryList extends ListBean<Country> {

    private List<Country> data;

    public List<Country> getList() {
        if (data == null) {
            return new ArrayList<Country>();
        }

        return data;
    }
}
