package com.tripsters.android.model;

public enum Gender {
    MALE("m"), FEMALE("f"), UNKNOWN("n");

    final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Gender getFromValue(String value) {
        for (Gender gender : values()) {
            if (gender.value.equals(value)) {
                return gender;
            }
        }

        return UNKNOWN;
    }
}
