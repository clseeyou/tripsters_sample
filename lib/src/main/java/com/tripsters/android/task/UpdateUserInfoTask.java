package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.NetResult;
import com.tripsters.android.net.NetRequest;

import java.io.IOException;

public class UpdateUserInfoTask extends AsyncTask<Void, Void, NetResult> {

    public interface UpdateUserInfoTaskResult {
        void onTaskResult(NetResult result);
    }

    private Context mContext;
    private String mUid;
    private String mChannelId;
    private UpdateUserInfoTaskResult mTaskResult;

    public UpdateUserInfoTask(Context context, String uid, String channelId,
                              UpdateUserInfoTaskResult taskResult) {
        this.mContext = context;
        this.mUid = uid;
        this.mChannelId = channelId;
        this.mTaskResult = taskResult;
    }

    @Override
    protected NetResult doInBackground(Void... params) {
        try {
            return NetRequest.updateUserInfo(mContext, mUid, mChannelId);
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
