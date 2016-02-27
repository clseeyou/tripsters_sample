package com.tripsters.android.util;

import android.content.Context;

import java.util.Locale;

public class Utils {

    public static String getAppId(Context context) {
        if (DebugConfig.DEBUG) {
            return "jieda";
        } else {
            return "";
        }
    }

    public static String getAppLang(Context context) {
        return Locale.getDefault().toString();
    }
}
