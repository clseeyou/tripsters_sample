package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.QuestionList;
import com.tripsters.android.net.NetRequest;
import com.tripsters.android.util.Constants;

import java.io.IOException;

public class GetAppQuestionTask extends AsyncTask<Void, Void, QuestionList> {

    public interface GetAppQuestionTaskResult {
        void onTaskResult(QuestionList result);
    }

    private Context mContext;
    private String mCountryName;
    private int mPage = 1;
    private int mPageCount = Constants.PAGE_COUNT;
    private GetAppQuestionTaskResult mTaskResult;

    public GetAppQuestionTask(Context context, String countryName, int page, int pageCount,
                              GetAppQuestionTaskResult taskResult) {
        this.mContext = context;
        this.mCountryName = countryName;
        this.mPage = page;
        this.mPageCount = pageCount;
        this.mTaskResult = taskResult;
    }

    @Override
    protected QuestionList doInBackground(Void... params) {
        try {
            return NetRequest.getAppQuestion(mContext, mCountryName, mPage, mPageCount);
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
