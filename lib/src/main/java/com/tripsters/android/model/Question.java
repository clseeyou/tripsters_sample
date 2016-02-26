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
            question.Pics = source.readParcelable(PicInfo.class.getClassLoader());
            question.created = source.readLong();
            question.answer_num = source.readInt();
            question.save_num = source.readInt();
            question.country = source.readParcelable(Country.class.getClassLoader());
            question.Citys = source.readArrayList(City.class.getClassLoader());
            question.cates = source.readArrayList(Tag.class.getClassLoader());
            question.ipaddr = source.readString();
            question.address = source.readString();
            // question.Answer = source.readParcelable(Answer.class.getClassLoader());
            // question.user_id = source.readString();
            // question.user_pic = source.readString();
            // question.nickname = source.readString();
            // question.identity = source.readString();

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
        dest.writeParcelable(Pics, flags);
        dest.writeLong(created);
        dest.writeInt(answer_num);
        dest.writeInt(save_num);
        dest.writeParcelable(country, flags);
        dest.writeList(Citys);
        dest.writeList(cates);
        dest.writeString(ipaddr);
        dest.writeString(address);
        // dest.writeParcelable(Answer, flags);
        // dest.writeString(user_id);
        // dest.writeString(nickname);
        // dest.writeString(user_pic);
        // dest.writeString(identity);

        dest.writeParcelable(getUserInfo(), flags);
    }

    private String question_id;
    private String title;
    private String detail;
    private PicInfo Pics;
    private long created;
    private int answer_num;// "回答的数量"
    private int save_num;// "收藏的数量"
    private Country country;
    private List<City> Citys;
    private List<Tag> cates;
    private String ipaddr;
    private String address;

    private boolean save;

    private Answer Answer;

    private String user_id;
    private String nickname;// 昵称
    private String user_pic;
    private String identity;

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
        return Pics;
    }

    public void setPicInfo(PicInfo picInfo) {
        this.Pics = picInfo;
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
        if (Citys == null) {
            return new ArrayList<City>();
        }

        return Citys;
    }

    public void setCities(List<City> cities) {
        this.Citys = cities;
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
        return Answer;
    }

    public void setAnswer(Answer answer) {
        this.Answer = answer;
    }

    public UserInfo getUserInfo() {
        if (userinfo == null) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user_id);
            userInfo.setNickname(nickname);
            userInfo.setAvatar(user_pic);
            if (!TextUtils.isEmpty(identity)) {
                try {
                    userInfo.setIdentity(Integer.parseInt(identity));
                } catch (NumberFormatException e) {
                    userInfo.setIdentity(0);
                }
            }

            return userInfo;
        }

        return userinfo;
    }

    public void setUserInfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    @Override
    public String toString() {
        return "Question [question_id=" + question_id + ", title=" + title + ", detail=" + detail
                + ", created=" + created + ", user_id=" + user_id + ", pic=" + user_pic
                + ", identity=" + identity + ", answer_num=" + answer_num + ", save_num="
                + save_num + ", nickname=" + nickname + "]";
    }
}
