package com.tripsters.sample.manager;

import com.tripsters.android.model.AnswerList;
import com.tripsters.android.task.GetUserAnswerTask;
import com.tripsters.sample.TripstersApplication;
import com.tripsters.sample.adapter.AnswerListAdapter;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.view.TEmptyView.Type;
import com.tripsters.sample.view.TListView;

public class ProfileAnswersManager {

    private TListView mTbListView;
    private GetUserAnswerTask mTask;
    private AnswerListAdapter mAdapter;
    private String mId;

    public ProfileAnswersManager(TListView tbListView, String id, boolean showPortrait) {
        this.mId = id;
        init(tbListView, showPortrait);
    }

    private void init(TListView tbListView, boolean showPortrait) {
        mTbListView = tbListView;
        mTbListView.setEmptyType(Type.ANSWERS);
        mAdapter = new AnswerListAdapter(tbListView.getContext(), showPortrait);
    }

    public AnswerListAdapter getAdapter() {
        return mAdapter;
    }

    public void loadData(int page) {
        mTask =
                new GetUserAnswerTask(TripstersApplication.mContext, mId, page, Constants.PAGE_COUNT,
                        new GetUserAnswerTask.GetUserAnswerTaskResult() {

                            @Override
                            public void onTaskResult(AnswerList result) {
                                setResultInfo(result);
                            }
                        });
        mTask.execute();
    }

    private void setResultInfo(AnswerList result) {
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
