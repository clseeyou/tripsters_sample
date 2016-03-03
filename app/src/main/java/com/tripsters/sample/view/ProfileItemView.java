package com.tripsters.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tripsters.sample.R;

public class ProfileItemView extends RelativeLayout {

    private ImageView mIconIv;
    private TextView mTitleTv;

    public ProfileItemView(Context context) {
        super(context);
        init(context);
    }

    public ProfileItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProfileItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setBackgroundResource(R.drawable.bg_item);

        View view = View.inflate(context, R.layout.item_profile, this);
        mIconIv = (ImageView) view.findViewById(R.id.iv_icon);
        mTitleTv = (TextView) view.findViewById(R.id.tv_title);
    }

    public void update(int iconResId, int titleResId, OnClickListener listener) {
        mIconIv.setImageResource(iconResId);
        mTitleTv.setText(titleResId);
        setOnClickListener(listener);
    }
}
