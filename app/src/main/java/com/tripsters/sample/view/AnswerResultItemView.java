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
import com.tripsters.sample.R;
import com.tripsters.sample.SendActivity;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.DateUtils;
import com.tripsters.sample.util.ImageUtils;

import java.util.Date;

public class AnswerResultItemView extends LinearLayout {

    private Answer mAnswer;

    private PortraitTopView mPortraitLt;
    private TextView mDetailTv;
    private ImageView mPicIv;
    private TextView mTimeBottomTv;
    private FrameLayout mQuestionLt;

    public AnswerResultItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.bg_item);

        View view = View.inflate(context, R.layout.item_answer_result, this);
        mPortraitLt = (PortraitTopView) view.findViewById(R.id.lt_portrait);
        mPortraitLt.setVerifyVisible(true);
        mPortraitLt.setTimeVisible(false);
        mDetailTv = (TextView) view.findViewById(R.id.tv_detail);
        mPicIv = (ImageView) view.findViewById(R.id.iv_pic);
//        mPicIv.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Utils.startSingleImageActivity(getContext(), mAnswer.getPicInfo());
//            }
//        });
        mTimeBottomTv = (TextView) view.findViewById(R.id.tv_time_bottom);
        mQuestionLt = (FrameLayout) view.findViewById(R.id.lt_question);
        mQuestionLt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SendActivity.class);
//                intent.putExtra(Constants.Extra.COMPOSER_TYPE,
//                        ComposerType.SEND_REANSWER.getValue());
                intent.putExtra(Constants.Extra.ANSWER, mAnswer);
                getContext().startActivity(intent);
            }
        });
    }

    public void update(Answer answer) {
        this.mAnswer = answer;

        mPortraitLt.update(mAnswer.getUserInfo(), mAnswer.getCreated());
        mDetailTv.setText(mAnswer.getDetail());
        ImageUtils.setResizeImage(getContext(), mPicIv, mAnswer.getPicInfo(), PicType.MIDDLE);
        mTimeBottomTv
                .setText(DateUtils.formatDate2(getContext(), new Date(mAnswer.getCreated() * 1000)));
    }
}
