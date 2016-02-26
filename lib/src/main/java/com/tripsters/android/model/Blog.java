package com.tripsters.android.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.tripsters.android.model.RichInfo.Type;

public class Blog implements Parcelable {

    public static final Creator<Blog> CREATOR = new Creator<Blog>() {

        @Override
        public Blog[] newArray(int size) {
            return new Blog[size];
        }

        @SuppressWarnings("unchecked")
        @Override
        public Blog createFromParcel(Parcel source) {
            Blog blog = new Blog();

            blog.local_id = source.readString();
            blog.type = source.readInt();
            blog.name = source.readString();
            blog.title = source.readString();
            blog.detail = source.readString();
            blog.pics = source.readArrayList(PicInfo.class.getClassLoader());
            blog.pois = source.readArrayList(Poi.class.getClassLoader());
            blog.video = source.readArrayList(VideoInfo.class.getClassLoader());
            blog.pic_text = source.readString();
            blog.created = source.readLong();
            blog.save_num = source.readInt();
            blog.favorite_num = source.readInt();
            blog.read_num = source.readInt();
            blog.comment_num = source.readInt();
            blog.lat = source.readString();
            blog.lng = source.readString();
            blog.address = source.readString();
            blog.country_struct = source.readParcelable(Country.class.getClassLoader());
            blog.citys = source.readArrayList(City.class.getClassLoader());
            blog.categorys = source.readArrayList(Tag.class.getClassLoader());
            blog.url = source.readString();

            UserInfo userInfo = source.readParcelable(UserInfo.class.getClassLoader());
            blog.setUserInfo(userInfo);

            return blog;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(local_id);
        dest.writeInt(type);
        dest.writeString(name);
        dest.writeString(title);
        dest.writeString(detail);
        dest.writeList(pics);
        dest.writeList(pois);
        dest.writeList(video);
        dest.writeString(pic_text);
        dest.writeLong(created);
        dest.writeInt(save_num);
        dest.writeInt(favorite_num);
        dest.writeInt(read_num);
        dest.writeInt(comment_num);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(address);
        dest.writeParcelable(country_struct, flags);
        dest.writeList(citys);
        dest.writeList(categorys);
        dest.writeString(url);

        dest.writeParcelable(getUserInfo(), flags);
    }

    private String local_id;
    private int type;
    private String name;
    private String title;
    private String detail;
    private List<PicInfo> pics;
    private List<Poi> pois;
    private List<VideoInfo> video;
    private String pic_text;
    private long created;
    private int save_num; // "收藏的数量"
    private int favorite_num;// "赞的数量"
    private int read_num;// "阅读的数量"
    private int comment_num;// "回答的数量"
    private String lat;
    private String lng;
    private String address;
    private Country country_struct;
    private List<City> citys;
    private List<Tag> categorys;
    private String url;

    private boolean save;
    private boolean fav;

    private UserInfo userinfo;

    public String getId() {
        return local_id;
    }

    public void setId(String id) {
        this.local_id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<PicInfo> getPicInfos() {
        if (pics == null) {
            return new ArrayList<PicInfo>();
        }

        return pics;
    }

    public void setPicInfos(List<PicInfo> picInfos) {
        this.pics = picInfos;
    }

    public List<VideoInfo> getVideoInfos() {
        if (video == null) {
            return new ArrayList<VideoInfo>();
        }

        return video;
    }

    public void setVideoInfos(List<VideoInfo> videoInfos) {
        this.video = videoInfos;
    }

    public List<Poi> getPois() {
        if (pois == null) {
            return new ArrayList<Poi>();
        }

        return pois;
    }

    public void setPois(List<Poi> pois) {
        this.pois = pois;
    }

    public String getPicText() {
        return pic_text;
    }

    public void setPicText(String picText) {
        this.pic_text = picText;
    }

    public List<RichInfo> getRichInfos() {
        List<RichInfo> richInfos = RichInfo.createList(pic_text);

        for (RichInfo richInfo : richInfos) {
            if (richInfo.getType() == Type.PIC) {
                RichMediaInfo richMediaInfo = (RichMediaInfo) richInfo;

                for (PicInfo picInfo : getPicInfos()) {
                    if (richMediaInfo.getId().equals(picInfo.getId())) {
                        richMediaInfo.setPicInfo(picInfo);
                    }
                }
            } else if (richInfo.getType() == Type.POI) {
                RichPoiInfo richPoiInfo = (RichPoiInfo) richInfo;

                for (Poi poi : getPois()) {
                    if (richPoiInfo.getId().equals(poi.getId())) {
                        richPoiInfo.setPoi(poi);
                    }
                }
            } else if (richInfo.getType() == Type.VIDEO) {
                RichVideoInfo richVideoInfo = (RichVideoInfo) richInfo;

                for (VideoInfo videoInfo : getVideoInfos()) {
                    if (richVideoInfo.getId().equals(videoInfo.getId())) {
                        richVideoInfo.setVideoInfo(videoInfo);
                    }
                }
            }
        }

        return richInfos;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getSaveNum() {
        return save_num;
    }

    public void setSaveNum(int saveNum) {
        this.save_num = saveNum;
    }

    public int getFavoriteNum() {
        return favorite_num;
    }

    public void setFavoriteNum(int favoriteNum) {
        this.favorite_num = favoriteNum;
    }

    public int getReadNum() {
        return read_num;
    }

    public void setReadNum(int readNum) {
        this.read_num = readNum;
    }

    public int getCommentNum() {
        return comment_num;
    }

    public void setCommentNum(int commentNum) {
        this.comment_num = commentNum;
    }

    public double getLat() {
        if (!TextUtils.isEmpty(lat)) {
            try {
                return Double.parseDouble(lat);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        return 0;
    }

    public void setLat(double lat) {
        this.lat = String.valueOf(lat);
    }

    public double getLng() {
        if (!TextUtils.isEmpty(lng)) {
            try {
                return Double.parseDouble(lng);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        return 0;
    }

    public void setLng(double lng) {
        this.lng = String.valueOf(lng);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Country getCountry() {
        return country_struct;
    }

    public void setCountry(Country country) {
        this.country_struct = country;
    }

    public List<City> getCities() {
        if (citys == null) {
            return new ArrayList<City>();
        }

        return citys;
    }

    public void setCities(List<City> cities) {
        this.citys = cities;
    }

    public List<Tag> getTags() {
        if (categorys == null) {
            return new ArrayList<Tag>();
        }

        return categorys;
    }

    public void setTags(List<Tag> tags) {
        this.categorys = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public UserInfo getUserInfo() {
        return userinfo;
    }

    public void setUserInfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public PicInfo getFirstPic() {
        List<RichInfo> richInfos = getRichInfos();

        if (richInfos.isEmpty()) {
            return null;
        }

        if (richInfos.get(0).getType() == Type.PIC) {
            return getPicInfos().get(0);
        }

        if (richInfos.get(0).getType() == Type.VIDEO) {
            return getVideoInfos().get(0).getPic();
        }

        return null;
    }

    public Type getFirstType() {
        List<RichInfo> richInfos = getRichInfos();

        if (richInfos.isEmpty()) {
            return null;
        }

        return richInfos.get(0).getType();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Blog) {
            return this.getId().equals(((Blog) o).getId());
        }

        return false;
    }
}
