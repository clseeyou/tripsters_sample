package com.tripsters.android.model;

import java.util.List;

public abstract class ListBean<T> extends ResultBean {
    public abstract List<T> getList();
}
