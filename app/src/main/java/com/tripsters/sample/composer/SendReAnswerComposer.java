package com.tripsters.sample.composer;

import android.content.Context;
import android.content.Intent;

import com.tripsters.android.model.Answer;
import com.tripsters.android.model.NetResult;
import com.tripsters.android.model.Question;
import com.tripsters.android.task.SendReAnswerTask;
import com.tripsters.sample.BaseActivity;
import com.tripsters.sample.R;
import com.tripsters.sample.TripstersApplication;
import com.tripsters.sample.util.CheckUtils;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.util.IntentUtils;

public class SendReAnswerComposer extends BaseComposer {

    private Answer mAnswer;

    private boolean mTaskRunning;

    @Override
    public void init(Context context, Intent intent) {
        // List<BaseComposer> composers =
        // ComposerCenter.getInstance().getDrafts(TripstersApplication.mContext, type);;
        // if (!composers.isEmpty()) {
        // content = composers.get(0).getContent();
        // }
        //
        mAnswer = intent.getParcelableExtra(Constants.Extra.ANSWER);
    }

    @Override
    public boolean showAddCities() {
        return false;
    }

    @Override
    public String getTitle(Context context) {
        return context.getString(R.string.titlebar_send_reanswer);
    }

    @Override
    public String getHint(Context context) {
        return context.getString(R.string.hint_send_reanswer_detail, mAnswer.getUserInfo()
                .getNickname());
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
    public String getSendContent(Context context) {
        return context.getString(R.string.answer_content, mAnswer.getUserInfo().getNickname(),
                getContent());
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

        new SendReAnswerTask(TripstersApplication.mContext, uid, getSendContent(context),
                getPicPath(), mAnswer.getQuestion().getId(), mAnswer.getUserInfo().getId(),
                new SendReAnswerTask.SendReAnswerTaskResult() {

                    @Override
                    public void onTaskResult(NetResult result) {
                        mTaskRunning = false;
                        if (context instanceof BaseActivity) {
                            ((BaseActivity) context).dismissProgress();
                        }

                        if (ErrorToast.getInstance().checkNetResult(result)) {
                            IntentUtils.sendAnswerBroadcast(TripstersApplication.mContext, uid, mAnswer
                                    .getQuestion().getId());

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
    protected void saveDraft() {
        // do nothing
    }

    @Override
    protected void removeDraft() {
        // do nothing
    }

    @Override
    public Question getQuestion() {
        return mAnswer.getQuestion();
    }
}
