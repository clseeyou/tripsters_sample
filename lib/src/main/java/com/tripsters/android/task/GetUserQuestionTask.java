package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.QuestionList;
import com.tripsters.android.net.NetRequest;

import java.io.IOException;

public class GetUserQuestionTask extends AsyncTask<Void, Void, QuestionList> {

    public interface GetUserQuestionTaskResult {
        void onTaskResult(QuestionList result);
    }

    private Context mContext;
    private String mUid;
    private int mPage;
    private int mPageCount;
    private GetUserQuestionTaskResult mTaskResult;

    public GetUserQuestionTask(Context context, String uid, int page, int pageCount,
                               GetUserQuestionTaskResult taskResult) {
        this.mContext = context;
        this.mUid = uid;
        this.mPage = page;
        this.mPageCount = pageCount;
        this.mTaskResult = taskResult;
    }

    @Override
    protected QuestionList doInBackground(Void... params) {
        try {
            return NetRequest.getUserQuestion(mContext, mUid, mPage, mPageCount);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(QuestionList result) {
        if (mTaskResult != null) {
            mTaskResult.onTaskResult(result);
        }
    }
}
