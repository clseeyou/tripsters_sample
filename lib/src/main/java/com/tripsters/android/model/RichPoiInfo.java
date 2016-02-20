package com.tripsters.android.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;

import com.tripsters.android.util.LogUtils;

public class RichPoiInfo extends RichInfo {

    public static final Creator<RichPoiInfo> CREATOR = new Creator<RichPoiInfo>() {

        @Override
        public RichPoiInfo[] newArray(int size) {
            return new RichPoiInfo[size];
        }

        @Override
        public RichPoiInfo createFromParcel(Parcel source) {
            RichPoiInfo richPoiInfo = new RichPoiInfo();

            richPoiInfo.id = source.readString();
            richPoiInfo.poi = source.readParcelable(Poi.class.getClassLoader());

            return richPoiInfo;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(poi, flags);
    }

    private String id;
    private Poi poi;

    public RichPoiInfo() {
        setType(Type.POI);
    }

    public RichPoiInfo(Poi poi) {
        this();
        this.poi = poi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Poi getPoi() {
        return poi;
    }

    public void setPoi(Poi poi) {
        this.poi = poi;
    }

    @Override
    public JSONObject getJsonObject(boolean local) {
        try {
            JSONObject jobj = new JSONObject();

            jobj.put(VALUE_TYPE, Type.POI.value);
            if (local) {
                JSONObject jobjPoi = new JSONObject();
                jobjPoi.put("id", poi.getId());

                jobj.put("poi", jobjPoi);
            } else {
                jobj.put("id", id);
            }

            return jobj;
        } catch (JSONException e) {
            LogUtils.loge(e);
        }

        return null;
    }

    @Override
    protected void read(Parcel source) {
        id = source.readString();
        poi = source.readParcelable(Poi.class.getClassLoader());
    }

    @Override
    protected void write(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(poi, flags);
    }
}
