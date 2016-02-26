package com.tripsters.android.model;

import java.util.List;

public abstract class ListNetResult<T> extends NetResult {
    public abstract List<T> getList();
}
