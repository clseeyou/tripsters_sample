package com.tripsters.sample;

import android.support.v4.app.FragmentActivity;

import com.tripsters.sample.util.CustomToast;

public class BaseFragmentActivity extends FragmentActivity {

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
