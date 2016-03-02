package com.tripsters.sample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.tripsters.android.model.Question;
import com.tripsters.sample.view.QuestionItemView;

public class QuestionListAdapter extends TAdapter<Question> {

    private Context mContext;

    public QuestionListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }

        return mList.size();
    }

    @Override
    public Question getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuestionItemView itemView;

        if (convertView == null) {
            itemView = new QuestionItemView(mContext);
            itemView.setPortraitVisible(true);
            itemView.setDetailVisible(false);
            itemView.setAnswerVisible(true);
        } else {
            itemView = (QuestionItemView) convertView;
        }

        itemView.update(getItem(position));

        return itemView;
    }
}
