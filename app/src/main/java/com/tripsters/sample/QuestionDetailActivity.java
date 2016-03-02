package com.tripsters.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;

import com.tripsters.android.model.AnswerList;
import com.tripsters.android.model.Question;
import com.tripsters.android.model.QuestionResult;
import com.tripsters.android.task.GetAnswerTask;
import com.tripsters.android.task.GetQuestionDetailTask;
import com.tripsters.sample.adapter.AnswerResultListAdapter;
import com.tripsters.sample.composer.BaseComposer.ComposerType;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.view.AnswerNumView;
import com.tripsters.sample.view.QuestionDetailHeaderView;
import com.tripsters.sample.view.TEmptyView.Type;
import com.tripsters.sample.view.TListView;
import com.tripsters.sample.view.TListView.ListUpdateListener;
import com.tripsters.sample.view.TitleBar;
import com.tripsters.sample.view.TitleBar.LeftType;
import com.tripsters.sample.view.TitleBar.RightType;

public class QuestionDetailActivity extends BaseActivity {

    private Question mQuestion;
    private String mQuestionId;

    private QuestionDetailHeaderView mQuestionDetailHeaderView;
    private AnswerNumView mAnswerNumHeaderView;

    private TitleBar mTitleBar;
    private LinearLayout mAnswerLt;
    private TListView mPullDownView;
    private AnswerResultListAdapter mAdapter;
    private AnswerNumView mAnswerNumView;

    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        mQuestion = getIntent().getParcelableExtra(Constants.Extra.QUESTION);

        if (mQuestion == null) {
            finish();

            return;
        }

        mQuestionId = mQuestion.getId();

        if (TextUtils.isEmpty(mQuestionId)) {
            finish();

            return;
        }

        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        mTitleBar.initTitleBar(LeftType.ICON_BACK, R.string.titlebar_question_detail,
                RightType.NONE);
        mTitleBar.setLeftClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAnswerLt = (LinearLayout) findViewById(R.id.lt_answer);
        mAnswerLt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                goAnswer();
            }
        });

        mPullDownView = (TListView) findViewById(R.id.pd_answer);
        mPullDownView.setEmptyType(Type.QUESTION_DETAIL);
        mPullDownView.setDivider(getResources().getDrawable(R.drawable.bg_divider));
        mPullDownView.setDividerHeight(getResources().getDimensionPixelSize(
                R.dimen.tb_divider_height));
        mQuestionDetailHeaderView = new QuestionDetailHeaderView(this);
        mAnswerNumHeaderView = new AnswerNumView(this);
        mPullDownView.addHeaderView(mQuestionDetailHeaderView);
        mPullDownView.addHeaderView(mAnswerNumHeaderView);
        mAnswerNumView = (AnswerNumView) findViewById(R.id.v_answer_num);
        mAdapter = new AnswerResultListAdapter(QuestionDetailActivity.this, mQuestion);
        mPullDownView.setAdapter(mAdapter, new ListUpdateListener() {

            @Override
            public void loadPageData(int page) {
                loadData(page);
            }
        });
        mPullDownView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                if (firstVisibleItem > 0) {
                    mAnswerNumView.setVisibility(View.VISIBLE);
                } else {
                    mAnswerNumView.setVisibility(View.GONE);
                }
            }
        });

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (Constants.Action.ANSWER_SUCCESS.equals(intent.getAction())) {
                    if (mQuestionId.equals(intent.getStringExtra(Constants.Extra.QUESTION_ID))) {
                        mQuestion.setAnswerNum(mQuestion.getAnswerNum() + 1);
                        updateHeader();

                        mPullDownView.reload();
                    }
                }
            }
        };
        IntentFilter intent = new IntentFilter();
        intent.addAction(Constants.Action.ANSWER_SUCCESS);
        registerReceiver(mReceiver, intent);

        updateHeader();
        mPullDownView.firstUpdate();
    }

    private void loadData(final int page) {
        if (page == 1) {
            new GetQuestionDetailTask(TripstersApplication.mContext, mQuestionId, new GetQuestionDetailTask.GetQuestionDetailTaskResult() {
                @Override
                public void onTaskResult(QuestionResult result) {
                    if (ErrorToast.getInstance().checkNetResult(result)) {
                        mQuestion = result.getQuestion();
                        updateHeader();

                        loadAnswers(page);
                    }
                }
            }).execute();
        } else {
            loadAnswers(page);
        }
    }

    private void loadAnswers(int page) {
        new GetAnswerTask(TripstersApplication.mContext, mQuestionId, page, Constants.PAGE_COUNT,
                new GetAnswerTask.GetAnswerTaskResult() {
                    @Override
                    public void onTaskResult(AnswerList result) {
                        setResultInfo(result);
                    }
                }).execute();
    }

    private void setResultInfo(AnswerList result) {
        if (ErrorToast.getInstance().checkNetResult(mPullDownView, result)) {
            mAdapter.notifyData(result.getList());
        }
    }

    @Override
    protected void onDestroy() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }

        super.onDestroy();
    }

    private void goAnswer() {
        Intent intent = new Intent(this, SendActivity.class);
        intent.putExtra(Constants.Extra.COMPOSER_TYPE, ComposerType.SEND_ANSWER.getValue());
        intent.putExtra(Constants.Extra.QUESTION, mQuestion);
        startActivity(intent);
    }

    private void updateHeader() {
        mQuestionDetailHeaderView.update(mQuestion);
        mAnswerNumHeaderView.update(mQuestion);
        mAnswerNumView.update(mQuestion);
    }
}
