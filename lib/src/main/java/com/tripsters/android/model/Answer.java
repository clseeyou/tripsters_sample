package com.tripsters.android.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {

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
            answer.pic = source.readParcelable(PicInfo.class.getClassLoader());
            answer.pois = source.readArrayList(Poi.class.getClassLoader());
            answer.locals = source.readArrayList(Blog.class.getClassLoader());
            answer.created = source.readLong();
            answer.like_num = source.readInt();
            answer.top = source.readInt();
            answer.question = source.readParcelable(Question.class.getClassLoader());

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
        dest.writeParcelable(pic, flags);
        dest.writeList(pois);
        dest.writeList(locals);
        dest.writeLong(created);
        dest.writeInt(like_num);
        dest.writeInt(top);
        dest.writeParcelable(question, flags);

        dest.writeParcelable(getUserInfo(), flags);
    }

    private String answer_id;
    private String detail;
    private PicInfo pic;
    private List<Poi> pois;
    private List<Blog> locals;
    private long created;
    private int like_num;
    private int top;
    private int answer_mode;

    private boolean fav;

    private Question question;
    private UserInfo userinfo;

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
        return pic;
    }

    public void setPicInfo(PicInfo picInfo) {
        this.pic = picInfo;
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
        return userinfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userinfo = userInfo;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
