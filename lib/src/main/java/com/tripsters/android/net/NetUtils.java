package com.tripsters.android.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.tripsters.android.util.LogUtils;

public class NetUtils {
    public static final String TYPE_FILE_NAME = "TYPE_FILE_NAME";
    public static final String GZIP_FILE_NAME = "GZIP_FILE_NAME";

    private static final int TIME_OUT_DELAY = 30000;
    private static int HTTP_STATUS_OK = 200;

    private enum NetworkState {
        NOTHING, MOBILE, WIFI
    }

    public static String httpGet(Context context, String url) throws IOException {
        LogUtils.logd(url);
        HttpClient httpClient = null;
        try {
            httpClient = NetUtils.getHttpClient(context);
        } catch (IOException e1) {
            e1.printStackTrace();
            throw e1;
        }
        HttpGet getRequst = new HttpGet(url);
        String result = "";
        try {
            if (httpClient != null) {
                result = NetUtils.execute(httpClient, getRequst);
            }
        } catch (ConnectTimeoutException e) {
            throw e;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }

        return result;
    }

    public static String httpPost(Context context, String url, Bundle postParams)
            throws IOException {
        LogUtils.logd(url);
        LogUtils.logd(postParams.toString());
        HttpClient httpClient = null;
        String result = "";
        try {
            httpClient = NetUtils.getHttpClient(context);
        } catch (IOException e1) {
            e1.printStackTrace();
            throw e1;
        }
        HttpPost postRequest = new HttpPost(url);
        MultipartEntity multipartContent = buildMultipartEntity(postParams);
        postRequest.setEntity(multipartContent);
        try {
            if (httpClient != null) {
                result = NetUtils.execute(httpClient, postRequest);
            }
        } catch (ConnectTimeoutException e) {
            throw e;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }

        return result;
    }

    public static Bitmap httpGetToBitmap(Context context, String url) throws IOException {
        HttpClient httpClient = null;
        try {
            httpClient = NetUtils.getHttpClient(context);
        } catch (IOException e1) {
            e1.printStackTrace();
            throw e1;
        }
        HttpGet getRequst = new HttpGet(url);
        try {
            if (httpClient != null) {
                return NetUtils.executeToBitmap(httpClient, getRequst);
            }
        } catch (ConnectTimeoutException e) {
            throw e;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return null;
    }

    public static HttpClient getHttpClient(Context context) throws IOException {
        NetworkState state = NetUtils.getNetworkState(context);
        HttpClient client = new DefaultHttpClient();
        // String product = Build.PRODUCT;
        if (state == NetworkState.NOTHING) {
            throw new IOException();
        }

        HttpConnectionParamBean paramHelper = new HttpConnectionParamBean(client.getParams());
        paramHelper.setSoTimeout(TIME_OUT_DELAY);
        paramHelper.setConnectionTimeout(TIME_OUT_DELAY);
        return client;
    }

    // 判断当前的网络的类型
    public static NetworkState getNetworkState(Context ctx) {
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            return NetworkState.NOTHING;
        } else {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return NetworkState.MOBILE;
            } else {
                return NetworkState.WIFI;
            }
        }
    }

    public static byte[] executeNetRequest(HttpClient client, HttpUriRequest request)
            throws IOException, ClientProtocolException {
        try {
            HttpResponse response = null;
            try {
                response = client.execute(request);
            } catch (NullPointerException e) {
                // google issue, doing this to work around
                response = client.execute(request);
            }
            StatusLine status = response.getStatusLine();
            int statusCode = status.getStatusCode();
            if (statusCode != HTTP_STATUS_OK) {
                throw new IOException(String.format("Invalid response from server: %s",
                        status.toString()));
            }
            // int length = -1;
            // Header lengthHeader = response.getFirstHeader("Content-Length");
            // if (lengthHeader != null) {// 获得下载文件长度
            // length = Integer.valueOf(lengthHeader.getValue());
            // }
            // Pull content stream from response
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            ByteArrayOutputStream content = new ByteArrayOutputStream();
            // Read response into a buffered stream
            int readBytes = 0;
            byte[] buffer = new byte[512];
            while ((readBytes = inputStream.read(buffer)) != -1) {
                content.write(buffer, 0, readBytes);
            }
            // Return result from buffered stream
            return content.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            if (client != null) {
                client.getConnectionManager().shutdown();
            }
        }
    }

    public static String execute(HttpClient client, HttpUriRequest request) throws IOException,
            ClientProtocolException {
        return new String(executeNetRequest(client, request));
    }

    public static Bitmap executeToBitmap(HttpClient client, HttpUriRequest request)
            throws IOException, ClientProtocolException {
        byte[] bytes = executeNetRequest(client, request);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private static MultipartEntity buildMultipartEntity(Bundle params)
            throws UnsupportedEncodingException {
        MultipartEntity multipartContent = new MultipartEntity();

        for (String key : params.keySet()) {
            if (TYPE_FILE_NAME.equals(key) || GZIP_FILE_NAME.equals(key)) {
                Object fileNames = params.get(key);
                if (fileNames != null && fileNames instanceof Bundle) {
                    Bundle pathBundle = (Bundle) fileNames;
                    // StringBuffer data = new StringBuffer();
                    for (String uploadFileKey : pathBundle.keySet()) {
                        String filePath = pathBundle.getString(uploadFileKey);
                        if (filePath != null) {
                            final File file = new File(filePath);
                            if (file.exists()) {
                                FileBody bin;
                                if (TYPE_FILE_NAME.equals(key)) {
                                    bin = new FileBody(file, "image/jpeg");
                                } else {
                                    bin = new FileBody(file, "application/zip");
                                }
                                multipartContent.addPart(uploadFileKey, bin);
                            }
                        }
                    }
                }
            } else {
                Object objValue = params.get(key);

                if (objValue != null && objValue instanceof byte[]) {
                    byte[] bytesValue = (byte[]) objValue;

                    ByteArrayBody body = new ByteArrayBody(bytesValue, null);

                    multipartContent.addPart(key, body);
                } else {
                    Object value = params.get(key);

                    value = (value == null ? "" : value);

                    StringBody body =
                            new StringBody(String.valueOf(value), Charset.forName(HTTP.UTF_8));
                    multipartContent.addPart(URLEncoder.encode(key, HTTP.UTF_8), body);
                }
            }
        }

        return multipartContent;
    }

}
