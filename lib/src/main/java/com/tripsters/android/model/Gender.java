package com.tripsters.android.model;

//import com.tripsters.android.R;
//
//import android.content.Context;

public enum Gender {
    MALE("m"), FEMALE("f"), UNKNOWN("");

    final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

//    public String getText(Context context) {
//        if (this == MALE) {
//            return context.getString(R.string.profile_info_gender_male);
//        } else if (this == FEMALE) {
//            return context.getString(R.string.profile_info_gender_female);
//        } else {
//            return context.getString(R.string.profile_info_gender_female);
//        }
//    }

    public static Gender getFromValue(String value) {
        for (Gender gender : values()) {
            if (gender.value.equals(value)) {
                return gender;
            }
        }

        return UNKNOWN;
    }

//    public static Gender getGenderFromText(Context context, String text) {
//        for (Gender gender : values()) {
//            if (gender.getText(context).equals(text)) {
//                return gender;
//            }
//        }
//
//        return UNKNOWN;
//    }
}
