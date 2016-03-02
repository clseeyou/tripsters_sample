package com.tripsters.sample;

import android.app.Activity;

import com.tripsters.sample.util.CustomToast;

public class BaseActivity extends Activity {

    private CustomToast mToast;

    @Override
    protected void onPause() {
        super.onPause();

        dismissProgress();
    }

    public void showProgress() {
        showProgress(R.string.loadinfo);
    }

    public void showProgress(int messageId) {
        if (mToast == null || !mToast.isShown()) {
            mToast = CustomToast.createProgressCustomToast(this, messageId);
            mToast.show();
        }
    }

    public void dismissProgress() {
        if (mToast != null && mToast.isShown()) {
            mToast.cancel();
        }
    }
}
