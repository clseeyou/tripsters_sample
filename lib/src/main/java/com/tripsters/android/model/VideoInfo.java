package com.tripsters.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoInfo implements Parcelable {

    public static final Creator<VideoInfo> CREATOR = new Creator<VideoInfo>() {

        @Override
        public VideoInfo[] newArray(int size) {
            return new VideoInfo[size];
        }

        @Override
        public VideoInfo createFromParcel(Parcel source) {
            VideoInfo blog = new VideoInfo();

            blog.video_id = source.readString();
            blog.name = source.readString();
            blog.url = source.readString();
            blog.pics = source.readParcelable(PicInfo.class.getClassLoader());

            return blog;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(video_id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeParcelable(pics, flags);
    }

    private String video_id;
    private String name;
    private String url;
    // private String format;
    // private String describes;
    // private long size;
    // private long created;
    private PicInfo pics;

    public String getId() {
        return video_id;
    }

    public void setId(String id) {
        this.video_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PicInfo getPic() {
        return pics;
    }

    public void setPic(PicInfo pic) {
        this.pics = pic;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof VideoInfo) {
            return this.getId().equals(((VideoInfo) o).getId());
        }

        return false;
    }
}
