package com.tripsters.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripsters.sample.R;

public class TEmptyView extends FrameLayout {

    private ImageView mEmptySimpleIv;
    private TextView mEmptySimpleTv;

    public enum Type {
        DEFULT(R.string.empty_default), QUESTION_DETAIL(
                R.string.empty_question_detail), QUESTIONS(
                R.string.empty_questions), ANSWERS(R.string.empty_answers);

        final int textResId;

        Type(int textResId) {
            this.textResId = textResId;
        }
    }

    public TEmptyView(Context context) {
        super(context);

        initView();
    }

    public TEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public TEmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_empty, this);
        mEmptySimpleIv = (ImageView) view.findViewById(R.id.iv_empty_simple);
        mEmptySimpleTv = (TextView) view.findViewById(R.id.tv_empty_simple);

        setType(Type.DEFULT);
    }

    public void setType(Type type) {
        setBackgroundColor(0x00ffffff);

        if (type == Type.QUESTION_DETAIL) {
            mEmptySimpleIv.setImageResource(R.drawable.empty_question_detail);
        } else {
            mEmptySimpleIv.setImageResource(R.drawable.empty_default);
        }
        mEmptySimpleTv.setText(type.textResId);
    }
}
