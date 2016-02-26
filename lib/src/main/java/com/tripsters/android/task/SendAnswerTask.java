package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.NetResult;
import com.tripsters.android.net.NetRequest;

import java.io.IOException;

public class SendAnswerTask extends AsyncTask<Void, Void, NetResult> {

    public interface SendAnswerTaskResult {
        public void onTaskResult(NetResult result);
    }

    private Context mContext;
    private String mUid;
    private String mTitle;
    private String mPicPath;
    private String mPois;
    private String mBlogs;
    private String mQid;
    private String mQuid;
    private SendAnswerTaskResult mTaskResult;

    public SendAnswerTask(Context context, String uid, String title, String detail,
                          String picPath, String pois, String blogs, String qid, String quid, SendAnswerTaskResult taskResult) {
        this.mContext = context;
        this.mUid = uid;
        this.mTitle = title;
        this.mPicPath = picPath;
        this.mPois = pois;
        this.mBlogs = blogs;
        this.mQid = qid;
        this.mQuid = quid;
        this.mTaskResult = taskResult;
    }

    @Override
    protected NetResult doInBackground(Void... params) {
        try {
            return NetRequest.sendAnswerById(mContext, mUid, mTitle, mPicPath, mPois, mBlogs,
                    mQid, mQuid);
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
