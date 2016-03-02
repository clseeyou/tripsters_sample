package com.tripsters.sample.composer;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.tripsters.android.model.Blog;
import com.tripsters.android.model.City;
import com.tripsters.android.model.Country;
import com.tripsters.android.model.MediaInfo;
import com.tripsters.android.model.Poi;
import com.tripsters.android.model.Question;
import com.tripsters.android.model.Tag;
import com.tripsters.sample.BaseActivity;
import com.tripsters.sample.R;
import com.tripsters.sample.TripstersApplication;
import com.tripsters.sample.composer.center.ComposerCenter;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.util.IntentUtils;
import com.tripsters.sample.util.LoginUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BaseComposer implements Parcelable, Comparable<BaseComposer> {

    public static final Creator<BaseComposer> CREATOR = new Creator<BaseComposer>() {

        @Override
        public BaseComposer[] newArray(int size) {
            return new BaseComposer[size];
        }

        @Override
        public BaseComposer createFromParcel(Parcel source) {
            BaseComposer composer = ComposerFactory.create(source.readInt());

            composer.read(source);

            return composer;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type.getValue());

        write(dest, flags);
    }

    public static enum ComposerType {
        SENT_QUESTION(0), SEND_ANSWER(1), SEND_REANSWER(2);

        final int value;

        ComposerType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static ComposerType getType(int value) {
            for (ComposerType type : values()) {
                if (type.value == value) {
                    return type;
                }
            }

            throw new IllegalArgumentException("value is illegal : value = " + value);
        }
    }

    public static final int STATE_EDITED = 0;
    public static final int STATE_SENDING = 1;
    public static final int STATE_FAILED = 2;
    public static final int STATE_RESENDING = 3;
    public static final int STATE_SUCCESS = 4;

    protected ComposerType type;

    protected String id;
    protected String uid;

    protected String content;
    protected Country country;
    protected List<City> cities;
    protected List<Tag> tags;
    protected List<Blog> blogs;
    protected List<Poi> pois;
    protected List<MediaInfo> mediaInfos;

    protected long created;
    protected int state = STATE_EDITED;

    BaseComposer() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        if (id == null) {
            return "";
        }

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        if (uid == null) {
            return "";
        }

        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ComposerType getType() {
        return type;
    }

    public void setType(ComposerType type) {
        this.type = type;
    }

    public String getContent() {
        if (content == null) {
            return "";
        }

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<City> getCities() {
        if (cities == null) {
            return new ArrayList<City>();
        }

        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Tag> getTags() {
        if (tags == null) {
            return new ArrayList<Tag>();
        }

        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Blog> getBlogs() {
        if (blogs == null) {
            return new ArrayList<Blog>();
        }

        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
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

    public List<MediaInfo> getMediaInfos() {
        if (mediaInfos == null) {
            return new ArrayList<MediaInfo>();
        }

        return mediaInfos;
    }

    public void setMediaInfos(List<MediaInfo> picPaths) {
        this.mediaInfos = picPaths;
    }

    public String getPicPath() {
        return getMediaInfos().isEmpty() ? null : getMediaInfos().get(0).getPath();
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setSendingState() {
        if (state == STATE_EDITED) {
            state = STATE_SENDING;
        } else if (state == STATE_FAILED) {
            state = STATE_RESENDING;
        }
    }

    public boolean isSendingState() {
        return state == STATE_SENDING || state == STATE_RESENDING;
    }

    public void clearSendingState() {
        if (isSendingState()) {
            state = STATE_FAILED;
        }
    }

    public abstract void init(Context context, Intent intent);

    public abstract boolean showAddCities();

    public abstract String getTitle(Context context);

    public abstract String getHint(Context context);

    public abstract int getMaxLenth();

    public abstract boolean isToolbarVisiable();

    public abstract boolean isTagVisiable();

    public abstract boolean isBlogVisiable();

    public abstract boolean isPoiVisiable();

    public abstract boolean isCityEnabled();

    public abstract boolean isLocationVisiable();

    public String getSendContent(Context context) {
        return content;
    }

    public void back(BaseActivity activity) {
        saveDraft();
        activity.finish();
    }

    public void publish(BaseActivity activity) {
        send(activity);
    }

    public abstract void send(Context context);

    public void addCities(BaseActivity activity) {
    }

    public void sendWithCities(BaseActivity activity) {
    }

    public Question getQuestion() {
        return null;
    }

    public int getConsumePoints() {
        return 0;
    }

    public void setLat(String lat) {
    }

    public void setLng(String lng) {
    }

    public void setAddress(String address) {
    }

    protected boolean checkValid(Context context) {
        if (!LoginUser.isLogin(context)) {
            ErrorToast.getInstance().showErrorMessage(R.string.pre_login_str);
            IntentUtils.login(context);

            return false;
        }

        return true;
    }

    protected void saveDraft() {
        ComposerCenter.getInstance().saveDraft(TripstersApplication.mContext, this);
    }

    protected void removeDraft() {
        ComposerCenter.getInstance().removeDraft(TripstersApplication.mContext, this);
    }

    @SuppressWarnings("unchecked")
    protected void read(Parcel source) {
        id = source.readString();
        uid = source.readString();
        content = source.readString();
        country = source.readParcelable(Country.class.getClassLoader());
        cities = source.readArrayList(City.class.getClassLoader());
        tags = source.readArrayList(Tag.class.getClassLoader());
        blogs = source.readArrayList(Blog.class.getClassLoader());
        pois = source.readArrayList(Poi.class.getClassLoader());
        mediaInfos = source.readArrayList(MediaInfo.class.getClassLoader());
        created = source.readLong();
        state = source.readInt();
    }

    protected void write(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(uid);
        dest.writeString(content);
        dest.writeParcelable(country, flags);
        dest.writeList(cities);
        dest.writeList(tags);
        dest.writeList(blogs);
        dest.writeList(pois);
        dest.writeList(mediaInfos);
        dest.writeLong(created);
        dest.writeInt(state);
    }

    protected final String getPicIdsString(List<MediaInfo> mediaInfos) {
        if (mediaInfos == null || mediaInfos.isEmpty()) {
            return "";
        }

        List<MediaInfo> picMediaInfos = new ArrayList<MediaInfo>();

        for (MediaInfo mediaInfo : mediaInfos) {
            if (mediaInfo.getType() == MediaInfo.PIC) {
                picMediaInfos.add(mediaInfo);
            }
        }

        if (picMediaInfos == null || picMediaInfos.isEmpty()) {
            return "";
        }

        StringBuilder stringBuilder =
                new StringBuilder(String.valueOf(picMediaInfos.get(0).getMediaId()));

        for (int i = 1; i < mediaInfos.size(); i++) {
            stringBuilder.append(",").append(picMediaInfos.get(i).getMediaId());
        }

        return stringBuilder.toString();
    }

    protected final String getVideoIdsString(List<MediaInfo> mediaInfos) {
        if (mediaInfos == null || mediaInfos.isEmpty()) {
            return "";
        }

        List<MediaInfo> videoMediaInfos = new ArrayList<MediaInfo>();

        for (MediaInfo mediaInfo : mediaInfos) {
            if (mediaInfo.getType() == MediaInfo.VIDEO) {
                videoMediaInfos.add(mediaInfo);
            }
        }

        if (videoMediaInfos == null || videoMediaInfos.isEmpty()) {
            return "";
        }

        StringBuilder stringBuilder =
                new StringBuilder(String.valueOf(videoMediaInfos.get(0).getMediaId()));

        for (int i = 1; i < mediaInfos.size(); i++) {
            stringBuilder.append(",").append(videoMediaInfos.get(i).getMediaId());
        }

        return stringBuilder.toString();
    }

    protected final String getCitiesString(List<City> cities) {
        if (cities == null || cities.isEmpty()) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder(String.valueOf(cities.get(0).getId()));

        for (int i = 1; i < cities.size(); i++) {
            stringBuilder.append(",").append(cities.get(i).getId());
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BaseComposer) {
            return this.getId().equals(((BaseComposer) o).getId());
        }

        return false;
    }

    @Override
    public int compareTo(BaseComposer another) {
        if (equals(another)) {
            return 0;
        }

        if (another.getCreated() > created) {
            return 1;
        } else if (another.getCreated() < created) {
            return -1;
        } else {
            return 0;
        }
    }
}
