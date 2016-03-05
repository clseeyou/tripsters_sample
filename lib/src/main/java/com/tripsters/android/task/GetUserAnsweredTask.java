package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.AnswerList;
import com.tripsters.android.net.NetRequest;

import java.io.IOException;

public class GetUserAnsweredTask extends AsyncTask<Void, Void, AnswerList> {

    public interface GetUserAnsweredTaskResult {
        void onTaskResult(AnswerList result);
    }

    private Context mContext;
    private String mUid;
    private int mPage;
    private int mPageCount;
    private GetUserAnsweredTaskResult mTaskResult;

    public GetUserAnsweredTask(Context context, String uid, int page, int pageCount,
                               GetUserAnsweredTaskResult taskResult) {
        this.mContext = context;
        this.mUid = uid;
        this.mPage = page;
        this.mPageCount = pageCount;
        this.mTaskResult = taskResult;
    }

    @Override
    protected AnswerList doInBackground(Void... params) {
        try {
            return NetRequest.getUserAnswered(mContext, mUid, mPage, mPageCount);
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
