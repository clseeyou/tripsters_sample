package com.tripsters.sample;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.tripsters.sample.manager.ProfileQuestionsManager;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.IntentUtils;
import com.tripsters.sample.util.UserUtils;
import com.tripsters.sample.view.TListView;
import com.tripsters.sample.view.TListView.ListUpdateListener;
import com.tripsters.sample.view.TitleBar;
import com.tripsters.sample.view.TitleBar.LeftType;
import com.tripsters.sample.view.TitleBar.RightType;

public class ProfileQuestionsActivity extends BaseActivity {

    private String mUserId;

    private TitleBar mTitleBar;

    private TListView mPullDownView;

    private ProfileQuestionsManager mProfileQuestionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mUserId = getIntent().getStringExtra(Constants.Extra.USER_ID);

        if (TextUtils.isEmpty(mUserId)) {
            finish();
            return;
        }

        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        mTitleBar.initTitleBar(LeftType.ICON_BACK,
                UserUtils.isMyself(mUserId) ? R.string.titlebar_my_questions
                        : R.string.titlebar_other_questions, RightType.NONE);
        mTitleBar.setLeftClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPullDownView = (TListView) findViewById(R.id.pd_list);

        mProfileQuestionsManager = new ProfileQuestionsManager(mPullDownView, mUserId, true);
        mPullDownView.setAdapter(mProfileQuestionsManager.getAdapter(), new ListUpdateListener() {

            @Override
            public void loadPageData(int page) {
                mProfileQuestionsManager.loadData(page);
            }
        });
        mPullDownView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentUtils.startQuestionDetailActivity(ProfileQuestionsActivity.this,
                        mProfileQuestionsManager.getAdapter().getItem(position));
            }
        });

        mPullDownView.firstUpdate();
    }
}
