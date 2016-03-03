package com.tripsters.sample;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.tripsters.android.model.UserInfo;
import com.tripsters.android.model.UserInfoResult;
import com.tripsters.android.task.GetUserInfoTask;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.util.LoginUser;
import com.tripsters.sample.view.ProfileHeaderView;
import com.tripsters.sample.view.ProfileView;
import com.tripsters.sample.view.ProfileView.UpdateListener;

public class ProfileActivity extends BaseActivity {

    private UserInfo mUserInfo;
    private String mUid;

    private ImageView mBackIv;

    private ProfileHeaderView mHeaderView;
    private ProfileView mProfileView;

    private boolean mTaskRunning;
    private GetUserInfoTask mUserInfoTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mUserInfo = getIntent().getParcelableExtra(Constants.Extra.USERINFO);

        if (mUserInfo == null) {
            mUid = getIntent().getStringExtra(Constants.Extra.USER_ID);
        } else {
            mUid = mUserInfo.getId();
        }

        if (TextUtils.isEmpty(mUid)) {
            finish();
            return;
        } else {
            if (mUserInfo == null && mUid.equals(LoginUser.getId())) {
                mUserInfo = LoginUser.getUser(this);
            }
        }

        mBackIv = (ImageView) findViewById(R.id.iv_back);
        mBackIv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mHeaderView = (ProfileHeaderView) findViewById(R.id.profile_info);
        mProfileView = (ProfileView) findViewById(R.id.view_profile);
        mProfileView.setUpdateListener(new UpdateListener() {

            @Override
            public void loadData() {
                ProfileActivity.this.loadData();
            }
        });

        initData();
    }

    private void initData() {
        if (mUserInfo != null) {
            mHeaderView.update(mUserInfo);
            mProfileView.update(mUserInfo);
        }

        mProfileView.firstUpdate();
    }

    private void loadData() {
        if (!mTaskRunning) {
            mTaskRunning = true;

            mUserInfoTask =
                    new GetUserInfoTask(TripstersApplication.mContext, mUid,
                            new GetUserInfoTask.GetUserInfoTaskResult() {

                                @Override
                                public void onTaskResult(UserInfoResult result) {
                                    mTaskRunning = false;

                                    if (ErrorToast.getInstance().checkNetResult(mProfileView,
                                            result)) {
                                        mUserInfo = result.getUserInfo();

                                        mHeaderView.update(mUserInfo);
                                        mProfileView.endLoadSuccess(mUserInfo);
                                    }
                                }
                            });
            mUserInfoTask.execute();
        }
    }
}
