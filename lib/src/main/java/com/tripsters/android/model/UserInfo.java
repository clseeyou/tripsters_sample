package com.tripsters.android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.tripsters.android.util.LogUtils;

public class UserInfo extends NetBean implements Parcelable, Cloneable {

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }

        @Override
        public UserInfo createFromParcel(Parcel source) {
            UserInfo userInfo = new UserInfo();

            userInfo.id = source.readString();
            userInfo.gender = source.readString();
            userInfo.nickname = source.readString();
            userInfo.avatar = source.readString();
            userInfo.identity = source.readInt();
            userInfo.followers_count = source.readString();
            userInfo.friends_count = source.readString();
            userInfo.is_fans = source.readInt();
            userInfo.location = source.readString();
            userInfo.description = source.readString();
            userInfo.country = source.readString();
            userInfo.phone = source.readString();
            userInfo.idle = source.readInt();
            userInfo.show_ipaddr = source.readString();
            userInfo.points = source.readInt();
            userInfo.gold = source.readInt();
            userInfo.growth = source.readInt();
            userInfo.nation = source.readString();
            userInfo.occupation = source.readString();
            userInfo.trip = source.readString();
            userInfo.black_tag = source.readInt();
            userInfo.from = source.readString();

            return userInfo;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(gender);
        dest.writeString(nickname);
        dest.writeString(avatar);
        dest.writeInt(identity);
        dest.writeString(followers_count);
        dest.writeString(friends_count);
        dest.writeInt(is_fans);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeString(country);
        dest.writeString(phone);
        dest.writeInt(idle);
        dest.writeString(show_ipaddr);
        dest.writeInt(points);
        dest.writeInt(gold);
        dest.writeInt(growth);
        dest.writeString(nation);
        dest.writeString(occupation);
        dest.writeString(trip);
        dest.writeInt(black_tag);
        dest.writeString(from);
    }

    private String id;
    private String gender;
    private String nickname;
    private String avatar;
    private int identity;
    private String followers_count;
    private String friends_count;
    private int is_fans;
    private String location;
    private String description;
    private String country;// 这是达人的注册的国家，一般人没有这个字段
    private String phone;
    private int idle;
    private String show_ipaddr;
    private int points;// 积分
    private int gold;// 金币
    private int growth;// 成长值
    private String nation;
    private String occupation;
    private String trip;
    private int black_tag; // 代表自己是否是系统黑名单用户，不需要存数据库

    private String from;

    public String getId() {
        if (id == null) {
            return "";
        }

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Gender getGender() {
        return Gender.getFromValue(gender);
    }

    public void setGender(Gender gender) {
        this.gender = gender.getValue();
    }

    public String getNickname() {
        if (nickname == null) {
            return "";
        }

        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        if (avatar == null) {
            return "";
        }

        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public int getFollowersCount() {
        if (!TextUtils.isEmpty(followers_count)) {
            try {
                return Integer.parseInt(followers_count);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        return 0;
    }

    public void setFollowersCount(int followersCount) {
        this.followers_count = String.valueOf(followersCount);
    }

    public int getFriendsCount() {
        if (!TextUtils.isEmpty(friends_count)) {
            try {
                return Integer.parseInt(friends_count);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        return 0;
    }

    public void setFriendsCount(int friendsCount) {
        this.friends_count = String.valueOf(friendsCount);
    }

    public int getFans() {
        return is_fans;
    }

    public void setFans(int fans) {
        this.is_fans = fans;
    }

    public String getLocation() {
        if (location == null) {
            return "";
        }

        return location.trim();
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        if (description == null) {
            return "";
        }

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        if (country == null) {
            return "";
        }

        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        if (phone == null) {
            return "";
        }

        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIdle() {
        return idle;
    }

    public void setIdle(int idle) {
        this.idle = idle;
    }

    public String getShowIpaddr() {
        return show_ipaddr;
    }

    public void setShowIpaddr(String showIpaddr) {
        this.show_ipaddr = showIpaddr;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public String getNation() {
        if (nation == null) {
            return "";
        }

        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getOccupation() {
        if (occupation == null) {
            return "";
        }

        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTrip() {
        if (trip == null) {
            return "";
        }

        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public int getBlackTag() {
        return black_tag;
    }

    public void setBlackTag(int blackTag) {
        this.black_tag = blackTag;
    }

    public String getFrom() {
        if (from == null) {
            return "";
        }

        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public UserInfo copy() {
        try {
            return (UserInfo) this.clone();
        } catch (CloneNotSupportedException e) {
            LogUtils.loge(e);

            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserInfo) {
            UserInfo userInfo = (UserInfo) o;

            return userInfo.getId() != null && userInfo.getId().equals(id);
        }

        return false;
    }
}
