package com.tripsters.sample.manager;

import com.tripsters.android.model.QuestionList;
import com.tripsters.android.task.GetUserQuestionTask;
import com.tripsters.sample.TripstersApplication;
import com.tripsters.sample.adapter.QuestionListAdapter;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.view.TEmptyView.Type;
import com.tripsters.sample.view.TListView;

public class ProfileQuestionsManager {

    private TListView mTbListView;
    private GetUserQuestionTask mTask;
    private QuestionListAdapter mAdapter;
    private String mId;

    public ProfileQuestionsManager(TListView tbListView, String id, boolean showPortrait) {
        this.mId = id;
        init(tbListView, showPortrait);
    }

    private void init(TListView tbListView, boolean showPortrait) {
        mTbListView = tbListView;
        mTbListView.setEmptyType(Type.QUESTIONS);
        mAdapter = new QuestionListAdapter(tbListView.getContext(), showPortrait);
    }

    public QuestionListAdapter getAdapter() {
        return mAdapter;
    }

    public void loadData(int page) {
        mTask =
                new GetUserQuestionTask(TripstersApplication.mContext, mId, page, Constants.PAGE_COUNT,
                        new GetUserQuestionTask.GetUserQuestionTaskResult() {

                            @Override
                            public void onTaskResult(QuestionList result) {
                                setResultInfo(result);
                            }
                        });
        mTask.execute();
    }

    private void setResultInfo(QuestionList result) {
        ErrorToast.getInstance().checkNetResult(mTbListView, result, false);
    }

    public void setId(String id) {
        if (mTask != null) {
            mTask.cancel(true);
        }

        this.mId = id;
    }

    public void clear() {
        setId("");
        mTbListView.clear();
    }
}
