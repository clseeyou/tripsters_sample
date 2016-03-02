package com.tripsters.sample.util;

import android.content.Context;

import com.tripsters.sample.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatDate2(Context ctx, Date date) {
        if (date == null) {
            return "";
        }
        long delta = new Date().getTime() - date.getTime();
        int value = 0;
        String f = "%d%s";
        if (delta > 86400000) {
            return formatTime2(date.getTime() / 1000);
            // return getDateFormat().format(date);
        } else {
            if (delta > 3600000) {
                value = (int) (delta / 3600000);
                if (value > 1) {
                    return String.format(f, value, ctx.getString(R.string.hour_label_plural));
                } else {
                    return String.format(f, value, ctx.getString(R.string.hour_label));
                }
            } else if (delta > 60000) {
                value = (int) (delta / 60000);
                if (value > 1) {
                    return String.format(f, value, ctx.getString(R.string.minute_label_plural));
                } else {
                    return String.format(f, value, ctx.getString(R.string.minute_label));
                }
            } else {
                return String.format(f, 1, ctx.getString(R.string.minute_label));
            }
        }
    }

    public static String formatTime2(long cc_time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String re_StrTime = sdf.format(new Date(cc_time * 1000L));

        return re_StrTime;
    }
}
