package com.tripsters.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.tripsters.android.model.Question;
import com.tripsters.sample.R;

public class AnswerNumView extends TextView {

    public AnswerNumView(Context context) {
        super(context);
        init();
    }

    public AnswerNumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnswerNumView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setBackgroundColor(getResources().getColor(R.color.tb_bg_white));
        setGravity(Gravity.CENTER);
        setMinHeight(getResources().getDimensionPixelSize(R.dimen.question_detail_answer_num_heigh));
        setTextColor(getResources().getColor(R.color.tb_light_grey));
        setTextSize(14);
    }

    public void update(Question question) {
        if (question.getAnswerNum() == 0) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
        }

        setText(getResources().getString(R.string.question_detail_answernum,
                question.getAnswerNum()));
    }
}
