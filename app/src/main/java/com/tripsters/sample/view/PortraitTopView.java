package com.tripsters.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tripsters.android.model.UserInfo;
import com.tripsters.sample.R;
import com.tripsters.sample.util.DateUtils;
import com.tripsters.sample.util.ImageUtils;
import com.tripsters.sample.util.IntentUtils;
import com.tripsters.sample.util.UserUtils;

import java.util.Date;

public class PortraitTopView extends RelativeLayout {

    private UserInfo mUserInfo;
    private boolean mVerifyVisible = false;
    private boolean mTimeVisible = true;

    private PortraitView mPortraitPv;
    private TextView mNickTv;
    private TextView mVerifyTv;
    private TextView mTimeTv;

    public PortraitTopView(Context context) {
        super(context);
        init();
    }

    public PortraitTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.view_portrait_top, this);
        mPortraitPv = (PortraitView) view.findViewById(R.id.pv_portrait);
        mPortraitPv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                IntentUtils.startUserInfoActivity(getContext(), mUserInfo);
            }
        });
        mNickTv = (TextView) view.findViewById(R.id.tv_nick);
        mVerifyTv = (TextView) view.findViewById(R.id.tv_verify);
        mTimeTv = (TextView) view.findViewById(R.id.tv_time);
    }

    public void update(UserInfo userInfo, long created) {
        mUserInfo = userInfo;

        ImageUtils.setAvata(getContext(), mPortraitPv, mUserInfo);
        mNickTv.setText(mUserInfo == null ? "" : mUserInfo.getNickname());
        if (mVerifyVisible) {
            mVerifyTv.setVisibility(View.VISIBLE);
            mVerifyTv.setText(UserUtils.getVerifyAndLocationInfo(getContext(), mUserInfo));
        } else {
            mVerifyTv.setVisibility(View.GONE);
        }
        if (mTimeVisible) {
            mTimeTv.setVisibility(View.VISIBLE);
            mTimeTv.setText(DateUtils.formatDateFromCreated(getContext(), created));

            if (!mVerifyVisible) {
                mTimeTv.setLayoutParams(mVerifyTv.getLayoutParams());
            }
        } else {
            mTimeTv.setVisibility(View.GONE);
        }
    }

    public void setVerifyVisible(boolean verifyVisible) {
        this.mVerifyVisible = verifyVisible;
    }

    public void setTimeVisible(boolean timeVisible) {
        this.mTimeVisible = timeVisible;
    }

    public void setNick(CharSequence text) {
        mNickTv.setText(text);
    }
}
