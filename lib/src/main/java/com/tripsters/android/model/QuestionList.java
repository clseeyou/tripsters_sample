package com.tripsters.android.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionList extends ListNetResult<Question> {

    private List<Question> data;

    @Override
    public List<Question> getList() {
        if (data == null) {
            return new ArrayList<Question>();
        }

        return data;
    }
}
