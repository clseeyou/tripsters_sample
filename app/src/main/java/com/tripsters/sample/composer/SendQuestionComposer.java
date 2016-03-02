package com.tripsters.sample.composer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.text.TextUtils;

import com.tripsters.android.model.NetResult;
import com.tripsters.android.task.SendQuestionTask;
import com.tripsters.sample.BaseActivity;
import com.tripsters.sample.CityListActivity;
import com.tripsters.sample.R;
import com.tripsters.sample.SendActivity;
import com.tripsters.sample.TripstersApplication;
import com.tripsters.sample.composer.center.ComposerCenter;
import com.tripsters.sample.util.CheckUtils;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.util.IntentUtils;

import java.util.List;

public class SendQuestionComposer extends BaseComposer {

    private String lat;
    private String lng;
    private String address;

    private boolean mTaskRunning;

    @Override
    public void init(Context context, Intent intent) {
        String text = intent.getStringExtra(Constants.Extra.TEXT);

        if (TextUtils.isEmpty(text)) {
            List<BaseComposer> composers =
                    ComposerCenter.getInstance()
                            .getDrafts(TripstersApplication.mContext, type, uid);

            if (!composers.isEmpty()) {
                content = composers.get(0).getContent();
            }
        } else {
            content = text;
        }
    }

    @Override
    public boolean showAddCities() {
        return true;
    }

    @Override
    public String getTitle(Context context) {
        return context.getString(R.string.titlebar_send_question);
    }

    @Override
    public String getHint(Context context) {
        return context.getString(R.string.hint_send_question_title);
    }

    @Override
    public int getMaxLenth() {
        return Constants.Edit.MAX_QUESTION_TITLE_SIZE;
    }

    public boolean isToolbarVisiable() {
        return true;
    }

    @Override
    public boolean isTagVisiable() {
        return true;
    }

    @Override
    public boolean isBlogVisiable() {
        return false;
    }

    @Override
    public boolean isPoiVisiable() {
        return false;
    }

    @Override
    public boolean isCityEnabled() {
        return true;
    }

    @Override
    public boolean isLocationVisiable() {
        return true;
    }

    @Override
    public void send(final Context context) {
        send(context, true);
    }

    public void send(final Context context, final boolean showResult) {
        saveDraft();

        mTaskRunning = true;
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).showProgress();
        }

        new SendQuestionTask(TripstersApplication.mContext, uid, getSendContent(context),
                getPicPath(), country.getCountryNameCn(), getCitiesString(cities), lat, lng, address,
                new SendQuestionTask.SendQuestionTaskResult() {

                    @Override
                    public void onTaskResult(NetResult result) {
                        mTaskRunning = false;
                        if (context instanceof BaseActivity) {
                            ((BaseActivity) context).dismissProgress();
                        }

                        if (ErrorToast.getInstance().checkNetResult(result)) {
                            IntentUtils.sendQuestionBroadcast(TripstersApplication.mContext, uid);

                            removeDraft();

                            if (context instanceof BaseActivity) {
                                ((BaseActivity) context).setResult(Activity.RESULT_OK);
                                ((BaseActivity) context).finish();
                            }
                        }
                    }
                }).execute();
    }

    @Override
    public void addCities(BaseActivity activity) {
        if (!checkValid(activity)) {
            return;
        }

        Intent intent = new Intent(activity, CityListActivity.class);
        intent.putExtra(Constants.Extra.MAX_COUNT, Constants.QUESTION_CITY_MAX_COUNT);
        intent.putExtra(Constants.Extra.COMPOSER, this);
        // intent.putExtra(Constants.Extra.COUNTRY, mComposer.getCountry());
        // intent.putParcelableArrayListExtra(Constants.Extra.CITIES, mComposer.getCities());
        activity.startActivityForResult(intent, SendActivity.REQUEST_ADD_CITIES);
    }

    @Override
    public void sendWithCities(BaseActivity activity) {
        if (cities.isEmpty()) {
            ErrorToast.getInstance().showErrorMessage(R.string.question_city_select_empty);
            return;
        }

        if (mTaskRunning) {
            return;
        }

        if (super.checkValid(activity)) {
            send(activity);
        }
    }

    @Override
    protected boolean checkValid(Context context) {
        if (!CheckUtils.checkQuestionTitleValid(content, false)) {
            return false;
        }

        return super.checkValid(context);
    }

    @Override
    protected void read(Parcel source) {
        super.read(source);

        lat = source.readString();
        lng = source.readString();
        address = source.readString();
    }

    @Override
    protected void write(Parcel dest, int flags) {
        super.write(dest, flags);

        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(address);
    }

    @Override
    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }
}
