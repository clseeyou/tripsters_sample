package com.tripsters.sample.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.tripsters.android.model.City;
import com.tripsters.android.model.Country;
import com.tripsters.sample.R;
import com.tripsters.sample.model.PinyinCity;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static DisplayMetrics getWindowRect(Context context) {
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        return metrics;
    }

    public static Country getThailaid() {
        Country country = new Country();

        country.setId(1);
        country.setCountryNameCn("泰国");
        country.setCountryNameEn("Thailand");
        country.setCountryNameEn("ประเทศไทย");
        country.setCountryCode("th");
        country.setHot(2);

        return country;
    }

    public static List<PinyinCity> initPinyin(Context context, List<City> cities) {
        if (cities == null) {
            return null;
        }

        List<PinyinCity> pinyinCities = new ArrayList<PinyinCity>();

        for (City city : cities) {
            PinyinCity pinyinCity = new PinyinCity(city,
                    getPinyin(context, city.getCityNameCn()).toUpperCase());
            pinyinCities.add(pinyinCity);
        }

        return pinyinCities;
    }

    public static String getPinyin(Context context, String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }

        return PinyinUtils.getInstance(context).getPinyin(text);
    }

    /**
     * 编辑框字数提示
     *
     * @param context
     * @param text
     * @param maxSize
     */
    public static SpannableString getLimitNum(Context context, CharSequence text, int maxSize) {
        text = TextUtils.isEmpty(text) ? "" : text;
        String s =
                String.format(context.getString(R.string.input_length_last),
                        (maxSize - text.length() + ""), (maxSize + ""));
        SpannableString msp = new SpannableString(s);
        msp.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tb_orange)), 0,
                (maxSize - text.length() + "").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为洋红色

        return msp;
    }
}
