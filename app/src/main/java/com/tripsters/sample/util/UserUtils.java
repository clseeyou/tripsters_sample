package com.tripsters.sample.util;

import android.content.Context;
import android.text.TextUtils;

import com.tripsters.android.model.Blog;
import com.tripsters.android.model.City;
import com.tripsters.android.model.Country;
import com.tripsters.android.model.Gender;
import com.tripsters.android.model.Identity;
import com.tripsters.android.model.MediaInfo;
import com.tripsters.android.model.PicInfo;
import com.tripsters.android.model.Poi;
import com.tripsters.android.model.Question;
import com.tripsters.android.model.Tag;
import com.tripsters.android.model.UserInfo;
import com.tripsters.android.model.VideoInfo;
import com.tripsters.sample.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserUtils {

    public static boolean isNormal(UserInfo userInfo) {
        return Identity.getFromUser(userInfo) == Identity.NORMAL;
    }

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

    public static String getTagsStringFromTags(List<Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(tags.get(0).getCategoryCn());

        for (int i = 1; i < tags.size(); i++) {
            builder.append(" ").append(tags.get(i).getCategoryCn());
        }

        return builder.toString();
    }

    public static String getTagsStringFromCities(List<City> cities) {
        if (cities == null || cities.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(cities.get(0).getCityNameCn());

        for (int i = 1; i < cities.size(); i++) {
            builder.append(" ").append(cities.get(i).getCityNameCn());
        }

        return builder.toString();
    }

    public static String getFilteredCities(List<City> filteredCities) {
        if (filteredCities == null || filteredCities.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder(String.valueOf(filteredCities.get(0).getId()));

        for (int i = 1; i < filteredCities.size(); i++) {
            builder.append(",").append(filteredCities.get(i).getId());
        }

        return builder.toString();
    }

    public static List<String> getFilteredCities(String filteredCities) {
        if (TextUtils.isEmpty(filteredCities)) {
            return new ArrayList<String>();
        }

        return Arrays.asList(filteredCities.split(","));
    }

    public static String getFilteredTags(List<Tag> filteredTags) {
        if (filteredTags == null || filteredTags.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder(String.valueOf(filteredTags.get(0).getId()));

        for (int i = 1; i < filteredTags.size(); i++) {
            builder.append(",").append(filteredTags.get(i).getId());
        }

        return builder.toString();
    }

    public static List<String> getFilteredTags(String filteredTags) {
        if (TextUtils.isEmpty(filteredTags)) {
            return new ArrayList<String>();
        }

        return Arrays.asList(filteredTags.split(","));
    }

    public static String getPicInfos(List<PicInfo> picInfos) {
        if (picInfos == null || picInfos.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder(String.valueOf(picInfos.get(0).getId()));

        for (int i = 1; i < picInfos.size(); i++) {
            builder.append(",").append(picInfos.get(i).getId());
        }

        return builder.toString();
    }

    public static List<String> getPicInfos(String picInfos) {
        if (TextUtils.isEmpty(picInfos)) {
            return new ArrayList<String>();
        }

        return Arrays.asList(picInfos.split(","));
    }

    public static String getPois(List<Poi> pois) {
        if (pois == null || pois.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder(String.valueOf(pois.get(0).getId()));

        for (int i = 1; i < pois.size(); i++) {
            builder.append(",").append(pois.get(i).getId());
        }

        return builder.toString();
    }

    public static List<String> getPois(String pois) {
        if (TextUtils.isEmpty(pois)) {
            return new ArrayList<String>();
        }

        return Arrays.asList(pois.split(","));
    }

    public static String getVideoInfos(List<VideoInfo> videoInfos) {
        if (videoInfos == null || videoInfos.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder(String.valueOf(videoInfos.get(0).getId()));

        for (int i = 1; i < videoInfos.size(); i++) {
            builder.append(",").append(videoInfos.get(i).getId());
        }

        return builder.toString();
    }

    public static List<String> getVideoInfos(String videoInfos) {
        if (TextUtils.isEmpty(videoInfos)) {
            return new ArrayList<String>();
        }

        return Arrays.asList(videoInfos.split(","));
    }

    public static String getMediaInfos(List<MediaInfo> mediaInfos) {
        if (mediaInfos == null || mediaInfos.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder(String.valueOf(mediaInfos.get(0).getId()));

        for (int i = 1; i < mediaInfos.size(); i++) {
            builder.append(",").append(mediaInfos.get(i).getId());
        }

        return builder.toString();
    }

    public static List<String> getMediaInfos(String mediaInfos) {
        if (TextUtils.isEmpty(mediaInfos)) {
            return new ArrayList<String>();
        }

        return Arrays.asList(mediaInfos.split(","));
    }

    public static String getBlogAddress(Blog blog) {
        if (blog.getCountry() == null || blog.getCities().isEmpty()) {
            return "";
        }

        return blog.getCountry().getCountryNameCn() + blog.getCities().get(0).getCityNameCn();
    }

    public static String getTagsStringFromCountry(Country country) {
        if (country == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(country.getCountryNameCn());

        return builder.toString();
    }

    public static boolean isMyself(String uid) {
        return !TextUtils.isEmpty(uid) && uid.equals(LoginUser.getId());
    }

    public static String getTags(Question question) {
        return new StringBuilder().append(getCitiesString(question.getCities()))
                .append(getTagsString(question.getTags())).toString();
    }

    // public static String getContent(Question question) {
    // return new StringBuilder().append(getCitiesString(question.getCities()))
    // .append(getTagsString(question.getTags())).append(question.getTitle()).toString();
    // }

    public static String getTripsStringFromCities(List<City> cities) {
        if (cities == null || cities.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(cities.get(0).getCityNameCn());

        for (int i = 1; i < cities.size(); i++) {
            builder.append("、").append(cities.get(i).getCityNameCn());
        }

        return builder.toString();
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
