package com.tripsters.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Country extends NetBean implements Parcelable {

    public static final Creator<Country> CREATOR = new Creator<Country>() {

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }

        @Override
        public Country createFromParcel(Parcel source) {
            Country country = new Country();

            country.id = source.readInt();
            country.countryNameCn = source.readString();
            country.countryNameEn = source.readString();
            country.countryNameLocal = source.readString();
            country.countryCode = source.readString();
            country.pic = source.readString();
            country.hot = source.readInt();

            return country;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(countryNameCn);
        dest.writeString(countryNameEn);
        dest.writeString(countryNameLocal);
        dest.writeString(countryCode);
        dest.writeString(pic);
        dest.writeInt(hot);
    }

    /**
     * hot = 1  开通
     */
    public static final int OPENED = 1;
    /**
     * hot = 2  试运营
     */
    public static final int OPERATED = 2;

    private int id;
    @SerializedName("country_name_cn")
    private String countryNameCn;
    @SerializedName("country_name_en")
    private String countryNameEn;
    @SerializedName("country_name_local")
    private String countryNameLocal;
    @SerializedName("country_code")
    private String countryCode;
    private String pic;
    private int hot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryNameCn() {
        return countryNameCn;
    }

    public void setCountryNameCn(String countryNameCn) {
        this.countryNameCn = countryNameCn;
    }

    public String getCountryNameEn() {
        return countryNameEn;
    }

    public void setCountryNameEn(String countryNameEn) {
        this.countryNameEn = countryNameEn;
    }

    public String getCountryNameLocal() {
        return countryNameLocal;
    }

    public void setCountryNameLocal(String countryNameLocal) {
        this.countryNameLocal = countryNameLocal;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Country) {
            Country country = (Country) o;

            return id == country.getId();
        }

        return false;
    }
}
