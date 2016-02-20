package com.tripsters.android.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tripsters.android.util.LogUtils;

public class ModelFactory {

    private static ModelFactory sInstance;

    private ModelFactory() {

    }

    public static ModelFactory getInstance() {
        if (sInstance == null) {
            synchronized (ModelFactory.class) {
                if (sInstance == null) {
                    sInstance = new ModelFactory();
                }
            }
        }

        return sInstance;
    }

    public <T> T create(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, classOfT);
        } catch (JsonSyntaxException e) {
            LogUtils.loge(e);

            try {
                return classOfT.newInstance();
            } catch (InstantiationException e1) {
                LogUtils.loge(e1);

                return null;
            } catch (IllegalAccessException e1) {
                LogUtils.loge(e1);

                return null;
            }
        }
    }

    // public <T> T create(JsonObject jsonObject, Class<T> classOfT) {
    // Gson gson = new Gson();
    // return gson.fromJson(jsonObject, classOfT);
    // }
}
