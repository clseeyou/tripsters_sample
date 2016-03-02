package com.tripsters.sample.fragment;

import com.tripsters.android.model.QuestionList;
import com.tripsters.android.task.GetAppQuestionTask;
import com.tripsters.sample.TripstersApplication;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.LoginUser;

public class AppQuestionListFragment extends QuestionListFragment {

    @Override
    protected void loadData(int page) {
        new GetAppQuestionTask(TripstersApplication.mContext, LoginUser.getCountry().getCountryNameCn(),
                page, Constants.PAGE_COUNT, new GetAppQuestionTask.GetAppQuestionTaskResult() {
            @Override
            public void onTaskResult(QuestionList result) {
                setResultInfo(result);
            }
        }).execute();
    }
}
