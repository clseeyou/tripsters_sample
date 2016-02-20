package com.tripsters.android.util;

public class DebugConfig {

    public static final boolean DEBUG = false;

    public static boolean sPushDebug;
    public static boolean sEMDebug;
    public static boolean sNetDebug;
    public static boolean sLogDebug;

    static {
        sPushDebug = DEBUG;
        sEMDebug = DEBUG;
        sNetDebug = DEBUG;
        sLogDebug = DEBUG;
    }

}
