package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.QuestionList;
import com.tripsters.android.net.NetRequest;

import java.io.IOException;

public class GetAllQuestionTask extends AsyncTask<Void, Void, QuestionList> {

    public interface GetAllQuestionTaskResult {
        void onTaskResult(QuestionList result);
    }

    private Context mContext;
    private String mCountryName;
    private int mPage;
    private int mPageCount;
    private GetAllQuestionTaskResult mTaskResult;

    public GetAllQuestionTask(Context context, String countryName, int page, int pageCount,
                              GetAllQuestionTaskResult taskResult) {
        this.mContext = context;
        this.mCountryName = countryName;
        this.mPage = page;
        this.mPageCount = pageCount;
        this.mTaskResult = taskResult;
    }

    @Override
    protected QuestionList doInBackground(Void... params) {
        try {
            return NetRequest.getAllQuestion(mContext, mCountryName, mPage, mPageCount);
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
