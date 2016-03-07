package com.tripsters.sample.util;

import android.content.Context;

import com.tripsters.sample.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatDateFromCreated(Context context, long created) {
        return formatDate(context, new Date(created * 1000));
    }

    public static String formatDate(Context context, Date date) {
        if (date == null) {
            return "";
        }

        long delta = new Date().getTime() - date.getTime();

        if (delta > 86400000) {
            return formatTime(date.getTime() / 1000);
        } else {
            int value;
            String format = "%d%s";

            if (delta > 3600000) {
                value = (int) (delta / 3600000);

                if (value > 1) {
                    return String.format(format, value, context.getString(R.string.hour_label_plural));
                } else {
                    return String.format(format, value, context.getString(R.string.hour_label));
                }
            } else if (delta > 60000) {
                value = (int) (delta / 60000);

                if (value > 1) {
                    return String.format(format, value, context.getString(R.string.minute_label_plural));
                } else {
                    return String.format(format, value, context.getString(R.string.minute_label));
                }
            } else {
                return String.format(format, 1, context.getString(R.string.minute_label));
            }
        }
    }

    public static String formatTime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        return simpleDateFormat.format(new Date(time * 1000L));
    }
}
