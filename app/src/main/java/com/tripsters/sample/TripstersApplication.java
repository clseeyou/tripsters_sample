package com.tripsters.sample;

import android.app.Application;
import android.content.Context;

public class TripstersApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
    }
}
