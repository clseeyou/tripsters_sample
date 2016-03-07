package com.tripsters.sample.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;

import com.tripsters.android.model.MediaInfo;
import com.tripsters.android.util.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryHelper {

    private static final Uri IMAGE_URI = Images.Media.EXTERNAL_CONTENT_URI;
    private static final String IMAGE_ORDER_BY = ImageColumns.DATE_TAKEN + " DESC";

    private static String[] IMAGE_SELECTION = {ImageColumns._ID, ImageColumns.DATA,
            ImageColumns.DATE_TAKEN};

    /**
     * 返回所有视频和图片
     */
    public static List<MediaInfo> getAllMedia(Context context) {
        List<MediaInfo> mediaInfos = new ArrayList<MediaInfo>();

        mediaInfos.addAll(getAllImage(context));
        // mediaInfos.addAll(getAllVideo(context));
        Collections.sort(mediaInfos);

        return mediaInfos;
    }

    /**
     * 返回所有图片
     */
    public static List<MediaInfo> getAllImage(Context context) {
        return getImage(context, 0, true);
    }

    private static List<MediaInfo> getImage(Context context, int bucketid, boolean isAll) {
        List<MediaInfo> mediaInfos = new ArrayList<MediaInfo>();

        String where = isAll ? null : ImageColumns.BUCKET_ID + " = ?";
        String[] selectionArgs = isAll ? null : new String[]{String.valueOf(bucketid)};
        try {
            Cursor cursor =
                    context.getContentResolver().query(IMAGE_URI, IMAGE_SELECTION, where,
                            selectionArgs, IMAGE_ORDER_BY);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                MediaInfo mediaInfo = cursor2ImageInfo(cursor);
                // 检测文件是否存在，防止文件被删除，图库数据未更新
                if (mediaInfo != null) {
                    mediaInfos.add(mediaInfo);
                }
            }
            cursor.close();
        } catch (SQLiteException e) {
            // 某些系统图片数据库被锁问题，用户自己刷机问题，仅保证不崩
            // android.database.sqlite.SQLiteException: database is locked
        }

        return mediaInfos;
    }

    private static MediaInfo cursor2ImageInfo(Cursor cursor) {
        try {
            String imgPath = cursor.getString(cursor.getColumnIndex(ImageColumns.DATA));
            // 检测文件是否存在，防止文件被删除，图库数据未更新
            if (FileUtils.doesExisted(imgPath)) {
                MediaInfo imageInfo = new MediaInfo();
                imageInfo.setType(MediaInfo.PIC);
                imageInfo.setPath(imgPath);
                imageInfo.setDateTaken(cursor.getLong(cursor
                        .getColumnIndex(ImageColumns.DATE_TAKEN)));
                // LogUtils.logd("image dateTaken = " + imageInfo.getDateTaken());

                return imageInfo;
            }
        } catch (Exception e) {
            // 没有相应Column，系统的 不保证
            LogUtils.loge(e);
        }

        return null;
    }
}
