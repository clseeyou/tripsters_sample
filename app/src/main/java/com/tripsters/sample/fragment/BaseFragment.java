package com.tripsters.sample.fragment;

import android.support.v4.app.Fragment;

import com.tripsters.sample.R;
import com.tripsters.sample.util.CustomToast;

public class BaseFragment extends Fragment {

    private CustomToast mToast;

    @Override
    public void onPause() {
        super.onPause();

        dismissProgress();
    }

    protected void showProgress() {
        showProgress(R.string.loadinfo);
    }

    protected void showProgress(int messageId) {
        if (mToast == null || !mToast.isShown()) {
            mToast = CustomToast.createProgressCustomToast(getActivity(), messageId);
            mToast.show();
        }
    }

    protected void dismissProgress() {
        if (mToast != null && mToast.isShown()) {
            mToast.cancel();
        }
    }
}
