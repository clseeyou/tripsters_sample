package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.CityList;
import com.tripsters.android.net.NetRequest;
import com.tripsters.android.util.LogUtils;

import java.io.IOException;

public class GetSupportCityTask extends AsyncTask<Void, Void, CityList> {

    public interface GetSupportCityTaskResult {
        void onTaskResult(CityList result);
    }

    private Context mContext;
    private GetSupportCityTaskResult mTaskResult;

    private String mCountryCode;

    public GetSupportCityTask(Context context, String countryCode, GetSupportCityTaskResult taskResult) {
        this.mContext = context;
        this.mCountryCode = countryCode;
        this.mTaskResult = taskResult;
    }

    @Override
    protected CityList doInBackground(Void... params) {
        try {
            return NetRequest.getSupportCity(mContext, mCountryCode);
        } catch (IOException e) {
            LogUtils.loge(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(CityList result) {
        if (mTaskResult != null) {
            mTaskResult.onTaskResult(result);
        }
    }
}
