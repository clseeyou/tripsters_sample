package com.tripsters.android.net;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.tripsters.android.model.AnswerList;
import com.tripsters.android.model.CityList;
import com.tripsters.android.model.CountryList;
import com.tripsters.android.model.ModelFactory;
import com.tripsters.android.model.NetResult;
import com.tripsters.android.model.QuestionList;
import com.tripsters.android.model.QuestionResult;
import com.tripsters.android.model.TagList;
import com.tripsters.android.model.UserInfoResult;
import com.tripsters.android.util.DebugConfig;
import com.tripsters.android.util.Utils;

import java.io.IOException;

/**
 * 网络接口类
 */
public class NetRequest {

    private static final String HOST_URL = "http://www.tripsters.cn/index.php"; // 发布地址，上线的时候切换为此服务器
    private static final String HOST_URL_TEST = "http://114.215.108.44/index.php"; // 测试地址，开发过程中用这个服务器

    private static final String PARAM_KEYS_GET = "keys_get";
    private static final String PARAM_KEYS_POST = "keys_post";
    private static final String PARAM_MODEL = "m";
    private static final String PARAM_CONTROLLER = "c";
    private static final String PARAM_ACTION = "a";

    private static final String PARAM_MODEL_VALUE_PARTNER = "partner";
    private static final String PARAM_CONTROLLER_VALUE_COUNTRY = "country";
    private static final String PARAM_CONTROLLER_VALUE_CATE = "cate";
    private static final String PARAM_CONTROLLER_VALUE_QUESTION = "question";
    private static final String PARAM_CONTROLLER_VALUE_USER = "user";

    private static final String PARAM_APPID = "appid";
    private static final String PARAM_PLATFORM = "platform";
    private static final String PARAM_PLATFORM_VALUE_ANDROID = "android";
    private static final String PARAM_VERSION = "version";
    private static final String PARAM_VERSION_VALUE = "sdk1";
    private static final String PARAM_LANGUAGE = "language";

    private static class Request {
        String url;
        Bundle post;

        Request(String url, Bundle post) {
            this.url = url;
            this.post = post;
        }
    }

    /**
     * 获取支持的国家
     *
     * @param context context
     * @return 国家列表
     * @throws IOException
     */
    public static CountryList getSupportCountry(Context context) throws IOException {
        Bundle params = new Bundle();
        params.putString(PARAM_MODEL, PARAM_MODEL_VALUE_PARTNER);
        params.putString(PARAM_CONTROLLER, PARAM_CONTROLLER_VALUE_COUNTRY);
        params.putString(PARAM_ACTION, "getSupportCountry");
        // params
        Bundle paramKeys = new Bundle();
        params.putBundle(PARAM_KEYS_GET, paramKeys);

        String response = request(context, params);

        return ModelFactory.getInstance().create(response, CountryList.class);
    }

    /**
     * 获取支持的城市
     *
     * @param context     context
     * @param countryCode 国家代码
     * @return 城市列表
     * @throws IOException
     */
    public static CityList getSupportCity(Context context, String countryCode) throws IOException {
        Bundle params = new Bundle();
        params.putString(PARAM_MODEL, PARAM_MODEL_VALUE_PARTNER);
        params.putString(PARAM_CONTROLLER, PARAM_CONTROLLER_VALUE_COUNTRY);
        params.putString(PARAM_ACTION, "getSupportCity");
        // params
        Bundle paramKeys = new Bundle();
        paramKeys.putString("country_code", countryCode);
        params.putBundle(PARAM_KEYS_GET, paramKeys);

        String response = request(context, params);

        return ModelFactory.getInstance().create(response, CityList.class);
    }

    /**
     * 获取支持的类别
     *
     * @param context context
     * @return 类别列表
     * @throws IOException
     */
    public static TagList getSupportCate(Context context) throws IOException {
        Bundle params = new Bundle();
        params.putString(PARAM_MODEL, PARAM_MODEL_VALUE_PARTNER);
        params.putString(PARAM_CONTROLLER, PARAM_CONTROLLER_VALUE_CATE);
        params.putString(PARAM_ACTION, "getSupportCate");
        // params
        Bundle paramKeys = new Bundle();
        params.putBundle(PARAM_KEYS_GET, paramKeys);

        String response = request(context, params);

        return ModelFactory.getInstance().create(response, TagList.class);
    }

    /**
     * 发送问题
     *
     * @param context context
     * @param uid     用户id（必填）
     * @param title   问题的标题，限长60个字（必填）
     * @param picPath 问题图片的流，限制为一张（可选）
     * @param country 国家，中文国家名（必填）
     * @param cities  国家对应的城市，用趣皮士提供的城市id，中间为英文逗号隔开如：34,45最多为两个城市（必填）
     * @param tags    问题标签，用趣皮士提供的标签id，中间为英文逗号隔开如：1,2最多为三个城市（可选）
     * @param lat     问题图片的流，限制为一张（可选）
     * @param lng     问题的精度，格式为：100.5391834（可选）
     * @param address 问题的精度，格式为：100.5391834（可选）
     * @return 提问结果
     * @throws IOException
     */
    public static NetResult sendQuestionById(Context context, String uid, String title,
                                             String picPath, String country, String cities, String tags, String lat,
                                             String lng, String address) throws IOException {
        Bundle params = new Bundle();
        params.putString(PARAM_MODEL, PARAM_MODEL_VALUE_PARTNER);
        params.putString(PARAM_CONTROLLER, PARAM_CONTROLLER_VALUE_QUESTION);
        params.putString(PARAM_ACTION, "sendQuestionById");
        // params
        Bundle paramKeys = new Bundle();
        paramKeys.putString("user_id", uid);
        paramKeys.putString("title", title);
        // 图片path
        Bundle picParams = new Bundle();
        picParams.putString("pic", picPath);
        paramKeys.putBundle(NetUtils.TYPE_FILE_NAME, picParams);
        paramKeys.putString("country", country);
        paramKeys.putString("city", cities);
        paramKeys.putString("category", tags);
        paramKeys.putString("lat", lat);
        paramKeys.putString("lng", lng);
        paramKeys.putString("address", address);
        params.putBundle(PARAM_KEYS_POST, paramKeys);

        String response = request(context, params);

        return ModelFactory.getInstance().create(response, NetResult.class);
    }

    /**
     * 第几页，从1开始
     *
     * @param context  context
     * @param qid      问题的id
     * @param page     第几页，从1开始
     * @param pagesize 每页的数量，默认为20，最大为50，当返回数量<pagesize，意味着没有更多了
     * @return 回答列表
     * @throws IOException
     */
    public static AnswerList getAnswer(Context context, String qid, int page, int pagesize)
            throws IOException {
        Bundle params = new Bundle();
        params.putString(PARAM_MODEL, PARAM_MODEL_VALUE_PARTNER);
        params.putString(PARAM_CONTROLLER, PARAM_CONTROLLER_VALUE_QUESTION);
        params.putString(PARAM_ACTION, "getAnswer");
        // params
        Bundle paramKeys = new Bundle();
        paramKeys.putString("question_id", qid);
        paramKeys.putString("page", page + "");
        paramKeys.putString("pagesize", pagesize + "");
        params.putBundle(PARAM_KEYS_GET, paramKeys);

        String response = request(context, params);

        return ModelFactory.getInstance().create(response, AnswerList.class);
    }

    /**
     * 获取趣皮士在某一国家下的全部问题
     *
     * @param context     context
     * @param countryName 中文国家名，当country为''空串时，返回所有国家的混合
     * @param page        第几页，从1开始
     * @param pagesize    每页的数量，默认为20，最大为50，当返回数量<pagesize，意味着没有更多了
     * @return 问题列表
     * @throws IOException
     */
    public static QuestionList getAllQuestion(Context context, String countryName, int page, int pagesize)
            throws IOException {
        Bundle params = new Bundle();
        params.putString(PARAM_MODEL, PARAM_MODEL_VALUE_PARTNER);
        params.putString(PARAM_CONTROLLER, PARAM_CONTROLLER_VALUE_QUESTION);
        params.putString(PARAM_ACTION, "getQuestion");
        // params
        Bundle paramKeys = new Bundle();
        paramKeys.putString("country", countryName);
        paramKeys.putString("mode", "all");
        paramKeys.putString("page", page + "");
        paramKeys.putString("pagesize", pagesize + "");
        params.putBundle(PARAM_KEYS_GET, paramKeys);

        String response = request(context, params);

        return ModelFactory.getInstance().create(response, QuestionList.class);
    }

    /**
     * 获取当前应用在某一国家下的全部问题
     *
     * @param context     context
     * @param countryName 中文国家名，当country为''空串时，返回所有国家的混合
     * @param page        第几页，从1开始
     * @param pagesize    每页的数量，默认为20，最大为50，当返回数量<pagesize，意味着没有更多了
     * @return 问题列表
     * @throws IOException
     */
    public static QuestionList getAppQuestion(Context context, String countryName, int page, int pagesize)
            throws IOException {
        Bundle params = new Bundle();
        params.putString(PARAM_MODEL, PARAM_MODEL_VALUE_PARTNER);
        params.putString(PARAM_CONTROLLER, PARAM_CONTROLLER_VALUE_QUESTION);
        params.putString(PARAM_ACTION, "getQuestion");
        // params
        Bundle paramKeys = new Bundle();
        paramKeys.putString("country", countryName);
        paramKeys.putString("mode", "myself");
        paramKeys.putString("page", page + "");
        paramKeys.putString("pagesize", pagesize + "");
        params.putBundle(PARAM_KEYS_GET, paramKeys);

        String response = request(context, params);

        return ModelFactory.getInstance().create(response, QuestionList.class);
    }

    /**
     * 获取问题详情
     *
     * @param context context
     * @param qid     问题的id
     * @return 问题详情
     * @throws IOException
     */
    public static QuestionResult getQuestionDetail(Context context, String qid)
            throws IOException {
        Bundle params = new Bundle();
        params.putString(PARAM_MODEL, PARAM_MODEL_VALUE_PARTNER);
        params.putString(PARAM_CONTROLLER, PARAM_CONTROLLER_VALUE_QUESTION);
        params.putString(PARAM_ACTION, "getQuestionDetail");
        // params
        Bundle paramKeys = new Bundle();
        paramKeys.putString("question_id", qid);
        params.putBundle(PARAM_KEYS_GET, paramKeys);

        String response = request(context, params);

        return ModelFactory.getInstance().create(response, QuestionResult.class);
    }

    /**
     * 回复问题
     *
     * @param context context
     * @param uid     用户id（必填）
     * @param detail  回复详情，限长为255（必填）
     * @param picPath 问题图片的流，限制为一张（可选）
     * @param pois
     * @param blogs
     * @param qid     回复详情，限长为255（必填）
     * @param quid
     * @return 回复问题结果
     * @throws IOException
     */
    public static NetResult sendAnswerById(Context context, String uid, String detail,
                                           String picPath, String pois, String blogs, String qid, String quid)
            throws IOException {
        Bundle params = new Bundle();
        params.putString(PARAM_MODEL, PARAM_MODEL_VALUE_PARTNER);
        params.putString(PARAM_CONTROLLER, PARAM_CONTROLLER_VALUE_QUESTION);
        params.putString(PARAM_ACTION, "sendAnswerById");

        // params
        Bundle paramKeys = new Bundle();
        paramKeys.putString("user_id", uid);
        paramKeys.putString("detail", detail);
        // 图片path
        Bundle picParams = new Bundle();
        picParams.putString("pic", picPath);
        paramKeys.putBundle(NetUtils.TYPE_FILE_NAME, picParams);
        paramKeys.putString("pois", pois);
        paramKeys.putString("locals", blogs);
        paramKeys.putString("question_id", qid);
        paramKeys.putString("q_user_id", quid);
        params.putBundle(PARAM_KEYS_POST, paramKeys);

        String response = request(context, params);

        return ModelFactory.getInstance().create(response, NetResult.class);
    }


    /**
     * 追问回复
     *
     * @param context context
     * @param uid     用户id（必填）
     * @param detail  回复详情，限长为255（必填）
     * @param picPath 问题图片的流，限制为一张（可选）
     * @param pois
     * @param locals
     * @param qid     回复详情，限长为255（必填）
     * @param quid
     * @param auid    被追问者的用户id（趣皮士提供的user_id,不是open_id）（必填）
     * @return 追问回复结果
     * @throws IOException
     */
    public static NetResult sendReAnswerById(Context context, String uid, String detail,
                                             String picPath, String pois, String locals, String qid, String quid, String auid)
            throws IOException {
        Bundle params = new Bundle();
        params.putString(PARAM_MODEL, PARAM_MODEL_VALUE_PARTNER);
        params.putString(PARAM_CONTROLLER, PARAM_CONTROLLER_VALUE_QUESTION);
        params.putString(PARAM_ACTION, "sendReAnswerById");

        // params
        Bundle paramKeys = new Bundle();
        paramKeys.putString("user_id", uid);
        paramKeys.putString("detail", detail);
        // 图片path
        Bundle picParams = new Bundle();
        picParams.putString("pic", picPath);
        paramKeys.putBundle(NetUtils.TYPE_FILE_NAME, picParams);
        paramKeys.putString("pois", pois);
        paramKeys.putString("locals", locals);
        paramKeys.putString("question_id", qid);
        paramKeys.putString("q_user_id", quid);
        paramKeys.putString("answer_user_id", auid);
        params.putBundle(PARAM_KEYS_POST, paramKeys);

        String response = request(context, params);

        return ModelFactory.getInstance().create(response, NetResult.class);
    }

    /**
     * 第三方用户登陆
     *
     * @param context  context
     * @param appuid   用户在自己平台的uid（必填）
     * @param nickname 昵称（必填）
     * @param avatar   用户头像（必填）
     * @param gender   性别格式为：m：男、f：女、n：未知 （必填）
     * @param location 用户的地址（可选）
     * @return 用户信息（UserInfo结构里只包含了趣皮士用户的id，用于趣皮士中需要uid的网络请求调用）
     * @throws IOException
     */
    public static UserInfoResult login(Context context, String appuid, String nickname,
                                       String avatar, String gender, String location) throws IOException {
        Bundle params = new Bundle();
        params.putString(PARAM_MODEL, PARAM_MODEL_VALUE_PARTNER);
        params.putString(PARAM_CONTROLLER, PARAM_CONTROLLER_VALUE_USER);
        params.putString(PARAM_ACTION, "login");
        // params
        Bundle paramKeys = new Bundle();
        paramKeys.putString("user_id", appuid);
        paramKeys.putString("nickname", nickname);
        paramKeys.putString("avatar", avatar);
        paramKeys.putString("location", location);
        paramKeys.putString("gender", gender);
        params.putBundle(PARAM_KEYS_GET, paramKeys);

        String response = request(context, params);

        return ModelFactory.getInstance().create(response, UserInfoResult.class);
    }

    private static String request(Context context, Bundle params) throws IOException {
        Request request = getRequest(context, params);

        if (request.post == null) {
            return NetUtils.httpGet(context, request.url);
        } else {
            return NetUtils.httpPost(context, request.url, request.post);
        }
    }

    private static Request getRequest(Context context, Bundle params) {
        Bundle paramKeysGet = params.getBundle(PARAM_KEYS_GET);
        Bundle paramKeysPost = params.getBundle(PARAM_KEYS_POST);

        if (paramKeysGet == null) {
            paramKeysGet = new Bundle();
        }

        String model = params.getString(PARAM_MODEL);
        if (!TextUtils.isEmpty(model)) {
            paramKeysGet.putString(PARAM_MODEL, model);
        }

        String controller = params.getString(PARAM_CONTROLLER);
        if (!TextUtils.isEmpty(controller)) {
            paramKeysGet.putString(PARAM_CONTROLLER, controller);
        }

        String action = params.getString(PARAM_ACTION);
        if (!TextUtils.isEmpty(action)) {
            paramKeysGet.putString(PARAM_ACTION, action);
        }

        if (paramKeysPost == null) {
            setCommonParams(context, paramKeysGet);
        } else {
            // 去除value空项
            Bundle bundle = new Bundle();
            bundle.putAll(paramKeysPost);

            for (String key : bundle.keySet()) {
                if (bundle.get(key) == null) {
                    paramKeysPost.remove(key);
                }
            }

            setCommonParams(context, paramKeysPost);
        }

        StringBuilder sbUrl;

        if (DebugConfig.sNetDebug) {
            sbUrl = new StringBuilder(HOST_URL_TEST);
        } else {
            sbUrl = new StringBuilder(HOST_URL);
        }

        sbUrl.append("?");

        int i = 0;
        for (String key : paramKeysGet.keySet()) {
            Object value = paramKeysGet.get(key);

            // value非空项
            if (value != null) {
                if (i != 0) {
                    sbUrl.append("&");
                }

                sbUrl.append(key).append("=").append(Uri.encode(value.toString()));
            }

            i++;
        }

        return new Request(sbUrl.toString(), paramKeysPost);
    }

    private static void setCommonParams(Context context, Bundle params) {
        params.putString(PARAM_APPID, Utils.getAppId(context));
        params.putString(PARAM_PLATFORM, PARAM_PLATFORM_VALUE_ANDROID);
        params.putString(PARAM_VERSION, PARAM_VERSION_VALUE);
        params.putString(PARAM_LANGUAGE, Utils.getAppLang(context));
    }
}
