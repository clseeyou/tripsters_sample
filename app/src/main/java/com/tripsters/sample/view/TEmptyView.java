package com.tripsters.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripsters.sample.R;

public class TEmptyView extends FrameLayout {

    private ImageView mEmptySimpleIv;
    private TextView mEmptySimpleTv;

    public enum Type {
        SEARCH_QUESTION(-1), DEFULT(R.string.empty_default), QUESTION_DETAIL(
                R.string.empty_question_detail), TRIPS(R.string.empty_trips), QUESTIONS(
                R.string.empty_questions), ANSWERS(R.string.empty_answers), BLOGS(
                R.string.empty_blogs), COMMENTS(R.string.empty_comments), GROUPONS(
                R.string.empty_groupons), FAV_QUESTIONS(R.string.empty_fav_questions), FAV_BLOGS(
                R.string.empty_fav_blogs), GROUPS(R.string.empty_groups), POIS(R.string.empty_pois), FRIENDS(
                R.string.empty_friends), FANS(R.string.empty_fans), FAV_USERS(
                R.string.empty_fav_users), GROUP_MEMBERS(R.string.empty_group_members), SYSTEM_MESSAGES(
                R.string.empty_system_messages), DRAFTS(R.string.empty_drafts), SEARCH(
                R.string.empty_search);

        final int textResId;

        Type(int textResId) {
            this.textResId = textResId;
        }
    }

    public TEmptyView(Context context) {
        super(context);

        initView();
    }

    public TEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public TEmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_empty, this);
        mEmptySimpleIv = (ImageView) view.findViewById(R.id.iv_empty_simple);
        mEmptySimpleTv = (TextView) view.findViewById(R.id.tv_empty_simple);

        setType(Type.DEFULT);
    }

    public void setType(Type type) {
        setBackgroundColor(0x00ffffff);

        if (type == Type.QUESTION_DETAIL) {
            mEmptySimpleIv.setImageResource(R.drawable.empty_question_detail);
        } else {
            mEmptySimpleIv.setImageResource(R.drawable.empty_default);
        }
        mEmptySimpleTv.setText(type.textResId);
    }
}
