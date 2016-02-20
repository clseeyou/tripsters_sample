package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.CountryList;
import com.tripsters.android.net.NetRequest;
import com.tripsters.android.util.LogUtils;

import java.io.IOException;

public class GetSupportCountryTask extends AsyncTask<Void, Void, CountryList> {

    public interface GetSupportCountryTaskResult {
        void onTaskResult(CountryList result);
    }

    private Context mContext;
    private GetSupportCountryTaskResult mTaskResult;

    public GetSupportCountryTask(Context context, GetSupportCountryTaskResult taskResult) {
        this.mContext = context;
        this.mTaskResult = taskResult;
    }

    @Override
    protected CountryList doInBackground(Void... params) {
        try {
            return NetRequest.getSupportCountry(mContext);
        } catch (IOException e) {
            LogUtils.loge(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(CountryList result) {
        if (mTaskResult != null) {
            mTaskResult.onTaskResult(result);
        }
    }
}
