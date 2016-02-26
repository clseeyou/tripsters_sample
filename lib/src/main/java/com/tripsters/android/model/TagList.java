package com.tripsters.android.model;

import java.util.ArrayList;
import java.util.List;

public class TagList extends ListNetResult<Tag> {

    private List<Tag> data;

    @Override
    public List<Tag> getList() {
        if (data == null) {
            return new ArrayList<Tag>();
        }

        return data;
    }
}
