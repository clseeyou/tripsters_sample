package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.NetResult;
import com.tripsters.android.net.NetRequest;

import java.io.IOException;

public class SendQuestionTask extends AsyncTask<Void, Void, NetResult> {

    public interface SendQuestionTaskResult {
        public void onTaskResult(NetResult result);
    }

    private Context mContext;
    private String mUid;
    private String mTitle;
    private String mPicPath;
    private String mCountry;
    private String mCities;
    private String mLat;
    private String mLng;
    private String mAddress;
    private SendQuestionTaskResult mTaskResult;

    public SendQuestionTask(Context context, String uid, String title, String picPath,
                            String country, String cities, String lat, String lng, String address,
                            SendQuestionTaskResult taskResult) {
        this.mContext = context;
        this.mUid = uid;
        this.mTitle = title;
        this.mPicPath = picPath;
        this.mCountry = country;
        this.mCities = cities;
        this.mLat = lat;
        this.mLng = lng;
        this.mAddress = address;
        this.mTaskResult = taskResult;
    }

    @Override
    protected NetResult doInBackground(Void... params) {
        try {
            return NetRequest.sendQuestionById(mContext, mUid, mTitle, mPicPath,
                    mCountry, mCities, mLat, mLng, mAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(NetResult result) {
        mTaskResult.onTaskResult(result);
    }
}
