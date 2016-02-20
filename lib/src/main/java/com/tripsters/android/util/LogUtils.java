package com.tripsters.android.util;

import android.util.Log;

public class LogUtils {

    private final static String TAG = "tripsters";

    public static void logd(String msg) {
        logd(TAG, msg);
    }

    public static void logi(String msg) {
        logi(TAG, msg);
    }

    public static void logw(String msg) {
        logw(TAG, msg);
    }

    public static void loge(String msg) {
        loge(TAG, msg);
    }

    public static void loge(Exception e) {
        loge(TAG, e);
    }

    public static void logd(String tag, String msg) {
        if (DebugConfig.sLogDebug) {
            Log.d(tag, msg);
        }
    }

    public static void logi(String tag, String msg) {
        if (DebugConfig.sLogDebug) {
            Log.i(tag, msg);
        }
    }

    public static void logw(String tag, String msg) {
        if (DebugConfig.sLogDebug) {
            Log.w(tag, msg);
        }
    }

    public static void loge(String tag, String msg) {
        if (DebugConfig.sLogDebug) {
            Log.e(tag, msg);
        }
    }

    public static void loge(String tag, Exception e) {
        if (DebugConfig.sLogDebug) {
            Log.e(tag, "Exception : ", e);
        }
    }
}
