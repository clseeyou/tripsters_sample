package com.tripsters.sample.task;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

public class GeoAddressTask extends AsyncTask<Void, Void, Address> {

    public interface GeoAddressTaskResult {
        void onTaskResult(Address result);
    }

    private Context mContext;
    private Location mLocation;
    private GeoAddressTaskResult mTaskResult;

    public GeoAddressTask(Context context, Location location, GeoAddressTaskResult taskResult) {
        this.mContext = context;
        this.mLocation = location;
        this.mTaskResult = taskResult;
    }

    @Override
    protected Address doInBackground(Void... params) {
        try {
            Geocoder geocoder = new Geocoder(mContext);
            List<Address> addresses =
                    geocoder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Address result) {
        if (mTaskResult != null) {
            mTaskResult.onTaskResult(result);
        }
    }
}
