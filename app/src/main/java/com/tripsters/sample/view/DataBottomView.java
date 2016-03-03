package com.tripsters.sample.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tripsters.android.model.PicInfo;
import com.tripsters.android.model.PicInfo.PicType;
import com.tripsters.android.model.UserInfo;
import com.tripsters.sample.R;
import com.tripsters.sample.util.ImageUtils;

public class DataBottomView extends RelativeLayout {

    private ImageView mPicIv;
    private TextView mNickTv;
    private TextView mContentTv;

    public DataBottomView(Context context) {
        super(context);
        init();
    }

    public DataBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setBackgroundResource(R.color.tb_bg_white);
        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.blog_comment_pic_size));

        View view = View.inflate(getContext(), R.layout.view_data_bottom, this);
        mPicIv = (ImageView) view.findViewById(R.id.iv_pic);
        mNickTv = (TextView) view.findViewById(R.id.tv_nick);
        mContentTv = (TextView) view.findViewById(R.id.tv_content);
    }

    public void update(PicInfo picInfo, UserInfo userInfo, String content) {
        if (picInfo == null) {
            mPicIv.setVisibility(View.GONE);
        } else {
            mPicIv.setVisibility(View.VISIBLE);

            ImageUtils.setDataBottomPic(getContext(), mPicIv, picInfo, PicType.SMALL);
        }
        mNickTv.setText(userInfo.getNickname());
        if (TextUtils.isEmpty(content)) {
            mContentTv.setVisibility(View.GONE);
        } else {
            mContentTv.setVisibility(View.VISIBLE);

            mContentTv.setText(content);
        }
    }
}
