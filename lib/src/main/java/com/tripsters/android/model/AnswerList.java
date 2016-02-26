package com.tripsters.android.model;

import java.util.ArrayList;
import java.util.List;

public class AnswerList extends ListNetResult<Answer> {

    private List<Answer> data;

    @Override
    public List<Answer> getList() {
        if (data == null) {
            return new ArrayList<Answer>();
        }

        return data;
    }
}
