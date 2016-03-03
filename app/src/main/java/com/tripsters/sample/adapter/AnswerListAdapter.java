package com.tripsters.sample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.tripsters.android.model.Answer;
import com.tripsters.sample.view.AnswerItemView;

public class AnswerListAdapter extends TAdapter<Answer> {

    private Context mContext;
    private boolean mPortraitVisible = true;
    private boolean mReplyVisible = false;

    public AnswerListAdapter(Context context) {
        this(context, true, false);
    }

    public AnswerListAdapter(Context context, boolean portraitVisible) {
        this(context, portraitVisible, false);
    }

    public AnswerListAdapter(Context context, boolean portraitVisible, boolean replyVisible) {
        this.mContext = context;
        this.mPortraitVisible = portraitVisible;
        this.mReplyVisible = replyVisible;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }

        return mList.size();
    }

    @Override
    public Answer getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AnswerItemView itemView;
        if (convertView == null) {
            itemView = new AnswerItemView(mContext);
            itemView.setPortraitVisible(mPortraitVisible);
            itemView.setReplyVisible(mReplyVisible);
        } else {
            itemView = (AnswerItemView) convertView;
        }

        itemView.update(getItem(position));

        return itemView;
    }

}
