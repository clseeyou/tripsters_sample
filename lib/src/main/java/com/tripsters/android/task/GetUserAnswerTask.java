package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.AnswerList;
import com.tripsters.android.net.NetRequest;

import java.io.IOException;

public class GetUserAnswerTask extends AsyncTask<Void, Void, AnswerList> {

    public interface GetUserAnswerTaskResult {
        void onTaskResult(AnswerList result);
    }

    private Context mContext;
    private String mUid;
    private int mPage;
    private int mPageCount;
    private GetUserAnswerTaskResult mTaskResult;

    public GetUserAnswerTask(Context context, String uid, int page, int pageCount,
                             GetUserAnswerTaskResult taskResult) {
        this.mContext = context;
        this.mUid = uid;
        this.mPage = page;
        this.mPageCount = pageCount;
        this.mTaskResult = taskResult;
    }

    @Override
    protected AnswerList doInBackground(Void... params) {
        try {
            return NetRequest.getUserAnswer(mContext, mUid, mPage, mPageCount);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(AnswerList result) {
        if (mTaskResult != null) {
            mTaskResult.onTaskResult(result);
        }
    }
}
