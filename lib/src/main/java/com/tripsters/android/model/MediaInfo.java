package com.tripsters.android.model;

import java.util.UUID;

import android.os.Parcel;
import android.os.Parcelable;

public class MediaInfo implements Parcelable, Comparable<MediaInfo> {

    public static final Creator<MediaInfo> CREATOR = new Creator<MediaInfo>() {

        @Override
        public MediaInfo[] newArray(int size) {
            return new MediaInfo[size];
        }

        @Override
        public MediaInfo createFromParcel(Parcel source) {
            MediaInfo mediaInfo = new MediaInfo();

            mediaInfo.type = source.readInt();
            mediaInfo.id = source.readString();
            mediaInfo.mediaId = source.readString();
            mediaInfo.path = source.readString();
            mediaInfo.dateTaken = source.readLong();

            return mediaInfo;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(id);
        dest.writeString(mediaId);
        dest.writeString(path);
        dest.writeLong(dateTaken);
    }

    public static final int PIC = 0;
    public static final int VIDEO = 1;

    private int type;
    private String id;
    private String mediaId;
    private String path;
    private long dateTaken;

    public MediaInfo() {
        this.id = UUID.randomUUID().toString();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MediaInfo) {
            return id.equals(((MediaInfo) o).id);
        }

        return false;
    }

    @Override
    public int compareTo(MediaInfo another) {
        if (equals(another)) {
            return 0;
        }

        if (another.getDateTaken() > dateTaken) {
            return 1;
        } else if (another.getDateTaken() < dateTaken) {
            return -1;
        } else {
            return 0;
        }
    }
}
