package com.tripsters.sample.util;

import android.text.TextUtils;

import com.tripsters.sample.R;
import com.tripsters.sample.TripstersApplication;

public class CheckUtils {

    /**
     * 检查问题标题是否合法
     *
     * @param questionTitle
     * @param onlyEmpty
     * @return
     */
    public static boolean checkQuestionTitleValid(String questionTitle, boolean onlyEmpty) {
        if (TextUtils.isEmpty(questionTitle)) {
            ErrorToast.getInstance().showErrorMessage(R.string.question_title_input_empty);
            return false;
        }

        if (onlyEmpty) {
            return true;
        }

        if (questionTitle.length() > Constants.Edit.MAX_QUESTION_TITLE_SIZE) {
            String prompt =
                    TripstersApplication.mContext.getString(R.string.question_title_input_invalid,
                            Constants.Edit.MAX_QUESTION_TITLE_SIZE);
            ErrorToast.getInstance().showErrorMessage(prompt);
            return false;
        }

        return true;
    }

    /**
     * 检查回答内容是否合法
     *
     * @param answerTitle
     * @return
     */
    public static boolean checkAnswerTitleValid(String answerTitle) {
        if (TextUtils.isEmpty(answerTitle)) {
            ErrorToast.getInstance().showErrorMessage(R.string.answer_title_input_empty);
            return false;
        }

        return true;
    }

    /**
     * 检查第三方平台用户唯一标示是否合法
     *
     * @param appuid
     * @return
     */
    public static boolean checkLoginAppuidValid(String appuid) {
        if (TextUtils.isEmpty(appuid)) {
            ErrorToast.getInstance().showErrorMessage(R.string.login_appuid_input_empty);
            return false;
        }

        return true;
    }

    /**
     * 检查第三方平台用户昵称是否合法
     *
     * @param nickname
     * @return
     */
    public static boolean checkLoginNicknameValid(String nickname) {
        if (TextUtils.isEmpty(nickname)) {
            ErrorToast.getInstance().showErrorMessage(R.string.login_nickname_input_empty);
            return false;
        }

        return true;
    }

    /**
     * 检查第三方平台用户头像是否合法
     *
     * @param avatar
     * @return
     */
    public static boolean checkLoginAvatarValid(String avatar) {
        if (TextUtils.isEmpty(avatar)) {
            ErrorToast.getInstance().showErrorMessage(R.string.login_avatar_input_empty);
            return false;
        }

        return true;
    }
}
