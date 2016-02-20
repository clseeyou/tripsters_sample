package com.tripsters.android.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer extends NetBean implements Parcelable {

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }

        @SuppressWarnings("unchecked")
        @Override
        public Answer createFromParcel(Parcel source) {
            Answer answer = new Answer();

            answer.answer_id = source.readString();
            answer.detail = source.readString();
            answer.Pics = source.readParcelable(PicInfo.class.getClassLoader());
            answer.pois = source.readArrayList(Poi.class.getClassLoader());
            answer.locals = source.readArrayList(Blog.class.getClassLoader());
            answer.created = source.readLong();
            answer.like_num = source.readInt();
            answer.top = source.readInt();
            answer.Question_Info = source.readParcelable(Question.class.getClassLoader());

            UserInfo userInfo = source.readParcelable(UserInfo.class.getClassLoader());
            answer.setUserInfo(userInfo);

            return answer;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(answer_id);
        dest.writeString(detail);
        dest.writeParcelable(Pics, flags);
        dest.writeList(pois);
        dest.writeList(locals);
        dest.writeLong(created);
        dest.writeInt(like_num);
        dest.writeInt(top);
        dest.writeParcelable(Question_Info, flags);

        dest.writeParcelable(getUserInfo(), flags);
    }

    private String answer_id;
    private String detail;
    private PicInfo Pics;
    private List<Poi> pois;
    private List<Blog> locals;
    private long created;
    private int like_num;
    private int top;
    private int answer_mode;

    private boolean fav;

    private String user_id;
    private String nickname;
    private String user_pic;
    private String country;
    private int identity;
    private String user_location;

    private Question Question_Info;
    private UserInfo userinfo; // Question_Info的userinfo

    public String getId() {
        return answer_id;
    }

    public void setId(String id) {
        this.answer_id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public PicInfo getPicInfo() {
        return Pics;
    }

    public void setPicInfo(PicInfo picInfo) {
        this.Pics = picInfo;
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

    public List<Blog> getBlogs() {
        if (locals == null) {
            return new ArrayList<Blog>();
        }

        return locals;
    }

    public void setBlogs(List<Blog> blogs) {
        this.locals = blogs;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getLikeNum() {
        return like_num;
    }

    public void setLikeNum(int likeNum) {
        this.like_num = likeNum;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getAnswerMode() {
        return answer_mode;
    }

    public void setAnswerMode(int answerMode) {
        this.answer_mode = answerMode;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user_id);
        userInfo.setNickname(nickname);
        userInfo.setAvatar(user_pic);
        try {
            userInfo.setIdentity(identity);
        } catch (NumberFormatException e) {
            userInfo.setIdentity(0);
        }
        userInfo.setCountry(country);
        userInfo.setLocation(user_location);

        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            this.user_id = "";
            this.nickname = "";
            this.user_pic = "";
            this.identity = 0;
            this.country = "";
            this.user_location = "";
        } else {
            this.user_id = userInfo.getId();
            this.nickname = userInfo.getNickname();
            this.user_pic = userInfo.getAvatar();
            this.identity = userInfo.getIdentity();
            this.country = userInfo.getCountry();
            this.user_location = userInfo.getLocation();
        }
    }

    public Question getQuestion() {
        if (Question_Info != null && userinfo != null) {
            Question_Info.setUserInfo(userinfo);
        }

        return Question_Info;
    }

    public void setQuestion(Question question) {
        this.Question_Info = question;
    }

    @Override
    public String toString() {
        return "Answer [answer_id=" + answer_id + ", detail=" + detail + ", pic_id=" + Pics.getId()
                + ", created=" + created + ", user_id=" + user_id + ", nickname=" + nickname
                + ", user_pic=" + user_pic + ", country=" + country + ", like_num=" + like_num
                + ", identity=" + identity + ", Question_Info=" + Question_Info + ", userinfo="
                + userinfo + "]";
    }
}
