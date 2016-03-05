package com.tripsters.sample.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.tripsters.android.model.QuestionList;
import com.tripsters.android.task.GetAllQuestionTask;
import com.tripsters.sample.R;
import com.tripsters.sample.TripstersApplication;
import com.tripsters.sample.adapter.QuestionListAdapter;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.util.IntentUtils;
import com.tripsters.sample.util.LoginUser;
import com.tripsters.sample.view.TEmptyView.Type;
import com.tripsters.sample.view.TListView;
import com.tripsters.sample.view.TListView.ListUpdateListener;

public class QuestionListFragment extends BaseFragment {

    private QuestionListAdapter mAdapter;
    private TListView mTbListView;

    private BroadcastReceiver mReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questionlist, container, false);

        mTbListView = (TListView) v.findViewById(R.id.pd_list);
        mTbListView.setEmptyType(Type.QUESTIONS);
        mAdapter = new QuestionListAdapter(getActivity());
        mTbListView.setAdapter(mAdapter, new ListUpdateListener() {

            @Override
            public void loadPageData(int page) {
                loadData(page);
            }
        });

        mTbListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentUtils.startQuestionDetailActivity(getActivity(), mAdapter.getItem(position));
            }
        });

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (Constants.Action.CHANGE_LOCATION.equals(intent.getAction())) {
                    mTbListView.clear();
                    mTbListView.firstUpdate();
                } else if (Constants.Action.QUESTION_SUCCESS.equals(intent.getAction())) {
                    mTbListView.reload();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        setFilterAction(intentFilter);
        getActivity().registerReceiver(mReceiver, intentFilter);

        mTbListView.firstUpdate();

        return v;
    }

    protected void setFilterAction(IntentFilter intentFilter) {
        intentFilter.addAction(Constants.Action.CHANGE_LOCATION);
        intentFilter.addAction(Constants.Action.QUESTION_SUCCESS);
    }

    protected void loadData(int page) {
        new GetAllQuestionTask(TripstersApplication.mContext, LoginUser.getCountry().getCountryNameCn(),
                page, Constants.PAGE_COUNT, new GetAllQuestionTask.GetAllQuestionTaskResult() {
            @Override
            public void onTaskResult(QuestionList result) {
                setResultInfo(result);
            }
        }).execute();
    }

    protected void setResultInfo(QuestionList result) {
        ErrorToast.getInstance().checkNetResult(mTbListView, result, false);
    }

    @Override
    public void onDestroyView() {
        if (mReceiver != null) {
            getActivity().unregisterReceiver(mReceiver);
        }

        super.onDestroy();
    }
}
