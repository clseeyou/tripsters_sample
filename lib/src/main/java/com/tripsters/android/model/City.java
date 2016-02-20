package com.tripsters.android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class City extends NetBean implements Parcelable {

    public static final Creator<City> CREATOR = new Creator<City>() {

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }

        @Override
        public City createFromParcel(Parcel source) {
            City city = new City();

            city.id = source.readInt();
            city.country_code = source.readString();
            city.city_name_cn = source.readString();
            city.city_name_en = source.readString();
            city.city_name_local = source.readString();
            city.city_lng = source.readString();
            city.city_lat = source.readString();
            city.city_hot = source.readInt();

            return city;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(country_code);
        dest.writeString(city_name_cn);
        dest.writeString(city_name_en);
        dest.writeString(city_name_local);
        dest.writeString(city_lng);
        dest.writeString(city_lat);
        dest.writeInt(city_hot);
    }

    public static final int OPENED = 1;
    public static final int OPERATED = 2;

    private int id;
    private String country_code;
    private String city_name_cn;
    private String city_name_en;
    private String city_name_local;
    private String city_lng;
    private String city_lat;
    private int city_hot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryCode() {
        return country_code;
    }

    public void setCountryCode(String countryCode) {
        this.country_code = countryCode;
    }

    public String getCityNameCn() {
        return city_name_cn;
    }

    public void setCityNameCn(String cityNameCn) {
        this.city_name_cn = cityNameCn;
    }

    public String getCityNameEn() {
        return city_name_en;
    }

    public void setCityNameEn(String cityNameEn) {
        this.city_name_en = cityNameEn;
    }

    public String getCityNameLocal() {
        return city_name_local;
    }

    public void setCityNameLocal(String cityNameLocal) {
        this.city_name_local = cityNameLocal;
    }

    public double getCityLng() {
        if (!TextUtils.isEmpty(city_lng)) {
            try {
                return Double.parseDouble(city_lng);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        return 0;
    }

    public void setCityLng(double cityLng) {
        this.city_lng = String.valueOf(cityLng);
    }

    public double getCityLat() {
        if (!TextUtils.isEmpty(city_lat)) {
            try {
                return Double.parseDouble(city_lat);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        return 0;
    }

    public void setCityLat(double cityLat) {
        this.city_lat = String.valueOf(cityLat);
    }

    public int getCityHot() {
        return city_hot;
    }

    public void setCityHot(int cityHot) {
        this.city_hot = cityHot;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof City) {
            City city = (City) o;

            return id == city.getId();
        }

        return false;
    }
}
