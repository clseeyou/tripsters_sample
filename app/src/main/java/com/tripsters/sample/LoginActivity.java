package com.tripsters.sample;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.tripsters.android.model.UserInfoResult;
import com.tripsters.android.task.LoginTask;
import com.tripsters.android.task.LoginTask.LoginTaskResult;
import com.tripsters.sample.util.CheckUtils;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.util.LoginUser;
import com.tripsters.sample.view.TitleBar;
import com.tripsters.sample.view.TitleBar.LeftType;
import com.tripsters.sample.view.TitleBar.RightType;

public class LoginActivity extends BaseActivity {

    private TitleBar mTitleBar;

    private EditText mAppuidEt;
    private EditText mNicknameEt;
    private EditText mAvatarEt;
    private EditText mGenderEt;
    private EditText mLocationEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        mTitleBar.initTitleBar(LeftType.NONE, R.string.titlebar_login, RightType.TEXT_DONE);
        mTitleBar.setRightClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                login(mAppuidEt.getText().toString(), mNicknameEt.getText().toString(),
                        mAvatarEt.getText().toString(), mGenderEt.getText().toString(),
                        mLocationEt.getText().toString());
            }
        });

        mAppuidEt = (EditText) findViewById(R.id.et_appuid);
        mNicknameEt = (EditText) findViewById(R.id.et_nickname);
        mAvatarEt = (EditText) findViewById(R.id.et_avatar);
        mGenderEt = (EditText) findViewById(R.id.et_gender);
        mLocationEt = (EditText) findViewById(R.id.et_location);

        mAppuidEt.setText("1");
        mNicknameEt.setText("android");
        mAvatarEt.setText("http://a.hiphotos.baidu.com/baike/w%3D268/sign=b63699f3a8773912c4268267c0188675/10dfa9ec8a13632722e51d7d908fa0ec08fac770.jpg");
        mGenderEt.setText("f");
        mLocationEt.setText("北京");
    }

    protected void login(String appuid, String nickname, String avatar, String gender, String location) {
        if (CheckUtils.checkLoginAppuidValid(appuid) && CheckUtils.checkLoginNicknameValid(nickname)
                && CheckUtils.checkLoginAvatarValid(avatar)) {
            showProgress(R.string.login_now);

            new LoginTask(TripstersApplication.mContext, appuid, nickname, avatar, gender, location,
                    new LoginTaskResult() {
                        @Override
                        public void onTaskResult(UserInfoResult result) {
                            dismissProgress();

                            if (ErrorToast.getInstance().checkNetResult(result)) {
                                LoginUser.setUser(result.getUserInfo());

                                finish();
                            }
                        }
                    }).execute();
        }
    }
}
