package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.AnswerList;
import com.tripsters.android.net.NetRequest;
import com.tripsters.android.util.Constants;

import java.io.IOException;

public class GetAnswerTask extends AsyncTask<Void, Void, AnswerList> {

    public interface GetAnswerTaskResult {
        public void onTaskResult(AnswerList result);
    }

    private Context mContext;
    private String mQid;
    private int mPage = 1;
    private int mPageCount = Constants.PAGE_COUNT;
    private GetAnswerTaskResult mTaskResult;

    public GetAnswerTask(Context context, String qid, int page, int pageCount,
                         GetAnswerTaskResult taskResult) {
        this.mContext = context;
        this.mQid = qid;
        this.mPage = page;
        this.mPageCount = pageCount;
        this.mTaskResult = taskResult;
    }

    @Override
    protected AnswerList doInBackground(Void... params) {
        try {
            return NetRequest.getAnswer(mContext, mQid, mPage, mPageCount);
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
