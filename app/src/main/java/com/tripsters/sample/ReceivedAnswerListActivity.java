package com.tripsters.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.tripsters.android.model.AnswerList;
import com.tripsters.android.task.GetUserAnsweredTask;
import com.tripsters.sample.adapter.AnswerListAdapter;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.util.IntentUtils;
import com.tripsters.sample.util.LoginUser;
import com.tripsters.sample.util.MessageUnread;
import com.tripsters.sample.view.TEmptyView.Type;
import com.tripsters.sample.view.TListView;
import com.tripsters.sample.view.TListView.ListUpdateListener;
import com.tripsters.sample.view.TitleBar;
import com.tripsters.sample.view.TitleBar.LeftType;
import com.tripsters.sample.view.TitleBar.RightType;

public class ReceivedAnswerListActivity extends BaseActivity {

    private TitleBar mTitleBar;
    private TListView mPullDownView;
    private GetUserAnsweredTask mTask;
    private AnswerListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        mTitleBar.initTitleBar(LeftType.ICON_BACK, R.string.titlebar_received_answers,
                RightType.NONE);
        mTitleBar.setLeftClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPullDownView = (TListView) findViewById(R.id.pd_list);
        mPullDownView.setEmptyType(Type.ANSWERS);
        mAdapter = new AnswerListAdapter(this, true, true);
        mPullDownView.setAdapter(mAdapter, new ListUpdateListener() {

            @Override
            public void loadPageData(int page) {
                loadData(page);
            }
        });
        mPullDownView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentUtils.startQuestionDetailActivity(ReceivedAnswerListActivity.this, mAdapter
                        .getItem(position).getQuestion());
            }
        });

        mPullDownView.firstUpdate();

        MessageUnread.getInstance(LoginUser.getUser(this)).clearAnswerNum();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        mPullDownView.reload();
    }

    private void loadData(int page) {
        mTask = new GetUserAnsweredTask(this, LoginUser.getId(), page, Constants.PAGE_COUNT,
                new GetUserAnsweredTask.GetUserAnsweredTaskResult() {

                    @Override
                    public void onTaskResult(AnswerList result) {
                        ErrorToast.getInstance().checkNetResult(mPullDownView, result);
                    }
                });
        mTask.execute();
    }
}
