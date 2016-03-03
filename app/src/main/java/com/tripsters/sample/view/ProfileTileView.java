package com.tripsters.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripsters.sample.R;

public class ProfileTileView extends LinearLayout {

    public interface OnTileClickListener {
        void onTileClick(View view);
    }

    private TextView mCountTv;
    private TextView mTitleTv;

    public ProfileTileView(Context context) {
        super(context);
        init();
    }

    public ProfileTileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProfileTileView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);

        View view = View.inflate(getContext(), R.layout.view_profile_tile, this);
        mCountTv = (TextView) view.findViewById(R.id.tv_count);
        mTitleTv = (TextView) view.findViewById(R.id.tv_title);
    }

    public void setTitle(String title) {
        mTitleTv.setText(title);
    }

    public void setCount(String count) {
        mCountTv.setText(count);
    }
}
