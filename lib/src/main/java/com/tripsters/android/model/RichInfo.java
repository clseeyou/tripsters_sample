package com.tripsters.android.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.tripsters.android.util.LogUtils;

public abstract class RichInfo implements Parcelable {

    public static final Creator<RichInfo> CREATOR = new Creator<RichInfo>() {

        @Override
        public RichInfo[] newArray(int size) {
            return new RichInfo[size];
        }

        @Override
        public RichInfo createFromParcel(Parcel source) {
            RichInfo richInfo = RichInfo.create(source.readString());

            richInfo.read(source);

            return richInfo;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);

        write(dest, flags);
    }

    protected final static String VALUE_TYPE = "type";

    public enum Type {
        PIC("pic"), TEXT("text"), POI("poi"), VIDEO("video");

        final String value;

        Type(String vaule) {
            this.value = vaule;
        }

        static Type getType(String value) {
            for (Type type : values()) {
                if (value.equals(type.value)) {
                    return type;
                }
            }

            return null;
        }
    }

    private String type;

    public Type getType() {
        return Type.getType(type);
    }

    public void setType(Type type) {
        this.type = type.value;
    }

    abstract public JSONObject getJsonObject(boolean local);

    abstract protected void read(Parcel source);

    abstract protected void write(Parcel dest, int flags);

    public static List<RichInfo> createList(String jarrString) {
        List<RichInfo> richInfos = new ArrayList<RichInfo>();

        if (!TextUtils.isEmpty(jarrString)) {
            try {
                JSONArray jarr = new JSONArray(jarrString);

                for (int i = 0; i < jarr.length(); i++) {
                    RichInfo richInfo = createOne(jarr.optString(i));

                    if (richInfo != null) {
                        richInfos.add(richInfo);
                    }
                }
            } catch (JSONException e) {
                LogUtils.loge(e);
            }
        }

        return richInfos;
    }

    private static RichInfo createOne(String jobjString) {
        try {
            JSONObject jobj = new JSONObject(jobjString);
            Type type = Type.getType(jobj.optString(VALUE_TYPE));

            switch (type) {
                case PIC:
                    return ModelFactory.getInstance().create(jobj.toString(), RichMediaInfo.class);
                case TEXT:
                    return ModelFactory.getInstance().create(jobj.toString(), RichTextInfo.class);
                case POI:
                    return ModelFactory.getInstance().create(jobj.toString(), RichPoiInfo.class);
                case VIDEO:
                    return ModelFactory.getInstance().create(jobj.toString(), RichVideoInfo.class);
                default:
                    return null;
            }
        } catch (JSONException e) {
            LogUtils.loge(e);
        }

        return null;
    }

    public static RichInfo create(String typeValue) {
        Type type = Type.getType(typeValue);

        switch (type) {
            case PIC:
                return new RichMediaInfo();
            case TEXT:
                return new RichTextInfo();
            case POI:
                return new RichPoiInfo();
            case VIDEO:
                return new RichVideoInfo();
            default:
                return null;
        }
    }

    public static String createText(List<RichInfo> richInfos, boolean local) {
        JSONArray jarr = new JSONArray();

        for (RichInfo richInfo : richInfos) {
            JSONObject jobj = richInfo.getJsonObject(local);

            if (jobj != null) {
                jarr.put(jobj);
            }
        }

        return jarr.toString();
    }

    public static List<RichInfo> replace(List<RichInfo> richInfos, List<MediaInfo> mediaInfos, List<Poi> pois) {
        if (richInfos != null && mediaInfos != null) {
            for (RichInfo richInfo : richInfos) {
                if (richInfo.getType() == Type.PIC) {
                    int index = mediaInfos.indexOf(((RichMediaInfo) richInfo).getMediaInfo());

                    if (index != -1) {
                        ((RichMediaInfo) richInfo).setId((mediaInfos.get(index).getMediaId()));
                        ((RichMediaInfo) richInfo).setMediaInfo(mediaInfos.get(index));
                    }
                } else if (richInfo.getType() == Type.POI) {
                    int index = pois.indexOf(((RichPoiInfo) richInfo).getPoi());

                    if (index != -1) {
                        ((RichPoiInfo) richInfo).setId((pois.get(index).getId()));
                        ((RichPoiInfo) richInfo).setPoi(pois.get(index));
                    }
                } else if (richInfo.getType() == Type.VIDEO) {
                    int index = mediaInfos.indexOf(((RichVideoInfo) richInfo).getMediaInfo());

                    if (index != -1) {
                        ((RichVideoInfo) richInfo).setId((mediaInfos.get(index).getMediaId()));
                        ((RichVideoInfo) richInfo).setMediaInfo(mediaInfos.get(index));
                    }
                }
            }
        }

        return richInfos;
    }
}
