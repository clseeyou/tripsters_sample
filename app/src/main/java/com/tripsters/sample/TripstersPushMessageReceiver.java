package com.tripsters.sample;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushMessageReceiver;
import com.tripsters.android.model.ModelFactory;
import com.tripsters.android.model.NetResult;
import com.tripsters.android.task.UpdateUserInfoTask;
import com.tripsters.android.util.LogUtils;
import com.tripsters.sample.model.PushMessage;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.LoginUser;
import com.tripsters.sample.util.MessageUnread;

import java.util.List;

/*
 * Push消息处理receiver。请编写您需要的回调函数， 一般来说： onBind是必须的，用来处理startWork返回值；
 *onMessage用来接收透传消息； onSetTags、onDelTags、onListTags是tag相关操作的回调；
 *onNotificationClicked在通知被点击时回调； onUnbind是stopWork接口的返回值回调

 * 返回值中的errorCode，解释如下：
 *0 - Success
 *10001 - Network Problem
 *10101  Integrate Check Error
 *30600 - Internal Server Error
 *30601 - Method Not Allowed
 *30602 - Request Params Not Valid
 *30603 - Authentication Failed
 *30604 - Quota Use Up Payment Required
 *30605 -Data Required Not Found
 *30606 - Request Time Expires Timeout
 *30607 - Channel Token Timeout
 *30608 - Bind Relation Not Found
 *30609 - Bind Number Too Many

 * 当您遇到以上返回错误时，如果解释不了您的问题，请用同一请求的返回值requestId和errorCode联系我们追查问题。
 *
 */
public class TripstersPushMessageReceiver extends PushMessageReceiver {
    /**
     * TAG to Log
     */
    public static final String TAG = "PushMessage";

    // 在百度开发者中心查询应用的API Key
    public static String API_KEY = "WEFAU1ylCuDROaM1tEwQpVR8";

    /**
     * 调用PushManager.startWork后，sdk将对push
     * server发起绑定请求，这个过程是异步的。绑定请求的结果通过onBind返回。 如果您需要用单播推送，需要把这里获取的channel
     * id和user id上传到应用server中，再调用server接口用channel id和user id给单个手机或者用户推送。
     *
     * @param context   BroadcastReceiver的执行Context
     * @param errorCode 绑定接口返回值，0 - 成功
     * @param appid     应用id。errorCode非0时为null
     * @param userId    应用user id。errorCode非0时为null
     * @param channelId 应用channel id。errorCode非0时为null
     * @param requestId 向服务端发起的请求id。在追查问题时有用；
     * @return none
     */
    @Override
    public void onBind(Context context, int errorCode, String appid,
                       String userId, String channelId, String requestId) {
        String responseString =
                "onBind errorCode=" + errorCode + " appid=" + appid + " userId=" + userId
                        + " channelId=" + channelId + " requestId=" + requestId;
        LogUtils.logd(TAG, responseString);

        // 绑定成功，设置已绑定flag，可以有效的减少不必要的绑定请求
        if (errorCode == 0) {
            if (LoginUser.isLogin()) {
                if (!LoginUser.isBind()) {
                    updateUserInfo(LoginUser.getId(), channelId);
                }
            } else {
                LoginUser.setChannelId(channelId);
            }
        }
    }

    /**
     * PushManager.stopWork() 的回调函数。
     *
     * @param context   上下文
     * @param errorCode 错误码。0表示从云推送解绑定成功；非0表示失败。
     * @param requestId 分配给对云推送的请求的id
     */
    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {
        String responseString = "onUnbind errorCode=" + errorCode + " requestId = " + requestId;
        LogUtils.logd(TAG, responseString);

        // 解绑定成功，设置未绑定flag，
        if (errorCode == 0) {
            LoginUser.clearChannelId();
        }
    }

    /**
     * setTags() 的回调函数。
     *
     * @param context     上下文
     * @param errorCode   错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
     * @param successTags 设置成功的tag
     * @param failTags    设置失败的tag
     * @param requestId   分配给对云推送的请求的id
     */
    @Override
    public void onSetTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString =
                "onSetTags errorCode=" + errorCode + " sucessTags=" + successTags + " failTags="
                        + failTags + " requestId=" + requestId;
        LogUtils.logd(TAG, responseString);
    }

    /**
     * delTags() 的回调函数。
     *
     * @param context     上下文
     * @param errorCode   错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
     * @param successTags 成功删除的tag
     * @param failTags    删除失败的tag
     * @param requestId   分配给对云推送的请求的id
     */
    @Override
    public void onDelTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString =
                "onDelTags errorCode=" + errorCode + " sucessTags=" + successTags + " failTags="
                        + failTags + " requestId=" + requestId;
        LogUtils.logd(TAG, responseString);
    }

    /**
     * listTags() 的回调函数。
     *
     * @param context   上下文
     * @param errorCode 错误码。0表示列举tag成功；非0表示失败。
     * @param tags      当前应用设置的所有tag。
     * @param requestId 分配给对云推送的请求的id
     */
    @Override
    public void onListTags(Context context, int errorCode, List<String> tags,
                           String requestId) {
        String responseString = "onListTags errorCode=" + errorCode + " tags=" + tags;
        LogUtils.logd(TAG, responseString);
    }

    /**
     * 接收透传消息的函数。
     *
     * @param context             上下文
     * @param message             推送的消息
     * @param customContentString 自定义内容,为空或者json字符串
     */
    @Override
    public void onMessage(Context context, String message, String customContentString) {
        String messageString =
                "透传消息 message=\"" + message + "\" customContentString=" + customContentString;
        LogUtils.logd(TAG, messageString);
    }

    /**
     * 接收通知点击的函数。
     *
     * @param context             上下文
     * @param title               推送的通知的标题
     * @param description         推送的通知的描述
     * @param customContentString 自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationClicked(Context context, String title,
                                      String description, String customContentString) {
        String notifyString =
                "通知点击 title=\"" + title + "\" description=\"" + description + "\" customContent="
                        + customContentString;
        LogUtils.logd(TAG, notifyString);

        if (customContentString != null) {
            // 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
            PushMessage message =
                    ModelFactory.getInstance().create(customContentString, PushMessage.class);
            message.setTitle(title);
            message.setDescription(description);
            message.setTime(System.currentTimeMillis());

            // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
            updateContent(context, null, false);
        }
    }

    /**
     * 接收通知到达的函数。
     *
     * @param context             上下文
     * @param title               推送的通知的标题
     * @param description         推送的通知的描述
     * @param customContentString 自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationArrived(Context context, String title,
                                      String description, String customContentString) {
        String notifyString =
                "通知到达 title=\"" + title + "\" description=\"" + description + "\" customContent="
                        + customContentString;
        LogUtils.logd(TAG, notifyString);

        if (customContentString != null) {
            // 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
            PushMessage message =
                    ModelFactory.getInstance().create(customContentString, PushMessage.class);
            message.setTitle(title);
            message.setDescription(description);
            message.setTime(System.currentTimeMillis());

            // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
            updateContent(context, message, true);
        }
    }

    /**
     * 百度push绑定
     *
     * @param context 上下文
     */
    public static void start(Context context) {
        LogUtils.logd(TAG, "startWork");
        PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY, API_KEY);
    }

    /**
     * 百度push解绑
     *
     * @param context 上下文
     */
    public static void stop(Context context) {
        LogUtils.logd(TAG, "stopWork");
        PushManager.stopWork(context);
    }

    /**
     * 更新用户的push绑定信息
     *
     * @param uid       用户的uid
     * @param channelId 用户的channelId
     */
    public static void updateUserInfo(final String uid, final String channelId) {
        if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(channelId)) {
            new UpdateUserInfoTask(TripstersApplication.mContext, uid, channelId,
                    new UpdateUserInfoTask.UpdateUserInfoTaskResult() {
                        @Override
                        public void onTaskResult(NetResult result) {
                            if (result != null && result.isSuccessful()) {
                                LoginUser.setChannelId(channelId);
                            }
                        }
                    }).execute();
        }
    }

    private void updateContent(Context context, PushMessage content, boolean arrived) {
        LogUtils.logd(TAG, "updateContent");
        switch (content.getType()) {
            case RECEIVED_ANSWER:
                if (LoginUser.isLogin(context)) {
                    if (arrived) {
                        MessageUnread.getInstance(LoginUser.getUser(context)).addAnswerNum();
                    } else {
                        MessageUnread.getInstance(LoginUser.getUser(context)).reduceAnswerNum();

                        onReceivedQuestionClicked(context);
                    }

                    sendUnreadChangedBroadcast();
                }
                break;
            default:
                break;
        }
    }

    protected void onReceivedQuestionClicked(Context context) {
        Intent intent =
                new Intent(context.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.putExtra(Constants.Extra.NOTICE_TYPE,
                MainActivity.NOTICE_RECEIVED_ANSWER);
        context.getApplicationContext().startActivity(intent);
    }

    private void sendUnreadChangedBroadcast() {
        Intent intent = new Intent();
        intent.setAction(Constants.Action.MESSAGE_UNREAD_CHANGED);
        TripstersApplication.mContext.sendBroadcast(intent);
    }
}
