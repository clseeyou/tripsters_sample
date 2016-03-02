package com.tripsters.sample.composer;

import android.content.Context;
import android.content.Intent;

import com.tripsters.android.model.NetResult;
import com.tripsters.android.model.Question;
import com.tripsters.android.task.SendAnswerTask;
import com.tripsters.sample.BaseActivity;
import com.tripsters.sample.R;
import com.tripsters.sample.TripstersApplication;
import com.tripsters.sample.composer.center.ComposerCenter;
import com.tripsters.sample.util.CheckUtils;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.util.IntentUtils;

import java.util.List;

public class SendAnswerComposer extends BaseComposer {

    private Question mQuestion;

    private boolean mTaskRunning;

    @Override
    public void init(Context context, Intent intent) {
        List<BaseComposer> composers =
                ComposerCenter.getInstance().getDrafts(TripstersApplication.mContext, type, uid);
        if (!composers.isEmpty()) {
            content = composers.get(0).getContent();
        }

        mQuestion = intent.getParcelableExtra(Constants.Extra.QUESTION);
    }

    @Override
    public boolean showAddCities() {
        return false;
    }

    @Override
    public String getTitle(Context context) {
        return context.getString(R.string.titlebar_send_answer);
    }

    @Override
    public String getHint(Context context) {
        return context.getString(R.string.hint_send_answer_detail);
    }

    @Override
    public int getMaxLenth() {
        return -1;
    }

    public boolean isToolbarVisiable() {
        return true;
    }

    @Override
    public boolean isTagVisiable() {
        return false;
    }

    @Override
    public boolean isBlogVisiable() {
        return true;
    }

    @Override
    public boolean isPoiVisiable() {
        return true;
    }

    @Override
    public boolean isCityEnabled() {
        return false;
    }

    @Override
    public boolean isLocationVisiable() {
        return false;
    }

    @Override
    public void send(final Context context) {
        if (!checkValid(context)) {
            return;
        }

        if (mTaskRunning) {
            return;
        }

        saveDraft();

        mTaskRunning = true;
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).showProgress();
        }

        new SendAnswerTask(TripstersApplication.mContext, uid, getSendContent(context),
                getPicPath(), mQuestion.getId(), new SendAnswerTask.SendAnswerTaskResult() {

            @Override
            public void onTaskResult(NetResult result) {
                mTaskRunning = false;
                if (context instanceof BaseActivity) {
                    ((BaseActivity) context).dismissProgress();
                }

                if (ErrorToast.getInstance().checkNetResult(result)) {
                    IntentUtils.sendAnswerBroadcast(TripstersApplication.mContext, uid,
                            mQuestion.getId());

                    removeDraft();
                }
            }
        }).execute();

        if (context instanceof BaseActivity) {
            ((BaseActivity) context).finish();
        }
    }

    @Override
    protected boolean checkValid(Context context) {
        if (!CheckUtils.checkAnswerTitleValid(content)) {
            return false;
        }

        return super.checkValid(context);
    }

    @Override
    public Question getQuestion() {
        return mQuestion;
    }
}
