package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.TagList;
import com.tripsters.android.net.NetRequest;
import com.tripsters.android.util.LogUtils;

import java.io.IOException;

public class GetSupportCateTask extends AsyncTask<Void, Void, TagList> {

    public interface GetSupportCateTaskResult {
        void onTaskResult(TagList result);
    }

    private Context mContext;
    private GetSupportCateTaskResult mTaskResult;

    public GetSupportCateTask(Context context, GetSupportCateTaskResult taskResult) {
        this.mContext = context;
        this.mTaskResult = taskResult;
    }

    @Override
    protected TagList doInBackground(Void... params) {
        try {
            return NetRequest.getSupportCate(mContext);
        } catch (IOException e) {
            LogUtils.loge(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(TagList result) {
        if (mTaskResult != null) {
            mTaskResult.onTaskResult(result);
        }
    }
}
