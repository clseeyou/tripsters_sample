package com.tripsters.android.util;

public class DebugConfig {

    public static final boolean DEBUG = true;

    public static boolean sNetDebug;
    public static boolean sLogDebug;

    static {
        sNetDebug = DEBUG;
        sLogDebug = DEBUG;
    }

}
