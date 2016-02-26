package com.tripsters.android.task;

import android.content.Context;
import android.os.AsyncTask;

import com.tripsters.android.model.UserInfoResult;
import com.tripsters.android.net.NetRequest;

import java.io.IOException;

public class LoginTask extends AsyncTask<Void, Void, UserInfoResult> {

    public interface LoginTaskResult {
        public void onTaskResult(UserInfoResult result);
    }

    private Context mContext;
    private String mAppUid;
    private String mNickname;
    private String mAvatar;
    private String mGender;
    private String mLocation;
    private LoginTaskResult mTaskResult;

    public LoginTask(Context context, String appUid, String nickname, String avatar, String gender, String location,
                     LoginTaskResult taskResult) {
        this.mContext = context;
        this.mAppUid = appUid;
        this.mNickname = nickname;
        this.mAvatar = avatar;
        this.mGender = gender;
        this.mLocation = location;
        this.mTaskResult = taskResult;
    }

    @Override
    protected UserInfoResult doInBackground(Void... params) {
        try {
            return NetRequest.login(mContext, mAppUid, mNickname, mAvatar, mGender, mLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(UserInfoResult result) {
        if (mTaskResult != null) {
            mTaskResult.onTaskResult(result);
        }
    }
}
