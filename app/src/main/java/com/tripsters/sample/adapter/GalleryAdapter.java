package com.tripsters.sample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tripsters.android.model.MediaInfo;
import com.tripsters.sample.view.GalleryItemView;
import com.tripsters.sample.view.GalleryItemView.OnPicClickListener;
import com.tripsters.sample.view.GalleryPhotoView;
import com.tripsters.sample.view.GalleryPhotoView.OnPhotoClickListener;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends BaseAdapter {

    public interface GalleryListener {
        void onCameraClicked();

        void onPicClicked(MediaInfo mediaInfo, int position);

        void onPicSelected(MediaInfo mediaInfo, boolean selected, int position);
    }

    private Context mContext;
    private List<MediaInfo> mImageList = new ArrayList<MediaInfo>();
    private List<MediaInfo> mSelectedImageList = new ArrayList<MediaInfo>();
    private boolean mSingleType;
    private GalleryListener mListener;

    // private boolean mSingleMode;

    public GalleryAdapter(Context context, boolean singleType, GalleryListener listener) {
        this.mContext = context;
        this.mSingleType = singleType;
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return mImageList.size() + 1;
    }

    @Override
    public MediaInfo getItem(int position) {
        if (position == 0) {
            return null;
        }

        return mImageList.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }

        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            GalleryPhotoView view;

            if (convertView == null) {
                view = new GalleryPhotoView(mContext, new OnPhotoClickListener() {

                    @Override
                    public void onPhotoClick(GalleryPhotoView view) {
                        if (mListener != null) {
                            mListener.onCameraClicked();
                        }
                    }
                });
            } else {
                view = (GalleryPhotoView) convertView;
            }

            return view;
        }

        GalleryItemView view;

        if (convertView == null) {
            view = new GalleryItemView(mContext, mSingleType, new OnPicClickListener() {

                @Override
                public void onPicSelected(GalleryItemView view, MediaInfo mediaInfo,
                                          boolean selected, int position) {
                    if (mListener != null) {
                        mListener.onPicSelected(mediaInfo, selected, position);
                    }
                }

                @Override
                public void onPicClick(GalleryItemView view, MediaInfo mediaInfo, int position) {
                    if (mListener != null) {
                        mListener.onPicClicked(mediaInfo, position);
                    }
                }
            });
        } else {
            view = (GalleryItemView) convertView;
        }

        MediaInfo mediaInfo = getItem(position);

        view.update(mediaInfo, mSelectedImageList.contains(mediaInfo), position - 1);

        return view;
    }

    public void notifyData(List<MediaInfo> mediaInfos, List<MediaInfo> selectedMediaInfos) {
        mImageList.clear();
        mImageList.addAll(mediaInfos);
        mSelectedImageList.clear();
        mSelectedImageList.addAll(selectedMediaInfos);
        notifyDataSetChanged();
    }
}
