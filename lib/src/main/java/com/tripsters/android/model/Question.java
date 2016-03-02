package com.tripsters.android.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class Question implements Parcelable {

    public static final Creator<Question> CREATOR = new Creator<Question>() {

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }

        @SuppressWarnings("unchecked")
        @Override
        public Question createFromParcel(Parcel source) {
            Question question = new Question();

            question.question_id = source.readString();
            question.title = source.readString();
            question.detail = source.readString();
            question.pic = source.readParcelable(PicInfo.class.getClassLoader());
            question.created = source.readLong();
            question.answer_num = source.readInt();
            question.save_num = source.readInt();
            question.country = source.readParcelable(Country.class.getClassLoader());
            question.city = source.readArrayList(City.class.getClassLoader());
            question.cates = source.readArrayList(Tag.class.getClassLoader());
            question.ipaddr = source.readString();
            question.address = source.readString();

            UserInfo userInfo = source.readParcelable(UserInfo.class.getClassLoader());
            question.setUserInfo(userInfo);

            return question;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question_id);
        dest.writeString(title);
        dest.writeString(detail);
        dest.writeParcelable(pic, flags);
        dest.writeLong(created);
        dest.writeInt(answer_num);
        dest.writeInt(save_num);
        dest.writeParcelable(country, flags);
        dest.writeList(city);
        dest.writeList(cates);
        dest.writeString(ipaddr);
        dest.writeString(address);

        dest.writeParcelable(getUserInfo(), flags);
    }

    private String question_id;
    private String title;
    private String detail;
    private PicInfo pic;
    private long created;
    private int answer_num;// "回答的数量"
    private int save_num;// "收藏的数量"
    private Country country;
    private List<City> city;
    private List<Tag> cates;
    private String ipaddr;
    private String address;

    private boolean save;

    private Answer answer;

    private UserInfo userinfo;

    public String getId() {
        return question_id;
    }

    public void setId(String id) {
        this.question_id = id;
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

    public PicInfo getPicInfo() {
        return pic;
    }

    public void setPicInfo(PicInfo picInfo) {
        this.pic = picInfo;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getAnswerNum() {
        return answer_num;
    }

    public void setAnswerNum(int answerNum) {
        this.answer_num = answerNum;
    }

    public int getSaveNum() {
        return save_num;
    }

    public void setSaveNum(int saveNum) {
        this.save_num = saveNum;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<City> getCities() {
        if (city == null) {
            return new ArrayList<City>();
        }

        return city;
    }

    public void setCities(List<City> cities) {
        this.city = cities;
    }

    public List<Tag> getTags() {
        if (cates == null) {
            return new ArrayList<Tag>();
        }

        return cates;
    }

    public void setTags(List<Tag> tags) {
        this.cates = tags;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public UserInfo getUserInfo() {
        return userinfo;
    }

    public void setUserInfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }
}
