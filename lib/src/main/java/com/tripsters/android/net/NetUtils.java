package com.tripsters.android.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.tripsters.android.util.LogUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * http请求封装类
 */
public class NetUtils {

    public static final String TYPE_FILE_NAME = "TYPE_FILE_NAME";
    public static final String GZIP_FILE_NAME = "GZIP_FILE_NAME";

    private static final int TIME_OUT_DELAY = 30000;
    private static final int HTTP_STATUS_OK = 200;

    private enum NetworkState {
        NOTHING, MOBILE, WIFI
    }

    private static HttpClient getHttpClient(Context context) throws IOException {
        NetworkState networkState = getNetworkState(context);

        if (networkState == NetworkState.NOTHING) {
            throw new IOException("Net Error");
        }

        HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParamBean httpConnectionParamBean = new HttpConnectionParamBean(httpClient.getParams());

        httpConnectionParamBean.setSoTimeout(TIME_OUT_DELAY);
        httpConnectionParamBean.setConnectionTimeout(TIME_OUT_DELAY);

        return httpClient;
    }

    // 判断当前的网络的类型
    private static NetworkState getNetworkState(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isAvailable()) {
            return NetworkState.NOTHING;
        } else {
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return NetworkState.MOBILE;
            } else {
                return NetworkState.WIFI;
            }
        }
    }

    private static HttpEntity buildMultipartEntity(Bundle params)
            throws UnsupportedEncodingException {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        for (String key : params.keySet()) {
            Object objValue = params.get(key);

            if (TYPE_FILE_NAME.equals(key) || GZIP_FILE_NAME.equals(key)) {
                if (objValue != null && objValue instanceof Bundle) {
                    Bundle pathBundle = (Bundle) objValue;

                    for (String pathKey : pathBundle.keySet()) {
                        String filePath = pathBundle.getString(pathKey);

                        if (filePath != null) {
                            final File file = new File(filePath);

                            if (file.exists()) {
                                FileBody fileBody;

                                if (TYPE_FILE_NAME.equals(key)) {
                                    fileBody = new FileBody(file, ContentType.create("image/jpeg"));
                                } else {
                                    fileBody = new FileBody(file, ContentType.create("application/zip"));
                                }

                                multipartEntityBuilder.addPart(pathKey, fileBody);
                            }
                        }
                    }
                }
            } else {
                if (objValue != null && objValue instanceof byte[]) {
                    byte[] bytesValue = (byte[]) objValue;

                    multipartEntityBuilder.addBinaryBody(key, bytesValue);
                } else {
                    String value = objValue == null ? "" : String.valueOf(objValue);

                    multipartEntityBuilder.addTextBody(key, value, ContentType.TEXT_PLAIN.withCharset(HTTP.UTF_8));
                }
            }
        }

        return multipartEntityBuilder.build();
    }

    private static String execute(HttpClient httpClient, HttpUriRequest httpUriRequest) throws IOException {
        return new String(executeNetRequest(httpClient, httpUriRequest));
    }

    private static byte[] executeNetRequest(HttpClient httpClient, HttpUriRequest httpUriRequest)
            throws IOException {
        try {
            HttpResponse response = httpClient.execute(httpUriRequest);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode != HTTP_STATUS_OK) {
                throw new IOException(String.format("Invalid response from server: %s",
                        statusLine.toString()));
            }

            // int length = -1;
            // Header lengthHeader = response.getFirstHeader("Content-Length");
            // if (lengthHeader != null) {// 获得下载文件长度
            // length = Integer.valueOf(lengthHeader.getValue());
            // }

            // Pull content stream from response
            HttpEntity httpEntity = response.getEntity();
            InputStream inputStream = httpEntity.getContent();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // Read response into a buffered stream
            byte[] buffer = new byte[512];
            int len;

            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }

            // Return result from buffered stream
            return byteArrayOutputStream.toByteArray();
        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }
    }

    /**
     * 网络请求（Get方式）
     *
     * @param context context
     * @param url     get方式请求的url
     * @return 返回数据（json结构）
     * @throws IOException
     */
    public static String httpGet(Context context, String url) throws IOException {
        LogUtils.logd(url);
        HttpClient httpClient = getHttpClient(context);
        HttpGet httpGet = new HttpGet(url);

        return execute(httpClient, httpGet);
    }

    /**
     * 网络请求（Post方式）
     *
     * @param context    context
     * @param url        post方式请求的url
     * @param postParams post方式提交的内容
     * @return 返回数据（json结构）
     * @throws IOException
     */
    public static String httpPost(Context context, String url, Bundle postParams)
            throws IOException {
        LogUtils.logd(url);
        LogUtils.logd(postParams.toString());
        HttpClient httpClient = getHttpClient(context);
        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(buildMultipartEntity(postParams));

        return execute(httpClient, httpPost);
    }
}
