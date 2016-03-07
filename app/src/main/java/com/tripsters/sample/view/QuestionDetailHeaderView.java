package com.tripsters.sample.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripsters.android.model.PicInfo.PicType;
import com.tripsters.android.model.Question;
import com.tripsters.sample.R;
import com.tripsters.sample.util.DateUtils;
import com.tripsters.sample.util.ImageUtils;
import com.tripsters.sample.util.UserUtils;

public class QuestionDetailHeaderView extends LinearLayout {

    private Question mQuestion;

    private PortraitTopView mPortraitLt;
    private TextView mTitleTv;
    private TextView mDetailTv;
    private ImageView mPicIv;
    private TextView mTagsTv;
    private LocationView mLocationView;
    private TextView mTimeBottomTv;
    private TextView mFavNumTv;

    public QuestionDetailHeaderView(Context context) {
        super(context);
        init(context);
    }

    public QuestionDetailHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QuestionDetailHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        setBackgroundColor(getResources().getColor(android.R.color.white));

        View view = View.inflate(context, R.layout.view_question_detail_header, this);
        mPortraitLt = (PortraitTopView) view.findViewById(R.id.lt_portrait);
        mPortraitLt.setVerifyVisible(true);
        mPortraitLt.setTimeVisible(false);
        mTitleTv = (TextView) view.findViewById(R.id.tv_title);
        mDetailTv = (TextView) view.findViewById(R.id.tv_detail);
        mPicIv = (ImageView) view.findViewById(R.id.iv_pic);
//        mPicIv.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Utils.startSingleImageActivity(getContext(), mQuestion.getPicInfo());
//            }
//        });
        mTagsTv = (TextView) view.findViewById(R.id.tv_tags);
        mLocationView = (LocationView) view.findViewById(R.id.v_location);
        mTimeBottomTv = (TextView) view.findViewById(R.id.tv_time_bottom);
        mFavNumTv = (TextView) view.findViewById(R.id.tv_fav_num);
    }

    public void update(Question question) {
        this.mQuestion = question;

        mPortraitLt.update(mQuestion.getUserInfo(), mQuestion.getCreated());
        mTitleTv.setText(mQuestion.getTitle());
        if (TextUtils.isEmpty(mQuestion.getDetail())) {
            mDetailTv.setVisibility(View.GONE);
        } else {
            mDetailTv.setVisibility(View.VISIBLE);
            mDetailTv.setText(mQuestion.getDetail());
        }
        ImageUtils.setResizeImage(getContext(), mPicIv, mQuestion.getPicInfo(), PicType.SMALL);
        mTagsTv.setText(UserUtils.getTags(mQuestion));
        mLocationView.update(mQuestion.getIpaddr(), mQuestion.getAddress());
        mTimeBottomTv.setText(DateUtils.formatDateFromCreated(getContext(), mQuestion.getCreated()));
        mFavNumTv.setText(getResources().getString(R.string.question_item_favorite_num,
                mQuestion.getSaveNum()));
    }
}
