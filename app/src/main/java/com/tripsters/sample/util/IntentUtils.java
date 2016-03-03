package com.tripsters.sample.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.tripsters.android.model.Question;
import com.tripsters.android.model.UserInfo;
import com.tripsters.sample.LoginActivity;
import com.tripsters.sample.ProfileActivity;
import com.tripsters.sample.QuestionDetailActivity;
import com.tripsters.sample.TGalleryActivity;

public class IntentUtils {

    /**
     * 登录
     *
     * @param context
     */
    public static void login(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳转到用户资料页
     *
     * @param context
     * @param userInfo
     */
    public static void startUserInfoActivity(Context context, UserInfo userInfo) {
        if (userInfo != null) {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra(Constants.Extra.USERINFO, userInfo);
            context.startActivity(intent);
        }
    }

    /**
     * 跳转到问题资料页
     *
     * @param context
     * @param question
     */
    public static void startQuestionDetailActivity(Context context, Question question) {
        if (question != null) {
            Intent intent = new Intent(context, QuestionDetailActivity.class);
            intent.putExtra(Constants.Extra.QUESTION, question);
            context.startActivity(intent);
        }
    }

    /**
     * 跳转到外部浏览器
     *
     * @param context
     * @param url
     */
    public static void openUrlByBrowser(Context context, String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.length() > PatternUtils.HTTP_PREFIX.length()) {
                String sub = url.subSequence(0, PatternUtils.HTTP_PREFIX.length()).toString();

                if (!sub.equalsIgnoreCase(PatternUtils.HTTP_PREFIX)) {
                    url = PatternUtils.HTTP_PREFIX + url;
                }
            } else {
                url = PatternUtils.HTTP_PREFIX + url;
            }

            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    /**
     * 发送提问成功广播
     *
     * @param context
     * @param uid
     */
    public static void sendQuestionBroadcast(Context context, String uid) {
        if (!LoginUser.isLogin(context) || !LoginUser.getId().equals(uid)) {
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Constants.Action.QUESTION_SUCCESS);
        intent.putExtra(Constants.Extra.USER_ID, uid);
        context.sendBroadcast(intent);
    }

    /**
     * 发送回答成功广播
     *
     * @param context
     * @param uid
     * @param quetionId
     */
    public static void sendAnswerBroadcast(Context context, String uid, String quetionId) {
        if (!LoginUser.isLogin(context) || !LoginUser.getId().equals(uid)) {
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Constants.Action.ANSWER_SUCCESS);
        intent.putExtra(Constants.Extra.USER_ID, uid);
        intent.putExtra(Constants.Extra.QUESTION_ID, quetionId);
        context.sendBroadcast(intent);
    }

    /**
     * start camera activity upon Gallery
     *
     * @param activity
     * @param requestCode
     * @param cutImage
     */
    public static void startToCameraActivity(Activity activity, int requestCode, boolean cutImage) {
        Intent cameraIntent = new Intent(activity, TGalleryActivity.class);
        cameraIntent.putExtra(Constants.Extra.TYPE, TGalleryActivity.TYPE_FROM_CAMERA);
        cameraIntent.putExtra(Constants.Extra.CUT_IMAGE, cutImage);

        activity.startActivityForResult(cameraIntent, requestCode);
    }

    /**
     * start Media activity upon Gallery
     *
     * @param activity
     * @param requestCode
     * @param maxCount
     * @param singleType
     */
    public static void startToMediaActivity(Activity activity, int requestCode, int maxCount,
                                            boolean singleType) {
        Intent mediaIntent = new Intent(activity, TGalleryActivity.class);
        mediaIntent.putExtra(Constants.Extra.TYPE, TGalleryActivity.TYPE_FROM_ACTIVITY);
        mediaIntent.putExtra(Constants.Extra.MAX_COUNT, maxCount);
        mediaIntent.putExtra(Constants.Extra.SINGLE_TYPE, singleType);

        activity.startActivityForResult(mediaIntent, requestCode);
    }
}
