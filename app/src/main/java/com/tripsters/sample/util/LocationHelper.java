package com.tripsters.sample.util;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class LocationHelper {

    /*
     * user-defined interface
     */
    public interface LocationHelperListener {
        /**
         * do something before the location is start
         */
        void onLocationStart();

        /**
         * do something after the location is end
         */
        void onLocationEnd(Location location);
    }

    static class LocationHandler extends Handler {
        WeakReference<LocationHelper> mWrLocationHelper;

        LocationHandler(LocationHelper locationHelper) {
            mWrLocationHelper = new WeakReference<LocationHelper>(locationHelper);
        }

        @Override
        public void handleMessage(Message msg) {
            LocationHelper locationHelper = mWrLocationHelper.get();

            if (locationHelper != null) {
                if (locationHelper.mListener != null) {
                    locationHelper.mListener.onLocationEnd(null);
                }

                locationHelper.close();
            }
        }
    }

    private static final int MAX_TIME = 30000;

    private Context mContext;
    private LocationHelperListener mListener;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Handler mHandler;
    private Timer mTimer;

    public LocationHelper(Context context, LocationHelperListener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mLocationManager =
                (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        this.mHandler = new LocationHandler(this);
        this.mTimer = new Timer();

        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        }, MAX_TIME);
    }

    public void startLocation() {
        if (mListener != null) {
            mListener.onLocationStart();
        }

        List<String> providers = mLocationManager.getProviders(true);
        mLocationListener = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}

            @Override
            public void onLocationChanged(Location location) {
                if (mListener != null) {
                    mListener.onLocationEnd(location);
                }

                close();
            }
        };

        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    mLocationListener);
        }

        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                    mLocationListener);
        }
    }

    private void close() {
        if (mLocationListener != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }

        if (mTimer != null) {
            mTimer.cancel();
        }

        mListener = null;
        mLocationListener = null;
        mTimer = null;
    }
}
