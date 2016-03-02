package com.tripsters.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tripsters.sample.R;

public class TLoadMoreView extends LinearLayout {

    public interface OnLoadMoreListener {
        public void loadMore();
    }

    public static final int TYPE_LOADING = 0;
    public static final int TYPE_LOADMORE = 1;
    public static final int TYPE_NOMORE = 2;

    private TextView mTvLoadMore;
    private ProgressBar mPbLoading;

    private OnLoadMoreListener mListener;

    private int mType;

    public TLoadMoreView(Context context) {
        super(context);
        init(null);
    }

    public TLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(null);
    }

    public TLoadMoreView(Context context, OnLoadMoreListener listener) {
        super(context);
        init(listener);
    }

    private void init(OnLoadMoreListener listener) {
        mListener = listener;

        LayoutInflater.from(getContext()).inflate(R.layout.view_tb_loadmore, this);

        mTvLoadMore = (TextView) findViewById(R.id.load_more);
        mPbLoading = (ProgressBar) findViewById(R.id.loading);

        if (mListener != null) {
            setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mType == TYPE_LOADMORE) {
                        mListener.loadMore();
                    }
                }
            });
        }
    }

    public void setText(String text) {
        mTvLoadMore.setText(text);
    }

    public void load(int type) {
        this.mType = type;

        if (mType == TYPE_LOADING) {
            mTvLoadMore.setVisibility(View.GONE);
            mPbLoading.setVisibility(View.VISIBLE);
        } else if (mType == TYPE_LOADMORE) {
            mTvLoadMore.setVisibility(View.VISIBLE);
            mPbLoading.setVisibility(View.GONE);

            mTvLoadMore.setText(R.string.load_more);
        } else {
            mTvLoadMore.setVisibility(View.VISIBLE);
            mPbLoading.setVisibility(View.GONE);

            mTvLoadMore.setText(R.string.no_more);
        }
    }

    public int getType() {
        return mType;
    }
}
