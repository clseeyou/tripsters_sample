package com.tripsters.android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class Poi implements Parcelable {

    public static final Creator<Poi> CREATOR = new Creator<Poi>() {

        @Override
        public Poi[] newArray(int size) {
            return new Poi[size];
        }

        @Override
        public Poi createFromParcel(Parcel source) {
            Poi blog = new Poi();

            blog.id = source.readString();
            blog.style = source.readString();
            blog.name_cn = source.readString();
            blog.name_en = source.readString();
            blog.name_local = source.readString();
            blog.describes = source.readString();
            blog.add_pic = source.readString();
            blog.address = source.readString();
            blog.ways = source.readString();
            blog.time = source.readString();
            blog.ticket = source.readString();
            blog.url = source.readString();
            blog.times = source.readString();
            blog.styles = source.readString();
            blog.telephone = source.readString();
            blog.tips = source.readString();
            blog.source = source.readString();
            blog.consume = source.readString();
            blog.grade = source.readString();
            blog.country = source.readString();
            blog.city = source.readString();
            blog.lat = source.readString();
            blog.lng = source.readString();

            return blog;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(style);
        dest.writeString(name_cn);
        dest.writeString(name_en);
        dest.writeString(name_local);
        dest.writeString(describes);
        dest.writeString(add_pic);
        dest.writeString(address);
        dest.writeString(ways);
        dest.writeString(time);
        dest.writeString(ticket);
        dest.writeString(url);
        dest.writeString(times);
        dest.writeString(styles);
        dest.writeString(telephone);
        dest.writeString(tips);
        dest.writeString(source);
        dest.writeString(consume);
        dest.writeString(grade);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(lat);
        dest.writeString(lng);
    }

    public enum Style {
        FOOD("food"), SCENIC("scenic"), SHOP("shop"), ACTIVITY("activity");

        final String value;

        Style(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Style getStyle(String value) {
            if (TextUtils.isEmpty(value)) {
                return null;
            }

            Style[] styles = Style.values();

            for (Style style : styles) {
                if (value.equals(style.value)) {
                    return style;
                }
            }

            return null;
        }
    }

    private String id;
    private String style;
    private String name_cn;
    private String name_en;
    private String name_local;
    private String describes;
    private String add_pic;
    private String address;
    private String ways;
    private String time;
    private String ticket;
    private String url;
    private String times;
    private String styles;
    private String telephone;
    private String tips;
    private String source;
    private String consume;
    private String grade;
    private String country;
    private String city;
    private String lat;
    private String lng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Style getStyle() {
        return Style.getStyle(style);
    }

    public void setStyle(Style style) {
        this.style = style.getValue();
    }

    public String getName() {
        if (TextUtils.isEmpty(name_cn)) {
            return name_en;
        }

        return name_cn;
    }

    public String getNameCn() {
        return name_cn;
    }

    public void setNameCn(String name_cn) {
        this.name_cn = name_cn;
    }

    public String getNameEn() {
        return name_en;
    }

    public void setNameEn(String name_en) {
        this.name_en = name_en;
    }

    public String getNameLocal() {
        return name_local;
    }

    public void setNameLocal(String name_local) {
        this.name_local = name_local;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getAddPic() {
        return add_pic;
    }

    public void setAddPic(String add_pic) {
        this.add_pic = add_pic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWays() {
        return ways;
    }

    public void setWays(String ways) {
        this.ways = ways;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getStyles() {
        return styles;
    }

    public void setStyles(String styles) {
        this.styles = styles;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getConsume() {
        return consume;
    }

    public void setConsume(String consume) {
        this.consume = consume;
    }

    public float getGrade() {
        if (!TextUtils.isEmpty(grade)) {
            try {
                return Float.parseFloat(grade);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        return 0;
    }

    public void setGrade(float grade) {
        this.grade = String.valueOf(grade);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Poi) {
            return this.getId().equals(((Poi) o).getId());
        }

        return false;
    }
}
