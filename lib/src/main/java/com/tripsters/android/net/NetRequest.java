package com.tripsters.android.net;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.tripsters.android.model.CityList;
import com.tripsters.android.model.CountryList;
import com.tripsters.android.model.ModelFactory;

import java.io.IOException;
import java.util.Locale;

/**
 * 网络接口类
 */
public class NetRequest {

    private static final String HOST_URL = "http://www.tripsters.cn/index.php";

    private static final String PARAM_KEYS_GET = "keys_get";
    private static final String PARAM_KEYS_POST = "keys_post";
    private static final String PARAM_MODEL = "m";
    private static final String PARAM_CONTROLLER = "c";
    private static final String PARAM_ACTION = "a";

    private static final String PARAM_MODEL_VALUE_PARTNER = "partner";
    private static final String PARAM_CONTROLLER_VALUE_COUNTRY = "country";

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
     * @param context
     * @return
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
     * @param context
     * @param countryCode 国家代码
     * @return
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

        StringBuilder sbUrl = new StringBuilder(HOST_URL);

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
        params.putString(PARAM_APPID, "appid");
        params.putString(PARAM_PLATFORM, PARAM_PLATFORM_VALUE_ANDROID);
        params.putString(PARAM_VERSION, PARAM_VERSION_VALUE);
        params.putString(PARAM_LANGUAGE, Locale.getDefault().toString());
    }
}
