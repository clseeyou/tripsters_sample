package com.tripsters.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripsters.sample.R;

public class CustomToastView extends LinearLayout {

    private TextView mToastTv;

    public CustomToastView(Context context) {
        super(context);
        init();
    }

    public CustomToastView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomToastView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setBackgroundResource(R.drawable.bg_toast);

        View view = View.inflate(getContext(), R.layout.view_custom_toast, this);
        mToastTv = (TextView) view.findViewById(R.id.tv_toast);
    }

    public void update(int toastResId) {
        mToastTv.setText(toastResId);
    }
}
