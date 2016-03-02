package com.tripsters.sample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.tripsters.android.model.Answer;
import com.tripsters.android.model.Question;
import com.tripsters.sample.view.AnswerResultItemView;

/**
 * 问题详情页回答列表
 *
 * @author chenli
 */
public class AnswerResultListAdapter extends TAdapter<Answer> {

    private Context mContext;
    private Question mQuestion;

    public AnswerResultListAdapter(Context context, Question question) {
        this.mContext = context;
        this.mQuestion = question;
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
        AnswerResultItemView itemView;

        if (convertView == null) {
            itemView = new AnswerResultItemView(mContext);
        } else {
            itemView = (AnswerResultItemView) convertView;
        }

        Answer answer = getItem(position);

        if (answer.getQuestion() == null) {
            answer.setQuestion(mQuestion);
        }

        itemView.update(answer);

        return itemView;
    }
}
