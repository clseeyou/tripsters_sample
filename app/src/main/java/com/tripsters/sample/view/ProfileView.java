package com.tripsters.sample.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.tripsters.android.model.UserInfo;
import com.tripsters.sample.ProfileAnswersActivity;
import com.tripsters.sample.ProfileDetailActivity;
import com.tripsters.sample.ProfileQuestionsActivity;
import com.tripsters.sample.R;
import com.tripsters.sample.ReceivedAnswerListActivity;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.util.UserUtils;

import java.util.Date;

public class ProfileView extends PullDownView {

    public interface UpdateListener {
        void loadData();
    }

    private UserInfo mUserInfo;

    private ProfileItemView mProfileView;
    private ProfileItemView mQuetionsView;
    private ProfileItemView mAnswersView;
    private LinearLayout mReceivedLt;
    private ProfileItemView mReceivedAnswersView;

    private UpdateListener mListener;

    public ProfileView(Context context) {
        super(context);
        init();
    }

    public ProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.view_profile, this);

        mProfileView = (ProfileItemView) view.findViewById(R.id.item_profile);
        mQuetionsView = (ProfileItemView) view.findViewById(R.id.item_questions);
        mAnswersView = (ProfileItemView) view.findViewById(R.id.item_answers);
        mReceivedLt = (LinearLayout) view.findViewById(R.id.lt_received);
        mReceivedAnswersView = (ProfileItemView) view.findViewById(R.id.item_received_answers);
    }

    public void update(UserInfo userInfo) {
        mUserInfo = userInfo;

        if (mUserInfo == null) {
            mProfileView.setVisibility(View.GONE);
        } else {
            mProfileView.setVisibility(View.VISIBLE);

            mProfileView.update(R.drawable.icon_profile,
                    UserUtils.isMyself(mUserInfo.getId()) ? R.string.titlebar_my_profile
                            : R.string.titlebar_other_profile, new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), ProfileDetailActivity.class);
                            intent.putExtra(Constants.Extra.USER, mUserInfo);
                            getContext().startActivity(intent);
                        }
                    });
            mQuetionsView.update(R.drawable.icon_questions,
                    UserUtils.isMyself(mUserInfo.getId()) ? R.string.titlebar_my_questions
                            : R.string.titlebar_other_questions, new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent =
                                    new Intent(getContext(), ProfileQuestionsActivity.class);
                            intent.putExtra(Constants.Extra.USER_ID, mUserInfo.getId());
                            getContext().startActivity(intent);
                        }
                    });
            mAnswersView.update(R.drawable.icon_answers,
                    UserUtils.isMyself(mUserInfo.getId()) ? R.string.titlebar_my_answers
                            : R.string.titlebar_other_answers, new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), ProfileAnswersActivity.class);
                            intent.putExtra(Constants.Extra.USER_ID, mUserInfo.getId());
                            getContext().startActivity(intent);
                        }
                    });
            if (UserUtils.isMyself(mUserInfo.getId())) {
                mReceivedLt.setVisibility(View.VISIBLE);

                mReceivedAnswersView.update(R.drawable.icon_received_answers
                        , R.string.titlebar_received_answers,
                        new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), ReceivedAnswerListActivity.class);
                                getContext().startActivity(intent);
                            }
                        });
            } else {
                mReceivedLt.setVisibility(View.GONE);
            }
        }
    }

    public void setUpdateListener(UpdateListener listener) {
        this.mListener = listener;

        setUpdateHandle(new UpdateHandle() {

            @Override
            public void onUpdate() {
                reload();
            }
        });
    }

    public void reload() {
        if (mListener != null) {
            mListener.loadData();
        }
    }

    public void endLoadSuccess(UserInfo userInfo) {
        mUserInfo = userInfo;

        update(mUserInfo);

        endUpdate(new Date());
        //
        // endLoad();
    }

    public void endLoadFailed() {
        endUpdate(new Date());
        //
        // endLoad();

        ErrorToast.getInstance().showNetError();
    }

    public void endLoadFailed(String message) {
        endUpdate(new Date());
        //
        // endLoad();

        ErrorToast.getInstance().showErrorMessage(message);
    }

    public void firstUpdate() {
        update();
        reload();
    }
}
