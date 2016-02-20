package com.tripsters.android.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;

import com.tripsters.android.util.LogUtils;

public class RichTextInfo extends RichInfo {

    public static final Creator<RichTextInfo> CREATOR = new Creator<RichTextInfo>() {

        @Override
        public RichTextInfo[] newArray(int size) {
            return new RichTextInfo[size];
        }

        @Override
        public RichTextInfo createFromParcel(Parcel source) {
            RichTextInfo richTextInfo = new RichTextInfo();

            richTextInfo.value = source.readString();

            return richTextInfo;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
    }

    private String value;

    public RichTextInfo() {
        setType(Type.TEXT);
    }

    public String getValue() {
        if (value == null) {
            return "";
        }

        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public JSONObject getJsonObject(boolean local) {
        try {
            JSONObject jobj = new JSONObject();

            jobj.put(VALUE_TYPE, Type.TEXT.value);
            jobj.put("value", value);

            return jobj;
        } catch (JSONException e) {
            LogUtils.loge(e);
        }

        return null;
    }

    @Override
    protected void read(Parcel source) {
        value = source.readString();
    }

    @Override
    protected void write(Parcel dest, int flags) {
        dest.writeString(value);
    }
}
