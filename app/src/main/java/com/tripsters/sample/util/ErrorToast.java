package com.tripsters.sample.util;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.tripsters.android.model.ListNetResult;
import com.tripsters.android.model.NetResult;
import com.tripsters.sample.R;
import com.tripsters.sample.TripstersApplication;
import com.tripsters.sample.view.TListView;

public class ErrorToast {

    public interface OnResultListener {
        void onNetError(String message);
    }

    private static ErrorToast sInstance = null;

    private boolean mErroMessage;

    private ErrorToast() {}

    public static ErrorToast getInstance() {
        if (sInstance == null) {
            synchronized (ErrorToast.class) {
                if (sInstance == null) {
                    sInstance = new ErrorToast();
                }
            }
        }

        return sInstance;
    }

    public void showNetError() {
        if (!mErroMessage) {
            mErroMessage = true;

            Toast.makeText(TripstersApplication.mContext,
                    TripstersApplication.mContext.getString(R.string.connect_failuer_toast),
                    Toast.LENGTH_SHORT).show();

            // 5s后提示
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                @Override
                public void run() {
                    mErroMessage = false;
                }
            }, 3000);
        }
    }

    public void showErrorMessage(String errorMessage) {
        if (!TextUtils.isEmpty(errorMessage)) {
            Toast.makeText(TripstersApplication.mContext, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public void showErrorMessage(int errorMessageResId) {
        Toast.makeText(TripstersApplication.mContext,
                TripstersApplication.mContext.getString(errorMessageResId), Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * 对网络数据返回进行测试
     * 
     * @param result
     * @return true：返回成功 false：返回失败
     */
    public boolean checkNetResult(NetResult result) {
        if (result == null) {
            showNetError();

            return false;
        } else {
            if (result.isSuccessful()) {
                return true;
            } else {
                showErrorMessage(result.getErrmsg());

                return false;
            }
        }
    }

    /**
     * 对网络数据返回进行测试
     * 
     * @param result
     * @return true：返回成功 false：返回失败
     */
    public boolean checkNetResult(NetResult result, OnResultListener listener) {
        if (result == null) {
            showNetError();

            return false;
        } else {
            if (result.isSuccessful()) {
                return true;
            } else {
                listener.onNetError(result.getErrmsg());

                return false;
            }
        }
    }

    /**
     * 对网络数据返回进行测试
     * 
     * @param view
     * @param result
     * @return true：返回成功 false：返回失败
     */
    public boolean checkNetResult(TListView view, ListNetResult<?> result) {
        // if (result == null) {
        // view.endLoadFailed();
        //
        // return false;
        // } else {
        // if (result.isSuccessful()) {
        // view.endLoadSuccess(result.getList());
        //
        // return true;
        // } else {
        // view.endLoadFailed(result.getMessage());
        //
        // return false;
        // }
        // }
        return checkNetResult(view, result, true);
    }

    // 对接口做兼容
    public boolean checkNetResult(TListView view, ListNetResult<?> result, boolean showMessageToast) {
        if (result == null) {
            view.endLoadFailed();

            return false;
        } else {
            if (result.isSuccessful()) {
                view.endLoadSuccess(result.getList());

                return true;
            } else {
                view.endLoadFailed(result.getErrmsg(), showMessageToast);

                return false;
            }
        }
    }
}
