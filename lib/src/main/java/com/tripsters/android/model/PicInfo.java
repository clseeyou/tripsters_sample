package com.tripsters.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PicInfo implements Parcelable {
    public static enum PicType {
        ORI, SMALL, MIDDLE, BIG;

        public Pic getPic(PicInfo picInfo) {
            if (picInfo == null) {
                return null;
            }

            switch (this) {
                case ORI:
                    return new Pic(picInfo.getPic(), picInfo.getHeight(), picInfo.getWidth(), this);
                case SMALL:
                    return new Pic(picInfo.getPicSmall(), picInfo.getSmallHeight(),
                            picInfo.getSmallWidth(), this);
                case MIDDLE:
                    return new Pic(picInfo.getPicMiddle(), picInfo.getMiddleHeight(),
                            picInfo.getMiddleWidth(), this);
                case BIG:
                    return new Pic(picInfo.getPicBig(), picInfo.getBigHeight(),
                            picInfo.getBigWidth(), this);
                default:
                    return null;
            }
        }
    }

    public static class Pic {
        private String pic;
        private int height;
        private int width;
        private PicType type;

        Pic(String pic, int height, int width, PicType type) {
            this.pic = pic;
            this.height = height;
            this.width = width;
            this.type = type;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public PicType getType() {
            return type;
        }

        public void setType(PicType type) {
            this.type = type;
        }
    }

    public static final Creator<PicInfo> CREATOR = new Creator<PicInfo>() {

        @Override
        public PicInfo[] newArray(int size) {
            return new PicInfo[size];
        }

        @Override
        public PicInfo createFromParcel(Parcel source) {
            PicInfo picInfo = new PicInfo();

            picInfo.id = source.readString();
            picInfo.pic = source.readString();
            picInfo.height = source.readInt();
            picInfo.width = source.readInt();
            picInfo.pic_small = source.readString();
            picInfo.small_height = source.readInt();
            picInfo.small_width = source.readInt();
            picInfo.pic_middle = source.readString();
            picInfo.middle_height = source.readInt();
            picInfo.middle_width = source.readInt();
            picInfo.pic_big = source.readString();
            picInfo.big_height = source.readInt();
            picInfo.big_width = source.readInt();

            return picInfo;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(pic);
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeString(pic_small);
        dest.writeInt(small_height);
        dest.writeInt(small_width);
        dest.writeString(pic_middle);
        dest.writeInt(middle_height);
        dest.writeInt(middle_width);
        dest.writeString(pic_big);
        dest.writeInt(big_height);
        dest.writeInt(big_width);
    }

    private String id;

    private String pic;
    private int height;
    private int width;

    private String pic_small;
    private int small_height;
    private int small_width;

    private String pic_middle;
    private int middle_height;
    private int middle_width;

    private String pic_big;
    private int big_height;
    private int big_width;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getPicSmall() {
        return pic_small;
    }

    public void setPicSmall(String picMmall) {
        this.pic_small = picMmall;
    }

    public int getSmallHeight() {
        return small_height;
    }

    public void setSmallHeight(int smallHeight) {
        this.small_height = smallHeight;
    }

    public int getSmallWidth() {
        return small_width;
    }

    public void setSmallWidth(int smallWidth) {
        this.small_width = smallWidth;
    }

    public String getPicMiddle() {
        return pic_middle;
    }

    public void setPicMiddle(String picMiddle) {
        this.pic_middle = picMiddle;
    }

    public int getMiddleHeight() {
        return middle_height;
    }

    public void setMiddleHeight(int middleHeight) {
        this.middle_height = middleHeight;
    }

    public int getMiddleWidth() {
        return middle_width;
    }

    public void setMiddleWidth(int middleWidth) {
        this.middle_width = middleWidth;
    }

    public String getPicBig() {
        return pic_big;
    }

    public void setPicBig(String picBig) {
        this.pic_big = picBig;
    }

    public int getBigHeight() {
        return big_height;
    }

    public void setBigHeight(int bigHeight) {
        this.big_height = bigHeight;
    }

    public int getBigWidth() {
        return big_width;
    }

    public void setBigWidth(int bigWidth) {
        this.big_width = bigWidth;
    }
}
