package com.tripsters.android.model;

public enum Identity {
    NONE(-1), OTHER(0), NORMAL(1), COUNTRY_DAREN(2), NORMAL_DAREN(4);

    final int value;

    Identity(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public String getStringValue() {
        return String.valueOf(this.value);
    }

    public static Identity getFromValue(int value) {
        for (Identity identity : values()) {
            if (identity.value == value) {
                return identity;
            }
        }

        return OTHER;
    }

    public static Identity getFromStringValue(String stringValue) {
        for (Identity identity : values()) {
            if (identity.getStringValue().equals(stringValue)) {
                return identity;
            }
        }

        return OTHER;
    }

    public static Identity getFromUser(UserInfo userInfo) {
        if (userInfo == null) {
            return NONE;
        }

        for (Identity identity : values()) {
            if (identity.value == userInfo.getIdentity()) {
                return identity;
            }
        }

        return OTHER;
    }
}
