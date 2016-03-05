package com.tripsters.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PushMessage implements Parcelable {

    public static final Creator<PushMessage> CREATOR = new Creator<PushMessage>() {

        @Override
        public PushMessage[] newArray(int size) {
            return new PushMessage[size];
        }

        @Override
        public PushMessage createFromParcel(Parcel source) {
            PushMessage message = new PushMessage();

            message.title = source.readString();
            message.description = source.readString();
            message.type = source.readInt();
            message.time = source.readLong();

            return message;
        }
    };

    public enum Type {
        RECEIVED_ANSWER(2), UNKNOWN(0);

        final int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        static Type getValueFromValue(int value) {
            Type[] types = Type.values();

            for (Type type : types) {
                if (type.value == value) {
                    return type;
                }
            }

            return UNKNOWN;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(type);
        dest.writeLong(time);
    }

    private String title;
    private String description;

    private int type;// 自定义类型type，0为系统通知，1为需要我回答的，2为回答我的
    private long time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return Type.getValueFromValue(type);
    }

    public void setType(Type type) {
        this.type = type.getValue();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
