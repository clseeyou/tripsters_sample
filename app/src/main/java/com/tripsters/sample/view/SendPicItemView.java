package com.tripsters.sample.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tripsters.android.model.MediaInfo;
import com.tripsters.sample.R;
import com.tripsters.sample.util.ImageUtils;
import com.tripsters.sample.util.Utils;

public class SendPicItemView extends FrameLayout {

    public interface OnPicClickListener {
        void onPicClick(SendPicItemView view, MediaInfo mediaInfo, int position);

        void onPicDel(SendPicItemView view, MediaInfo mediaInfo, int position);
    }

    private static int sPicSize;

    private ImageView mPicIv;
    private ImageView mFirstIv;
    private ImageView mDelIv;

    private OnPicClickListener mListener;

    private MediaInfo mMediaInfo;
    private int mPosition;

    public SendPicItemView(Context context) {
        super(context);
        init();
    }

    public SendPicItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SendPicItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SendPicItemView(Context context, OnPicClickListener listener) {
        this(context);

        this.mListener = listener;
    }

    public void setOnPicClickListener(OnPicClickListener listener) {
        this.mListener = listener;
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.item_send_pic, this);
        mPicIv = (ImageView) view.findViewById(R.id.iv_pic);
        ViewGroup.LayoutParams params = mPicIv.getLayoutParams();
        params.width = getPicSize(getContext());
        params.height = getPicSize(getContext());
        mPicIv.setLayoutParams(params);
        mFirstIv = (ImageView) view.findViewById(R.id.iv_first);
        mDelIv = (ImageView) view.findViewById(R.id.iv_del);
        mDelIv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onPicDel(SendPicItemView.this, mMediaInfo, mPosition);
                }
            }
        });

        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onPicClick(SendPicItemView.this, mMediaInfo, mPosition);
                }
            }
        });
    }

    public void update(MediaInfo mediaInfo, int position) {
        update(mediaInfo, position, true);
    }

    public void update(MediaInfo mediaInfo, int position, boolean showFirst) {
        mMediaInfo = mediaInfo;
        mPosition = position;

        ImageUtils.setImage(getContext(), mPicIv, mMediaInfo, new Rect(0, 0, getPicSize(getContext()),
                getPicSize(getContext())));

        mFirstIv.setVisibility(showFirst && position == 0 ? View.VISIBLE : View.GONE);
    }

    static int getPicSize(Context context) {
        if (sPicSize == 0) {
            return (Utils.getWindowRect(context).widthPixels - 2 * context.getResources()
                    .getDimensionPixelSize(R.dimen.tb_margin)) / 2;
        }

        return sPicSize;
    }
}
