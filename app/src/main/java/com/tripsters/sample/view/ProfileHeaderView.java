package com.tripsters.sample.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tripsters.android.model.Gender;
import com.tripsters.android.model.Identity;
import com.tripsters.android.model.UserInfo;
import com.tripsters.sample.R;
import com.tripsters.sample.util.ImageUtils;
import com.tripsters.sample.util.UserUtils;

public class ProfileHeaderView extends RelativeLayout {

    protected TextView mNameTv;
    protected PortraitView mPortraitPv;
    protected TextView mLocationTv;
    protected TextView mIntroTv;
    protected ProfileTileView mTile1;
    protected ProfileTileView mTile2;
    protected ProfileTileView mTile3;
    protected ProfileTileView mTile4;

    protected UserInfo mUserInfo;

    public ProfileHeaderView(Context context) {
        super(context);
        init(context);
    }

    public ProfileHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void init(Context context) {
        View view = View.inflate(context, R.layout.view_profile_header, this);

        mNameTv = (TextView) findViewById(R.id.tv_name);
        mPortraitPv = (PortraitView) view.findViewById(R.id.pv_portrait);
//        mPortraitPv.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (mUserInfo != null && !TextUtils.isEmpty(mUserInfo.getAvatar())) {
//                    PicInfo picInfo = new PicInfo();
//                    picInfo.setPic(mUserInfo.getAvatar());
//                    Utils.startSingleImageActivity(getContext(), picInfo);
//                }
//            }
//        });
        mLocationTv = (TextView) findViewById(R.id.tv_location);
        mIntroTv = (TextView) findViewById(R.id.tv_intro);
        mTile1 = (ProfileTileView) findViewById(R.id.lt_tile1);
        mTile2 = (ProfileTileView) findViewById(R.id.lt_tile2);
        mTile3 = (ProfileTileView) findViewById(R.id.lt_tile3);
        mTile4 = (ProfileTileView) findViewById(R.id.lt_tile4);

        mTile1.setTitle(getResources().getString(R.string.profile_tile_friend));
        mTile2.setTitle(getResources().getString(R.string.profile_tile_follower));
        mTile3.setTitle(getResources().getString(R.string.profile_tile_point));
        mTile4.setTitle(getResources().getString(R.string.profile_tile_gold));

        clear();
    }

    protected String getUserId() {
        String userId = "";

        if (mUserInfo != null) {
            userId = mUserInfo.getId();
        }

        if (!TextUtils.isEmpty(userId)) {
            return userId;
        }

        return "";
    }

    protected String getUserName() {
        String userName = "";

        if (mUserInfo != null) {
            userName = mUserInfo.getNickname();
        }

        if (!TextUtils.isEmpty(userName)) {
            return userName;
        }

        return "";
    }

    protected String getUserPortraitUrl() {
        String userProtraitUrl = "";

        if (mUserInfo != null) {
            userProtraitUrl = mUserInfo.getAvatar();
        }

        if (!TextUtils.isEmpty(userProtraitUrl)) {
            return userProtraitUrl;
        }

        return "";
    }

    protected Gender getUserGender() {
        if (mUserInfo != null) {
            return mUserInfo.getGender();
        }

        return Gender.FEMALE;
    }

    protected int getUserIdentity() {
        if (mUserInfo != null) {
            return mUserInfo.getIdentity();
        }

        return Identity.NORMAL.getValue();
    }

    public String getPhone() {
        String phone = "";

        if (mUserInfo != null) {
            phone = mUserInfo.getPhone();
        }

        if (!TextUtils.isEmpty(phone)) {
            return phone;
        }

        return "";
    }

    protected boolean isFollowing() {
        if (mUserInfo != null) {
            return mUserInfo.getFans() == 1;
        }

        return false;
    }

    public void clear() {
        mUserInfo = null;

        mNameTv.setVisibility(View.INVISIBLE);
        mPortraitPv.setDeault(true);
        mLocationTv.setVisibility(View.INVISIBLE);
        mIntroTv.setVisibility(View.INVISIBLE);
        mTile1.setVisibility(View.GONE);
        mTile2.setVisibility(View.GONE);
        mTile3.setVisibility(View.GONE);
        mTile4.setVisibility(View.GONE);
    }

    public void update(UserInfo userInfo) {
        mUserInfo = userInfo;

        mNameTv.setVisibility(View.VISIBLE);
        mNameTv.setText(mUserInfo.getNickname());
        ImageUtils.setAvata(getContext(), mPortraitPv, mUserInfo);

        mLocationTv.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(mUserInfo.getLocation())) {
            mLocationTv.setText(R.string.profile_info_location_default);
        } else {
            mLocationTv.setText(mUserInfo.getLocation());
        }
        mIntroTv.setVisibility(View.VISIBLE);
        mIntroTv.setText(UserUtils.getVerifyInfo(getContext(), mUserInfo, true));

        mTile1.setVisibility(View.VISIBLE);
        mTile2.setVisibility(View.VISIBLE);
        mTile3.setVisibility(View.VISIBLE);
        mTile4.setVisibility(View.VISIBLE);
        mTile1.setCount(String.valueOf(mUserInfo.getFriendsCount()));
        mTile2.setCount(String.valueOf(mUserInfo.getFollowersCount()));
        mTile3.setCount(String.valueOf(mUserInfo.getPoints()));
        mTile4.setCount(String.valueOf(mUserInfo.getGold()));
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }
}
