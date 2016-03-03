package com.tripsters.sample.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripsters.android.model.Answer;
import com.tripsters.android.model.PicInfo.PicType;
import com.tripsters.android.model.Question;
import com.tripsters.sample.R;
import com.tripsters.sample.SendActivity;
import com.tripsters.sample.composer.BaseComposer.ComposerType;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ImageUtils;

public class AnswerItemView extends LinearLayout {

    private boolean mPortraitVisible = true;
    private boolean mReplyVisible = false;
    private Answer mAnswer;

    private FrameLayout mPortraitTopLt;
    private PortraitTopView mPortraitLt;
    private TextView mReplyTv;
    private TextView mAnswerTv;
    private ImageView mAnswerPicIv;
    private DataBottomView mQuestionLt;

    public AnswerItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.bg_item);

        View view = View.inflate(context, R.layout.item_answer, this);
        mPortraitTopLt = (FrameLayout) view.findViewById(R.id.lt_portrait_top);
        mPortraitLt = (PortraitTopView) view.findViewById(R.id.lt_portrait);
        mReplyTv = (TextView) view.findViewById(R.id.tv_reply);
        mReplyTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SendActivity.class);
                intent.putExtra(Constants.Extra.COMPOSER_TYPE,
                        ComposerType.SEND_REANSWER.getValue());
                intent.putExtra(Constants.Extra.ANSWER, mAnswer);
                getContext().startActivity(intent);
            }
        });
        mAnswerTv = (TextView) view.findViewById(R.id.tv_answer);
        mAnswerPicIv = (ImageView) view.findViewById(R.id.iv_answer_pic);
//        mAnswerPicIv.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Utils.startSingleImageActivity(getContext(), mAnswer.getPicInfo());
//            }
//        });
        mQuestionLt = (DataBottomView) view.findViewById(R.id.lt_question);
    }

    public void update(Answer answer) {
        this.mAnswer = answer;

        if (mPortraitVisible) {
            mPortraitTopLt.setVisibility(View.VISIBLE);
            mPortraitLt.update(mAnswer.getUserInfo(), mAnswer.getCreated());

            if (mReplyVisible) {
                mReplyTv.setVisibility(View.VISIBLE);
            } else {
                mReplyTv.setVisibility(View.GONE);
            }
        } else {
            mPortraitTopLt.setVisibility(View.GONE);
        }

        mAnswerTv.setText(mAnswer.getDetail());
        if (mAnswer.getPicInfo() == null) {
            mAnswerPicIv.setVisibility(View.GONE);
        } else {
            mAnswerPicIv.setVisibility(View.VISIBLE);

            ImageUtils.setResizeImage(getContext(), mAnswerPicIv, mAnswer.getPicInfo(), PicType.SMALL);
        }

        Question question = mAnswer.getQuestion();

        if (question == null) {
            mQuestionLt.setVisibility(View.GONE);
        } else {
            mQuestionLt.setVisibility(View.VISIBLE);

            mQuestionLt.update(question.getPicInfo(), question.getUserInfo(), question.getTitle());
        }
    }

    public void setPortraitVisible(boolean portraitVisible) {
        this.mPortraitVisible = portraitVisible;
    }

    public void setReplyVisible(boolean replyVisible) {
        this.mReplyVisible = replyVisible;
    }
}
