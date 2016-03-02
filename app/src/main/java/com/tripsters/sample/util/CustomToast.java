package com.tripsters.sample.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

import com.tripsters.sample.R;
import com.tripsters.sample.view.CustomToastView;

public class CustomToast {
    private Handler mHandler;
    private Toast mToast;
    private boolean isCancelled;

    public CustomToast(Context context, int resId) {
        mHandler = new Handler();
        mToast = createProgressToast(context, resId);
        isCancelled = true;
    }

    private static Toast createProgressToast(Context context, int resId) {
        Toast toast = new Toast(context);
        CustomToastView view = new CustomToastView(context);
        view.update(resId);

        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        return toast;
    }

    public void show() {
        isCancelled = false;
        doShow();
    }

    private void doShow() {
        // make sure the toast is shutdown when app close
        if (!isCancelled) {
            mToast.show();

            mHandler.postDelayed(new Runnable() {
                public void run() {
                    doShow();
                }

            }, 1000);
        }
    }

    public void showWithTime(int second) {
        isCancelled = false;
        showToastWithTime(second);
    }

    private void showToastWithTime(int second) {
        // make sure the toast is shutdown when app close
        mHandler.removeCallbacks(delayRunnable);

        if (!isCancelled) {
            mToast.show();

            mHandler.postDelayed(delayRunnable, second * 1000);
        }
    }

    private Runnable delayRunnable = new Runnable() {

        @Override
        public void run() {
            cancel();
        }
    };

    public boolean isShown() {
        return !isCancelled;
    }

    public void cancel() {
        isCancelled = true;
        mToast.cancel();
    }

    public static CustomToast createProgressCustomToast(Context context, int resId) {
        return new CustomToast(context, resId);
    }

    public static Dialog createProgressDialog(Context context, int resId/* , int style */) {
        Dialog mPgDialog = new Dialog(context, R.style.Tripsters_Dialog);
        CustomToastView view = new CustomToastView(context);
        view.update(resId);
        mPgDialog.setContentView(view);
        mPgDialog.setCancelable(true);
        return mPgDialog;
    }
}
