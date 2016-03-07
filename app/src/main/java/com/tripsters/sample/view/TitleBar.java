package com.tripsters.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripsters.sample.R;

public class TitleBar extends FrameLayout {
    public enum LeftType {
        NONE, ICON_BACK, TEXT_CANCEL
    }

    public enum RightType {
        NONE, TEXT_DONE, TEXT_PUBLISH, TEXT_JUMP, ICON_SEND_QUESTION
    }

    public interface OnTitleBarClicklistener {
        void onLeftClick(View view);

        void onRightClick(View view);
    }

    private LinearLayout mLeftLt;
    private TextView mLeftTv;
    private ImageView mLeftIv;
    private ImageView mLeftArrowIv;
    private LinearLayout mTitleLt;
    private TextView mTitleTv;
    private LinearLayout mTitleDoubleLt;
    private TextView mTitleLeftTv;
    private TextView mTitleRightTv;
    private ImageView mTitleArrowIv;
    private FrameLayout mRightLt;
    private TextView mRightTv;
    private ImageView mRightIv;

    public TitleBar(Context context) {
        super(context);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.view_title_bar, this);

        mLeftLt = (LinearLayout) view.findViewById(R.id.lt_left);
        mLeftTv = (TextView) view.findViewById(R.id.tv_left);
        mLeftIv = (ImageView) view.findViewById(R.id.iv_left);
        mLeftArrowIv = (ImageView) view.findViewById(R.id.iv_left_arrow);
        mLeftLt.setVisibility(View.GONE);
        mLeftArrowIv.setVisibility(View.GONE);

        mTitleLt = (LinearLayout) view.findViewById(R.id.lt_title);
        mTitleTv = (TextView) view.findViewById(R.id.tv_title);
        mTitleArrowIv = (ImageView) view.findViewById(R.id.iv_title_arrow);
        mTitleArrowIv.setVisibility(View.GONE);

        mTitleDoubleLt = (LinearLayout) view.findViewById(R.id.lt_title_double);
        mTitleLeftTv = (TextView) view.findViewById(R.id.tv_title_left);
        mTitleRightTv = (TextView) view.findViewById(R.id.tv_title_right);

        mRightLt = (FrameLayout) view.findViewById(R.id.lt_right);
        mRightTv = (TextView) view.findViewById(R.id.tv_right);
        mRightIv = (ImageView) view.findViewById(R.id.iv_right);
        mRightLt.setVisibility(View.GONE);
    }

    public void initTitleBar(LeftType left, int titleResId, RightType right) {
        setButtons(left, right);
        setTitle(getResources().getString(titleResId));
    }

    public void initTitleBar(LeftType left, String title, RightType right) {
        setButtons(left, right);
        setTitle(title);
    }

    public void initTitleBar(LeftType left, int titleLeftResId, int titleRightResId, RightType right) {
        setButtons(left, right);

        setTitle(getResources().getString(titleLeftResId), getResources()
                .getString(titleRightResId));
    }

    public void initTitleBar(LeftType left, String titleLeft, String titleRight, RightType right) {
        setButtons(left, right);

        setTitle(titleLeft, titleRight);
    }

    private void setButtons(LeftType left, RightType right) {
        switch (left) {
            case NONE:
                mLeftLt.setVisibility(View.GONE);
                break;
            case ICON_BACK:
                mLeftLt.setVisibility(View.VISIBLE);
                mLeftTv.setVisibility(View.GONE);
                mLeftIv.setVisibility(View.VISIBLE);

                mLeftIv.setImageResource(R.drawable.title_back);
                break;
            case TEXT_CANCEL:
                mLeftLt.setVisibility(View.VISIBLE);
                mLeftTv.setVisibility(View.VISIBLE);
                mLeftIv.setVisibility(View.GONE);

                mLeftTv.setText(R.string.cancel);
                break;
            default:
                mLeftLt.setVisibility(View.GONE);
                break;
        }

        switch (right) {
            case NONE:
                mRightLt.setVisibility(View.GONE);
                break;
            case TEXT_DONE:
                mRightLt.setVisibility(View.VISIBLE);
                mRightTv.setVisibility(View.VISIBLE);
                mRightIv.setVisibility(View.GONE);

                mRightTv.setText(R.string.done);
                break;
            case TEXT_PUBLISH:
                mRightLt.setVisibility(View.VISIBLE);
                mRightTv.setVisibility(View.VISIBLE);
                mRightIv.setVisibility(View.GONE);

                mRightTv.setText(R.string.publish);
                break;
            case TEXT_JUMP:
                mRightLt.setVisibility(View.VISIBLE);
                mRightTv.setVisibility(View.VISIBLE);
                mRightIv.setVisibility(View.GONE);

                mRightTv.setText(R.string.jump);
                break;
            case ICON_SEND_QUESTION:
                mRightLt.setVisibility(View.VISIBLE);
                mRightTv.setVisibility(View.GONE);
                mRightIv.setVisibility(View.VISIBLE);

                mRightIv.setImageResource(R.drawable.title_send_question);
                break;
            default:
                mRightLt.setVisibility(View.GONE);
                break;
        }
    }

    public void setTitle(String title) {
        mTitleLt.setVisibility(View.VISIBLE);
        mTitleTv.setText(title);
        mTitleDoubleLt.setVisibility(View.GONE);
    }

    public void setTitle(String titleLeft, String titleRight) {
        mTitleLt.setVisibility(View.GONE);
        mTitleDoubleLt.setVisibility(View.VISIBLE);
        mTitleLeftTv.setText(titleLeft);
        mTitleRightTv.setText(titleRight);
    }

    public void setTitleClick(OnClickListener listener) {
        mTitleArrowIv.setVisibility(listener == null ? View.GONE : View.VISIBLE);
        mTitleLt.setOnClickListener(listener);
    }

    public void setTitleLeftClick(OnClickListener listener) {
        mTitleLeftTv.setOnClickListener(listener);
    }

    public void setTitleRightClick(OnClickListener listener) {
        mTitleRightTv.setOnClickListener(listener);
    }

    public void setTitleLeftSelected(boolean selected) {
        mTitleLeftTv.setSelected(selected);
    }

    public void setTitleRightSelected(boolean selected) {
        mTitleRightTv.setSelected(selected);
    }

    public void setLeftClick(OnClickListener listener) {
        mLeftLt.setOnClickListener(listener);
    }

    public void setRightClick(OnClickListener listener) {
        mRightLt.setOnClickListener(listener);
    }

    public void setLeftVisible(boolean visible) {
        mLeftLt.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setLeftArrowVisible(boolean visible) {
        mLeftArrowIv.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightVisible(boolean visible) {
        mRightLt.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightClickable(boolean clickable) {
        mRightLt.setClickable(clickable);
    }

    public String getLeftText() {
        return mLeftTv.getText().toString();
    }

    public void setLeftText(String text) {
        mLeftLt.setVisibility(View.VISIBLE);
        mLeftTv.setVisibility(View.VISIBLE);
        mLeftIv.setVisibility(View.GONE);

        mLeftTv.setText(text);
    }

    public void setRightText(String text) {
        mRightLt.setVisibility(View.VISIBLE);
        mRightTv.setVisibility(View.VISIBLE);
        mRightIv.setVisibility(View.GONE);

        mRightTv.setText(text);
    }
}
