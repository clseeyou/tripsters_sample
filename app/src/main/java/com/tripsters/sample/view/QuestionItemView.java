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
import com.tripsters.sample.util.IntentUtils;
import com.tripsters.sample.util.UserUtils;

import java.util.Date;

public class QuestionItemView extends LinearLayout {

    private boolean mPortraitVisible = true;
    private boolean mDetailVisible = true;
    private boolean mAnswerVisible = false;
    private Question mQuestion;

    private PortraitView mPvPortrait;
    private TextView mNameTv;
    private TextView mDetailTv;
    private ImageView mQuestionPicIv;
    private TextView mTagsTv;
    private LocationView mLocationView;
    private TextView mTimeTv;
    private TextView mAnswerNumTv;
    private LinearLayout mAnswerLt;
    private TextView mAnswerDetailTv;
    private ImageView mAnswerPicIv;

    public QuestionItemView(Context context) {
        super(context);
        init(context);
    }

    public QuestionItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QuestionItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        setBackgroundResource(R.drawable.bg_item);

        View view = View.inflate(context, R.layout.item_question, this);
        mPvPortrait = (PortraitView) view.findViewById(R.id.pv_portrait);
        mPvPortrait.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                IntentUtils.startUserInfoActivity(getContext(), mQuestion.getUserInfo());
            }
        });
        mNameTv = (TextView) view.findViewById(R.id.tv_name);
        mDetailTv = (TextView) view.findViewById(R.id.tv_detail);
        mQuestionPicIv = (ImageView) view.findViewById(R.id.iv_question_pic);
//        mQuestionPicIv.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Utils.startSingleImageActivity(getContext(), mQuestion.getPicInfo());
//            }
//        });
        mTagsTv = (TextView) view.findViewById(R.id.tv_tags);
        mLocationView = (LocationView) view.findViewById(R.id.v_location);
        mTimeTv = (TextView) view.findViewById(R.id.tv_time);
        mAnswerNumTv = (TextView) view.findViewById(R.id.tv_answer_num);
        mAnswerLt = (LinearLayout) view.findViewById(R.id.lt_answer);
        mAnswerDetailTv = (TextView) view.findViewById(R.id.tv_answer_detail);
        mAnswerPicIv = (ImageView) view.findViewById(R.id.iv_answer_pic);
//        mAnswerPicIv.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Utils.startSingleImageActivity(getContext(), mQuestion.getAnswer().getPicInfo());
//            }
//        });
    }

    public void update(Question question) {
        this.mQuestion = question;

        if (mPortraitVisible) {
            mPvPortrait.setVisibility(View.VISIBLE);

            ImageUtils.setAvata(getContext(), mPvPortrait, mQuestion.getUserInfo());
        } else {
            mPvPortrait.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(mQuestion.getTitle())) {
            mNameTv.setText(mQuestion.getUserInfo().getNickname());
        } else {
            mNameTv.setText(mQuestion.getTitle());
        }
        if (!mDetailVisible || TextUtils.isEmpty(mQuestion.getDetail())) {
            mDetailTv.setVisibility(View.GONE);
        } else {
            mDetailTv.setVisibility(View.VISIBLE);

            mDetailTv.setText(mQuestion.getDetail());
        }
        if (mQuestion.getPicInfo() == null) {
            mQuestionPicIv.setVisibility(View.GONE);
        } else {
            mQuestionPicIv.setVisibility(View.VISIBLE);

            ImageUtils.setResizeImage(getContext(), mQuestionPicIv, mQuestion.getPicInfo(),
                    PicType.SMALL);
        }
        mTagsTv.setText(UserUtils.getTags(mQuestion));
        mLocationView.update(mQuestion.getIpaddr(), mQuestion.getAddress());
        mTimeTv.setText(DateUtils.formatDate2(getContext(), new Date(mQuestion.getCreated() * 1000)));
        mAnswerNumTv.setText(String.valueOf(mQuestion.getAnswerNum()));

        if (!mAnswerVisible || mQuestion.getAnswer() == null) {
            mAnswerLt.setVisibility(View.GONE);
        } else {
            mAnswerLt.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(mQuestion.getAnswer().getDetail())) {
                mAnswerDetailTv.setVisibility(View.GONE);
            } else {
                mAnswerDetailTv.setVisibility(View.VISIBLE);
                mAnswerDetailTv.setText(mQuestion.getAnswer().getDetail());
            }

            if (mQuestionPicIv.getVisibility() == View.GONE) {
                if (mQuestion.getAnswer().getPicInfo() == null) {
                    mAnswerPicIv.setVisibility(View.GONE);
                } else {
                    mAnswerPicIv.setVisibility(View.VISIBLE);

                    ImageUtils.setResizeImage(getContext(), mAnswerPicIv, mQuestion.getAnswer()
                            .getPicInfo(), PicType.SMALL);
                }
            } else {
                mAnswerPicIv.setVisibility(View.GONE);
            }
        }
    }

    public void setPortraitVisible(boolean portraitVisible) {
        this.mPortraitVisible = portraitVisible;
    }

    public void setDetailVisible(boolean detailVisible) {
        this.mDetailVisible = detailVisible;
    }

    public void setAnswerVisible(boolean answerVisible) {
        this.mAnswerVisible = answerVisible;
    }
}
