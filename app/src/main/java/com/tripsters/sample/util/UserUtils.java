package com.tripsters.sample.util;

import android.content.Context;
import android.text.TextUtils;

import com.tripsters.android.model.City;
import com.tripsters.android.model.Gender;
import com.tripsters.android.model.Identity;
import com.tripsters.android.model.Question;
import com.tripsters.android.model.Tag;
import com.tripsters.android.model.UserInfo;
import com.tripsters.sample.R;

import java.util.List;

public class UserUtils {

    public static boolean isDaren(UserInfo userInfo) {
        Identity identity = Identity.getFromUser(userInfo);

        return identity == Identity.COUNTRY_DAREN || identity == Identity.NORMAL_DAREN;
    }

    public static String getVerifyInfo(Context context, UserInfo userInfo, boolean title) {
        Identity identity = Identity.getFromUser(userInfo);

        if (identity == Identity.NONE) {
            return "";
        }

        if (identity == Identity.COUNTRY_DAREN) {
            if (title) {
                return context.getString(R.string.profile_info_verify_daren_title)
                        + context.getString(R.string.profile_info_verify_daren,
                        userInfo.getCountry());
            } else {
                return context.getString(R.string.profile_info_verify_daren, userInfo.getCountry());
            }
        } else if (identity == Identity.NORMAL_DAREN) {
            if (title) {
                return context.getString(R.string.profile_info_verify_daren_title)
                        + context.getString(R.string.profile_info_verify_daren_noraml);
            } else {
                return context.getString(R.string.profile_info_verify_daren_noraml);
            }
        } else {
            return context.getString(R.string.profile_info_verify_normal);
        }
    }

    public static String getVerifyAndLocationInfo(Context context, UserInfo userInfo) {
        if (userInfo == null) {
            return "";
        }

        StringBuilder sbVerifyInfo =
                new StringBuilder(UserUtils.getVerifyInfo(context, userInfo, false));

        if (!TextUtils.isEmpty(userInfo.getLocation())) {
            sbVerifyInfo.append(" · ").append(userInfo.getLocation());
        }

        return sbVerifyInfo.toString();
    }

    public static String getTagsString(List<Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < tags.size(); i++) {
            builder.append("#").append(tags.get(i).getCategoryCn()).append("#");
        }

        return builder.toString();
    }

    public static String getCitiesString(List<City> cities) {
        if (cities == null || cities.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < cities.size(); i++) {
            builder.append("#").append(cities.get(i).getCityNameCn()).append("#");
        }

        return builder.toString();
    }

    public static boolean isMyself(String uid) {
        return !TextUtils.isEmpty(uid) && uid.equals(LoginUser.getId());
    }

    public static String getTags(Question question) {
        return new StringBuilder().append(getCitiesString(question.getCities()))
                .append(getTagsString(question.getTags())).toString();
    }

    public static String getGender(Context context, Gender gender) {
        if (gender == Gender.MALE) {
            return context.getString(R.string.profile_info_gender_male);
        } else if (gender == Gender.FEMALE) {
            return context.getString(R.string.profile_info_gender_female);
        } else {
            return context.getString(R.string.profile_info_gender_female);
        }
    }
}
