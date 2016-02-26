package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.QuestionResult;
import com.tripsters.android.net.NetRequest;

import java.io.IOException;

public class GetQuestionDetailTask extends AsyncTask<Void, Void, QuestionResult> {

    public interface GetQuestionDetailTaskResult {
        public void onTaskResult(QuestionResult result);
    }

    private Context mContext;
    private String mQid;
    private GetQuestionDetailTaskResult mTaskResult;

    public GetQuestionDetailTask(Context context, String qid,
                                 GetQuestionDetailTaskResult taskResult) {
        this.mContext = context;
        this.mQid = qid;
        this.mTaskResult = taskResult;
    }

    @Override
    protected QuestionResult doInBackground(Void... params) {
        try {
            return NetRequest.getQuestionDetail(mContext, mQid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(QuestionResult result) {
        if (mTaskResult != null) {
            mTaskResult.onTaskResult(result);
        }
    }
}
