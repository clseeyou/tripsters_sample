package com.tripsters.android.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;

import com.tripsters.android.util.LogUtils;

public class RichVideoInfo extends RichInfo {

    public static final Creator<RichVideoInfo> CREATOR = new Creator<RichVideoInfo>() {

        @Override
        public RichVideoInfo[] newArray(int size) {
            return new RichVideoInfo[size];
        }

        @Override
        public RichVideoInfo createFromParcel(Parcel source) {
            RichVideoInfo richVideoInfo = new RichVideoInfo();

            richVideoInfo.id = source.readString();
            richVideoInfo.mediaInfo = source.readParcelable(MediaInfo.class.getClassLoader());
            richVideoInfo.videoInfo = source.readParcelable(VideoInfo.class.getClassLoader());

            return richVideoInfo;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(mediaInfo, flags);
        dest.writeParcelable(videoInfo, flags);
    }

    private String id;
    private MediaInfo mediaInfo;
    private VideoInfo videoInfo;

    public RichVideoInfo() {
        setType(Type.VIDEO);
    }

    public RichVideoInfo(MediaInfo mediaInfo) {
        this();
        this.mediaInfo = mediaInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MediaInfo getMediaInfo() {
        return mediaInfo;
    }

    public void setMediaInfo(MediaInfo mediaInfo) {
        this.mediaInfo = mediaInfo;
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
    }

    @Override
    public JSONObject getJsonObject(boolean local) {
        try {
            JSONObject jobj = new JSONObject();

            jobj.put(VALUE_TYPE, Type.VIDEO.value);
            if (local) {
                JSONObject jobjMedia = new JSONObject();
                jobjMedia.put("id", mediaInfo.getId());

                jobj.put("mediaInfo", jobjMedia);
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
        mediaInfo = source.readParcelable(MediaInfo.class.getClassLoader());
        videoInfo = source.readParcelable(VideoInfo.class.getClassLoader());
    }

    @Override
    protected void write(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(mediaInfo, flags);
        dest.writeParcelable(videoInfo, flags);
    }
}
