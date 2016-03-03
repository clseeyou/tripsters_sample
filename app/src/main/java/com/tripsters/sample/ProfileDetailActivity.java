package com.tripsters.sample;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tripsters.android.model.UserInfo;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.UserUtils;
import com.tripsters.sample.view.TitleBar;
import com.tripsters.sample.view.TitleBar.LeftType;
import com.tripsters.sample.view.TitleBar.RightType;

public class ProfileDetailActivity extends BaseActivity {

    private UserInfo mUserInfo;

    private TitleBar mTitleBar;
    private TextView mNameEt;
    private TextView mGenderTv;
    private TextView mAddressEt;
    private TextView mNationEt;
    private TextView mOccupationEt;
    private TextView mIntroEt;
    private TextView mTripTitleTv;
    private RelativeLayout mTripLt;
    private TextView mTripEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        mUserInfo = getIntent().getParcelableExtra(Constants.Extra.USER);

        if (mUserInfo == null) {
            finish();
            return;
        }

        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        mTitleBar.initTitleBar(LeftType.ICON_BACK, R.string.titlebar_other_profile,
                RightType.NONE);
        mTitleBar.setLeftClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mNameEt = (TextView) findViewById(R.id.et_edit_name);
        mGenderTv = (TextView) findViewById(R.id.tv_edit_gender);
        mAddressEt = (TextView) findViewById(R.id.et_edit_address);
        mNationEt = (TextView) findViewById(R.id.et_edit_nation);
        mOccupationEt = (TextView) findViewById(R.id.et_edit_occupation);
        mIntroEt = (TextView) findViewById(R.id.et_edit_intro);
        mTripTitleTv = (TextView) findViewById(R.id.tv_edit_trip_title);
        mTripLt = (RelativeLayout) findViewById(R.id.lt_edit_trip);
        mTripEt = (TextView) findViewById(R.id.et_edit_trip);

        if (UserUtils.isDaren(mUserInfo)) {
            mOccupationEt.setHint(R.string.profile_edit_occupation_daren_hint);
            mIntroEt.setHint(R.string.profile_edit_intro_daren_hint);
        } else {
            mOccupationEt.setHint(R.string.profile_edit_occupation_normal_hint);
            mIntroEt.setHint(R.string.profile_edit_intro_normal_hint);
        }

        update(mUserInfo);
    }

    private void update(UserInfo userInfo) {
        mNameEt.setText(mUserInfo.getNickname());
        mGenderTv.setText(UserUtils.getGender(this, mUserInfo.getGender()));
        if (TextUtils.isEmpty(userInfo.getLocation())) {
            mAddressEt.setText(R.string.profile_info_location_default);
        } else {
            mAddressEt.setText(mUserInfo.getLocation());
        }
        if (TextUtils.isEmpty(userInfo.getNation())) {
            mNationEt.setText(R.string.profile_info_nation_default);
        } else {
            mNationEt.setText(userInfo.getNation());
        }
        if (TextUtils.isEmpty(userInfo.getOccupation())) {
            mOccupationEt.setText(R.string.profile_info_occupation_default);
        } else {
            mOccupationEt.setText(userInfo.getOccupation());
        }
        if (TextUtils.isEmpty(userInfo.getDescription())) {
            mIntroEt.setText(R.string.profile_info_intro_default);
        } else {
            mIntroEt.setText(userInfo.getDescription());
        }
        if (UserUtils.isDaren(mUserInfo)) {
            mTripTitleTv.setVisibility(View.GONE);
            mTripLt.setVisibility(View.GONE);
        } else {
            mTripTitleTv.setVisibility(View.VISIBLE);
            mTripLt.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(userInfo.getTrip())) {
                mTripEt.setText(R.string.profile_info_trip_default);
            } else {
                mTripEt.setText(userInfo.getTrip());
            }
        }
    }
}
