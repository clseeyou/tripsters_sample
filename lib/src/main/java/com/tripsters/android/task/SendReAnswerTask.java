package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.NetResult;
import com.tripsters.android.net.NetRequest;

import java.io.IOException;

public class SendReAnswerTask extends AsyncTask<Void, Void, NetResult> {

    public interface SendReAnswerTaskResult {
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
    private String mAuid;
    private SendReAnswerTaskResult mTaskResult;

    public SendReAnswerTask(Context context, String uid, String title, String detail,
                            String picPath, String pois, String blogs, String qid, String quid, String auid, SendReAnswerTaskResult taskResult) {
        this.mContext = context;
        this.mUid = uid;
        this.mTitle = title;
        this.mPicPath = picPath;
        this.mPois = pois;
        this.mBlogs = blogs;
        this.mQid = qid;
        this.mQuid = quid;
        this.mAuid = auid;
        this.mTaskResult = taskResult;
    }

    @Override
    protected NetResult doInBackground(Void... params) {
        try {
            return NetRequest.sendReAnswerById(mContext, mUid, mTitle, mPicPath, mPois, mBlogs,
                    mQid, mQuid, mAuid);
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
