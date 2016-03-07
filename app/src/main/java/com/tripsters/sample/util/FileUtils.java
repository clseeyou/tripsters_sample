package com.tripsters.sample.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.tripsters.android.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    private static final int MIN_SDCARD_SPACE = 1024 * 1024 * 10; // sd卡空间如果小于10M，就认为sd卡空间不足

    /**
     * Check if the primary "external" storage device is available.
     */
    public static boolean hasSDCardMounted() {
        String state = Environment.getExternalStorageState();

        return state != null && state.equals(Environment.MEDIA_MOUNTED);
    }

    @SuppressWarnings("deprecation")
    public static boolean haveFreeSpace() {
        if (hasSDCardMounted()) {
            StatFs st = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long blockSize = st.getBlockSize();
            long available = st.getAvailableBlocks();
            long availableSize = (blockSize * available);

            return availableSize >= MIN_SDCARD_SPACE;
        }

        return false;
    }

    public static File getUriFile(String filePath, Context context) {
        Uri uri = Uri.parse(filePath);
        File file = null;

        if (uri.getScheme() != null && uri.getScheme().equals("content")) {
            try {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor =
                        context.getContentResolver().query(uri, proj, null, null, null);
                int actual_image_column_index =
                        actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                if (actualimagecursor.moveToFirst()) {
                    String img_path = actualimagecursor.getString(actual_image_column_index);
                    file = new File(img_path);
                }

                actualimagecursor.close();
            } catch (Exception e) {
                LogUtils.loge(e);
            }
        } else if (uri.getScheme() != null && uri.getScheme().equals("file")) {
            file = new File(uri.getPath());
        } else {
            file = new File(filePath);
        }

        return file;
    }

    public static boolean doesExisted(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return false;
        }

        File file = new File(fileName);
        return file.exists();
    }

    public static long getFileSize(String fileName) {
        File file = new File(fileName);
        return file.length();
    }

    public static boolean copy(InputStream is, OutputStream os) {
        try {
            int bytes;
            byte[] buffer = new byte[4 * 1024]; // 4K buffer

            while ((bytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytes);
            }

            os.flush();
        } catch (IOException e) {
            LogUtils.loge(e);

            return false;
        } finally {
            closeStream(is);
            closeStream(os);
        }

        return true;
    }

    public static void closeStream(InputStream is) {
        try {
            is.close();
        } catch (IOException e) {
            LogUtils.loge(e);
        }
    }

    public static void closeStream(OutputStream os) {
        try {
            os.close();
        } catch (IOException e) {
            LogUtils.loge(e);
        }
    }
}
