package com.tripsters.android.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * 分类
 * 
 * @author seeyou
 */
public class Tag extends NetBean implements Parcelable {

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }

        @Override
        public Tag createFromParcel(Parcel source) {
            Tag tag = new Tag();

            tag.id = source.readInt();
            tag.category_cn = source.readString();
            tag.category_en = source.readString();

            return tag;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(category_cn);
        dest.writeString(category_en);
    }

    private int id;
    private String category_cn;
    private String category_en;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryCn() {
        return category_cn;
    }

    public void setCategoryCn(String categoryCn) {
        this.category_cn = categoryCn;
    }

    public String getCategoryEn() {
        return category_en;
    }

    public void setCategoryEn(String categoryEn) {
        this.category_en = categoryEn;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tag) {
            Tag tag = (Tag) o;

            return id == tag.getId();
        }

        return false;
    }
}
