package com.tripsters.sample.util;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.tripsters.android.model.UserInfo;
import com.tripsters.sample.TripstersApplication;

public class MessageUnread {

    private static final String MESSAGE_UNREAD_SP = "message_unread_sp";
    private static final String KEY_ANSWER_TAG = "_answer_tag";

    private static MessageUnread sInstance = null;

    private String mUid;

    private int mAnswerNum;

    private MessageUnread() {
    }

    public synchronized static MessageUnread getInstance(UserInfo userInfo) {
        String uid = userInfo == null ? "" : userInfo.getId();

        if (sInstance == null) {
            sInstance = new MessageUnread();

            sInstance.init(uid);
        } else {
            if (TextUtils.isEmpty(uid)) {
                if (!TextUtils.isEmpty(sInstance.mUid)) {
                    sInstance.init(uid);
                }
            } else {
                if (!uid.equals(sInstance.mUid)) {
                    sInstance.init(uid);
                }
            }
        }

        return sInstance;
    }

    private void init(String uid) {
        mUid = uid;

        SharedPreferences sp =
                TripstersApplication.mContext.getSharedPreferences(MESSAGE_UNREAD_SP, 0);

        if (TextUtils.isEmpty(uid)) {
            mAnswerNum = 0;
        } else {
            mAnswerNum = sp.getInt(mUid + KEY_ANSWER_TAG, 0);
        }
    }

    public int getAnswerNum() {
        return mAnswerNum;
    }

    public void addAnswerNum() {
        mAnswerNum += 1;

        saveAnswerNum();
    }

    public void reduceAnswerNum() {
        mAnswerNum -= 1;

        saveAnswerNum();
    }

    public void clearAnswerNum() {
        mAnswerNum = 0;

        saveAnswerNum();
    }

    private void saveAnswerNum() {
        TripstersApplication.mContext.getSharedPreferences(MESSAGE_UNREAD_SP, 0).edit()
                .putInt(mUid + KEY_ANSWER_TAG, mAnswerNum).commit();
    }
}
