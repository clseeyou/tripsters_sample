package com.tripsters.android.model;

public class ResultBean extends NetBean {

    protected static final int CODE_SUCCESS = 0;

    private int errorcode;
    private String errmsg;

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "errorcode:" + errorcode + " errmsg:" + errmsg;
    }

    public boolean isSuccessful() {
        return CODE_SUCCESS == errorcode;
    }
}
