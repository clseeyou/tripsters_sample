package com.tripsters.sample.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tripsters.sample.R;

public class GalleryPhotoView extends FrameLayout {

    public interface OnPhotoClickListener {
        void onPhotoClick(GalleryPhotoView view);
    }

    private FrameLayout mPicLt;

    private OnPhotoClickListener mListener;

    public GalleryPhotoView(Context context) {
        super(context);
        init();
    }

    public GalleryPhotoView(Context context, OnPhotoClickListener listener) {
        this(context);

        this.mListener = listener;
    }

    private void init() {
        int padding = getResources().getDimensionPixelSize(R.dimen.galley_item_padding);
        setPadding(padding, padding, padding, padding);

        View view = View.inflate(getContext(), R.layout.view_gallery_photo, this);
        mPicLt = (FrameLayout) view.findViewById(R.id.lt_pic);
        ViewGroup.LayoutParams params = mPicLt.getLayoutParams();
        params.width = GalleryItemView.getPicSize(getContext()) - 2 * padding;
        params.height = GalleryItemView.getPicSize(getContext()) - 2 * padding;
        mPicLt.setLayoutParams(params);

        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onPhotoClick(GalleryPhotoView.this);
                }
            }
        });
    }
}
