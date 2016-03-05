package com.tripsters.sample.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.tripsters.android.model.Country;
import com.tripsters.android.model.Gender;
import com.tripsters.android.model.UserInfo;
import com.tripsters.sample.TripstersApplication;

public class LoginUser {

    private static final String USERINFO_SP = "userinfo_sp";

    private static final String COUNTRY_SP = "country_sp";

    private static final String PUSH_SP = "push_sp";

    private static final String KEY_BLACK_TAG = "black_tag";
    private static final String KEY_CHANNEL_ID = "channel_id";

    static class UserKey {
        public static final String KEY_ID = "id";
        public static final String KEY_GENDER = "gender";
        public static final String KEY_NICKNAME = "nickname";
        public static final String KEY_AVATAR = "avatar";
        public static final String KEY_IDENTITY = "identity";
        public static final String KEY_FOLLOWERS_COUNT = "followers_count";
        public static final String KEY_FRIENDS_COUNT = "friends_count";
        public static final String KEY_FANS = "fans";
        public static final String KEY_LOCATION = "location";
        public static final String KEY_DESCRIPTION = "description";
        public static final String KEY_COUNTRY = "country";
        public static final String KEY_PHONE = "phone";
        public static final String KEY_IDLE = "idle";
        public static final String KEY_SHOW_IPADDR = "show_ipaddr";
        public static final String KEY_POINTS = "points";
        public static final String KEY_GOLD = "gold";
        public static final String KEY_GROWTH = "growth";
        public static final String KEY_NATION = "nation";
        public static final String KEY_OCCUPATION = "occupation";
        public static final String KEY_TRIP = "trip";
        public static final String KEY_FROM = "user_from";
    }

    static class CountryKey {
        public static final String KEY_ID = "id";
        public static final String KEY_COUNTRY_NAME_CN = "country_name_cn";
        public static final String KEY_COUNTRY_NAME_EN = "country_name_en";
        public static final String KEY_COUNTRY_NAME_LOCAL = "country_name_local";
        public static final String KEY_COUNTRY_CODE = "country_code";
        public static final String KEY_PIC = "pic";
        public static final String KEY_HOT = "hot";
    }

    private static UserInfo mLoginUser;
    private static Country mChangeCountry;

    private LoginUser() {

    }

    synchronized public static boolean isLogin(Context context) {
        if (mLoginUser == null) {
            mLoginUser = getUser(context);
        }

        return mLoginUser != null;
    }

    synchronized public static boolean isLogin() {
        return isLogin(TripstersApplication.mContext);
    }

    synchronized public static UserInfo getUser(Context context) {
        if (mLoginUser == null) {
            mLoginUser = getUserInfoFromSp(context);
        }

        return mLoginUser;
    }

    synchronized public static UserInfo getUser() {
        return getUser(TripstersApplication.mContext);
    }

    public static String getId(Context context) {
        UserInfo userInfo = getUser(context);

        if (userInfo != null) {
            return userInfo.getId();
        }

        return "";
    }

    public static String getId() {
        return getId(TripstersApplication.mContext);
    }

    synchronized public static void setUser(Context context, final UserInfo userInfo) {
        mLoginUser = userInfo;

        saveUserInfoToSp(context, userInfo);
    }

    synchronized public static void setUser(final UserInfo userInfo) {
        setUser(TripstersApplication.mContext, userInfo);
    }

    synchronized public static void clearUser(Context context) {
        mLoginUser = null;

        SharedPreferences versionPrefs = getUserSp(context);
        versionPrefs.edit().clear().commit();
    }

    synchronized public static void clearUserLogin() {
        clearUser(TripstersApplication.mContext);
    }

    synchronized public static void setChannelId(Context context, String channelId) {
        saveChannelIdToSp(context, channelId);
    }

    synchronized public static void setChannelId(String channelId) {
        setChannelId(TripstersApplication.mContext, channelId);
    }

    synchronized public static String getChannelId(Context context) {
        return getChannelIdFromSp(context);
    }

    synchronized public static String getChannelId() {
        return getChannelIdFromSp(TripstersApplication.mContext);
    }

    synchronized public static void clearChannelId(Context context) {
        setChannelId(context, "");
    }

    synchronized public static void clearChannelId() {
        setChannelId(TripstersApplication.mContext, "");
    }

    synchronized public static boolean isBind(Context context) {
        return TextUtils.isEmpty(getChannelIdFromSp(context));
    }

    synchronized public static boolean isBind() {
        return isBind(TripstersApplication.mContext);
    }

    synchronized public static Country getCountry(Context context) {
        if (mChangeCountry == null) {
            mChangeCountry = getChangeCountryFromSp(context);
        }

        return mChangeCountry;
    }

    synchronized public static Country getCountry() {
        return getCountry(TripstersApplication.mContext);
    }

    synchronized public static void setCountry(Context context, final Country country) {
        mChangeCountry = country;

        saveChangeCountryToSp(context, country);
    }

    synchronized public static void setCountry(final Country country) {
        setCountry(TripstersApplication.mContext, country);
    }

    private static void saveUserInfoToSp(Context context, UserInfo userInfo) {
        SharedPreferences versionPrefs = getUserSp(context);
        SharedPreferences.Editor editorVersion = versionPrefs.edit();

        if (userInfo == null) {
            editorVersion.putString(UserKey.KEY_ID, "");
            editorVersion.putString(UserKey.KEY_GENDER, "");
            editorVersion.putString(UserKey.KEY_NICKNAME, "");
            editorVersion.putString(UserKey.KEY_AVATAR, "");
            editorVersion.putInt(UserKey.KEY_IDENTITY, 0);
            editorVersion.putInt(UserKey.KEY_FOLLOWERS_COUNT, 0);
            editorVersion.putInt(UserKey.KEY_FRIENDS_COUNT, 0);
            editorVersion.putInt(UserKey.KEY_FANS, 0);
            editorVersion.putString(UserKey.KEY_LOCATION, "");
            editorVersion.putString(UserKey.KEY_DESCRIPTION, "");
            editorVersion.putString(UserKey.KEY_COUNTRY, "");
            editorVersion.putString(UserKey.KEY_PHONE, "");
            editorVersion.putInt(UserKey.KEY_IDLE, 0);
            editorVersion.putString(UserKey.KEY_SHOW_IPADDR, "");
            editorVersion.putInt(UserKey.KEY_POINTS, 0);
            editorVersion.putInt(UserKey.KEY_GOLD, 0);
            editorVersion.putInt(UserKey.KEY_GROWTH, 0);
            editorVersion.putString(UserKey.KEY_NATION, "");
            editorVersion.putString(UserKey.KEY_OCCUPATION, "");
            editorVersion.putString(UserKey.KEY_TRIP, "");
            editorVersion.putInt(KEY_BLACK_TAG, 0);

            editorVersion.putString(UserKey.KEY_FROM, "");
        } else {
            editorVersion.putString(UserKey.KEY_ID, userInfo.getId());
            editorVersion.putString(UserKey.KEY_GENDER, userInfo.getGender().getValue());
            editorVersion.putString(UserKey.KEY_NICKNAME, userInfo.getNickname());
            editorVersion.putString(UserKey.KEY_AVATAR, userInfo.getAvatar());
            editorVersion.putInt(UserKey.KEY_IDENTITY, userInfo.getIdentity());
            editorVersion.putInt(UserKey.KEY_FOLLOWERS_COUNT, userInfo.getFollowersCount());
            editorVersion.putInt(UserKey.KEY_FRIENDS_COUNT, userInfo.getFriendsCount());
            editorVersion.putInt(UserKey.KEY_FANS, userInfo.getFans());
            editorVersion.putString(UserKey.KEY_LOCATION, userInfo.getLocation());
            editorVersion.putString(UserKey.KEY_DESCRIPTION, userInfo.getDescription());
            editorVersion.putString(UserKey.KEY_COUNTRY, userInfo.getCountry());
            editorVersion.putString(UserKey.KEY_PHONE, userInfo.getPhone());
            editorVersion.putInt(UserKey.KEY_IDLE, userInfo.getIdle());
            editorVersion.putString(UserKey.KEY_SHOW_IPADDR, userInfo.getShowIpaddr());
            editorVersion.putInt(UserKey.KEY_POINTS, userInfo.getPoints());
            editorVersion.putInt(UserKey.KEY_GOLD, userInfo.getGold());
            editorVersion.putInt(UserKey.KEY_GROWTH, userInfo.getGrowth());
            editorVersion.putString(UserKey.KEY_NATION, userInfo.getNation());
            editorVersion.putString(UserKey.KEY_OCCUPATION, userInfo.getOccupation());
            editorVersion.putString(UserKey.KEY_TRIP, userInfo.getTrip());
            editorVersion.putInt(KEY_BLACK_TAG, userInfo.getBlackTag());

            editorVersion.putString(UserKey.KEY_FROM, userInfo.getFrom());
        }

        editorVersion.commit();
    }

    private static UserInfo getUserInfoFromSp(Context context) {
        SharedPreferences versionPrefs = getUserSp(context);

        UserInfo userInfo = new UserInfo();
        userInfo.setId(versionPrefs.getString(UserKey.KEY_ID, ""));
        userInfo.setGender(Gender.getFromValue(versionPrefs.getString(UserKey.KEY_GENDER, "")));
        userInfo.setNickname(versionPrefs.getString(UserKey.KEY_NICKNAME, ""));
        userInfo.setAvatar(versionPrefs.getString(UserKey.KEY_AVATAR, ""));
        userInfo.setIdentity(versionPrefs.getInt(UserKey.KEY_IDENTITY, 0));
        userInfo.setFollowersCount(versionPrefs.getInt(UserKey.KEY_FOLLOWERS_COUNT, 0));
        userInfo.setFriendsCount(versionPrefs.getInt(UserKey.KEY_FRIENDS_COUNT, 0));
        userInfo.setFans(versionPrefs.getInt(UserKey.KEY_FANS, 0));
        userInfo.setLocation(versionPrefs.getString(UserKey.KEY_LOCATION, ""));
        userInfo.setDescription(versionPrefs.getString(UserKey.KEY_DESCRIPTION, ""));
        userInfo.setCountry(versionPrefs.getString(UserKey.KEY_COUNTRY, ""));
        userInfo.setPhone(versionPrefs.getString(UserKey.KEY_PHONE, ""));
        userInfo.setIdle(versionPrefs.getInt(UserKey.KEY_IDLE, 0));
        userInfo.setShowIpaddr(versionPrefs.getString(UserKey.KEY_SHOW_IPADDR, ""));
        userInfo.setPoints(versionPrefs.getInt(UserKey.KEY_POINTS, 0));
        userInfo.setGold(versionPrefs.getInt(UserKey.KEY_GOLD, 0));
        userInfo.setGrowth(versionPrefs.getInt(UserKey.KEY_GROWTH, 0));
        userInfo.setNation(versionPrefs.getString(UserKey.KEY_NATION, ""));
        userInfo.setOccupation(versionPrefs.getString(UserKey.KEY_OCCUPATION, ""));
        userInfo.setTrip(versionPrefs.getString(UserKey.KEY_TRIP, ""));
        userInfo.setBlackTag(versionPrefs.getInt(KEY_BLACK_TAG, 0));

        userInfo.setFrom(versionPrefs.getString(UserKey.KEY_FROM, ""));

        if (TextUtils.isEmpty(userInfo.getId())) {
            return null;
        }

        return userInfo;
    }

    private static void saveChangeCountryToSp(Context context, Country country) {
        SharedPreferences sp = getCountrySp(context);
        SharedPreferences.Editor editor = sp.edit();

        if (country == null) {
            editor.putInt(CountryKey.KEY_ID, 0);
            editor.putString(CountryKey.KEY_COUNTRY_NAME_CN, "");
            editor.putString(CountryKey.KEY_COUNTRY_NAME_EN, "");
            editor.putString(CountryKey.KEY_COUNTRY_NAME_LOCAL, "");
            editor.putString(CountryKey.KEY_COUNTRY_CODE, "");
            editor.putString(CountryKey.KEY_PIC, "");
            editor.putInt(CountryKey.KEY_HOT, 0);
        } else {
            editor.putInt(CountryKey.KEY_ID, country.getId());
            editor.putString(CountryKey.KEY_COUNTRY_NAME_CN, country.getCountryNameCn());
            editor.putString(CountryKey.KEY_COUNTRY_NAME_EN, country.getCountryNameEn());
            editor.putString(CountryKey.KEY_COUNTRY_NAME_LOCAL, country.getCountryNameLocal());
            editor.putString(CountryKey.KEY_COUNTRY_CODE, country.getCountryCode());
            editor.putString(CountryKey.KEY_PIC, country.getPic());
            editor.putInt(CountryKey.KEY_HOT, country.getHot());
        }

        editor.commit();
    }

    private static Country getChangeCountryFromSp(Context context) {
        Country country = new Country();

        SharedPreferences sp = getCountrySp(context);
        country.setId(sp.getInt(CountryKey.KEY_ID, 0));
        country.setCountryNameCn(sp.getString(CountryKey.KEY_COUNTRY_NAME_CN, ""));
        country.setCountryNameEn(sp.getString(CountryKey.KEY_COUNTRY_NAME_EN, ""));
        country.setCountryNameLocal(sp.getString(CountryKey.KEY_COUNTRY_NAME_LOCAL, ""));
        country.setCountryCode(sp.getString(CountryKey.KEY_COUNTRY_CODE, ""));
        country.setPic(sp.getString(CountryKey.KEY_PIC, ""));
        country.setHot(sp.getInt(CountryKey.KEY_HOT, 0));

        if (country.getId() == 0 && TextUtils.isEmpty(country.getCountryCode())) {
            return null;
        }

        return country;
    }

    private static void saveChannelIdToSp(Context context, String channelId) {
        getPushSp(context).edit().putString(KEY_CHANNEL_ID, channelId).commit();
    }

    private static String getChannelIdFromSp(Context context) {
        return getPushSp(context).getString(KEY_CHANNEL_ID, "");
    }

    private static SharedPreferences getUserSp(Context context) {
        if (context == null) {
            return TripstersApplication.mContext.getSharedPreferences(USERINFO_SP, 0);
        } else {
            return context.getSharedPreferences(USERINFO_SP, 0);
        }
    }

    private static SharedPreferences getCountrySp(Context context) {
        if (context == null) {
            return TripstersApplication.mContext.getSharedPreferences(COUNTRY_SP, 0);
        } else {
            return context.getSharedPreferences(COUNTRY_SP, 0);
        }
    }

    private static SharedPreferences getPushSp(Context context) {
        if (context == null) {
            return TripstersApplication.mContext.getSharedPreferences(PUSH_SP, 0);
        } else {
            return context.getSharedPreferences(PUSH_SP, 0);
        }
    }
}
