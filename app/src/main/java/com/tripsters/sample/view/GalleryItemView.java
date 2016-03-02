package com.tripsters.sample.view;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tripsters.android.model.MediaInfo;
import com.tripsters.sample.R;
import com.tripsters.sample.util.ImageUtils;
import com.tripsters.sample.util.Utils;

public class GalleryItemView extends FrameLayout {

    public interface OnPicClickListener {
        void onPicClick(GalleryItemView view, MediaInfo mediaInfo, int position);

        void onPicSelected(GalleryItemView view, MediaInfo mediaInfo, boolean selected,
                                  int position);
    }

    private static int sPicSize;

    private ImageView mPicIv;
    private ImageView mCheckIv;

    private boolean mSingleType;
    private OnPicClickListener mListener;

    private MediaInfo mMediaInfo;
    private boolean mSelected;
    private int mPosition;

    public GalleryItemView(Context context) {
        this(context, false, null);
    }

    public GalleryItemView(Context context, boolean singleType, OnPicClickListener listener) {
        super(context);

        this.mSingleType = singleType;
        this.mListener = listener;

        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.item_gallery, this);
        mPicIv = (ImageView) view.findViewById(R.id.iv_pic);
        ViewGroup.LayoutParams params = mPicIv.getLayoutParams();
        params.width = getPicSize(getContext());
        params.height = getPicSize(getContext());
        mPicIv.setLayoutParams(params);
        mCheckIv = (ImageView) view.findViewById(R.id.iv_check);
        if (mSingleType) {
            mCheckIv.setVisibility(View.GONE);
        } else {
            mCheckIv.setVisibility(View.VISIBLE);
            mCheckIv.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onPicSelected(GalleryItemView.this, mMediaInfo, mSelected,
                                mPosition);
                    }
                }
            });
        }

        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onPicClick(GalleryItemView.this, mMediaInfo, mPosition);
                }
            }
        });
    }

    public void update(MediaInfo mediaInfo, boolean selected, int position) {
        mMediaInfo = mediaInfo;
        mSelected = selected;
        mPosition = position;

        ImageUtils.setImage(getContext(), mPicIv, mMediaInfo, new Rect(0, 0, getPicSize(getContext()),
                getPicSize(getContext())), R.drawable.image_default);
        mPicIv.setAlpha(mSelected ? 0.8f : 1);
        mCheckIv.setImageResource(mSelected ? R.drawable.icon_gallery_selector_pressed
                : R.drawable.icon_gallery_selector_normal);
    }

    static int getPicSize(Context context) {
        if (sPicSize == 0) {
            return Utils.getWindowRect(context).widthPixels / 3;
        }

        return sPicSize;
    }
}
