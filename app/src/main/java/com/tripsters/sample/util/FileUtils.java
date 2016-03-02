package com.tripsters.sample.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.tripsters.android.util.LogUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    public static final int PIC_SMALL = 1;
    public static final int PIC_MIDDLE = 2;
    public static final int PIC_LARGE = 3;
    public static final int PIC_ORIGINAL = 4;

    private static final String SD_ROOT = "/tripsters";
    private static final String PIC_DIR_PATH = "/pic/";
    private static final String SAVE_DIR_PATH = "/";
    private static final int MIN_SDCARD_SPACE = 1024 * 1024 * 10; // sd卡空间如果小于10M，就认为sd卡空间不足

    /**
     * Check if the primary "external" storage device is available.
     * 
     * @return
     */
    public static boolean hasSDCardMounted() {
        String state = Environment.getExternalStorageState();
        if (state != null && state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static String printSDCardRoot() {
        return Environment.getExternalStorageDirectory().toString();
    }

    public static boolean haveFreeSpace() {
        if (hasSDCardMounted()) {
            StatFs st = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long blockSize = st.getBlockSize();
            long available = st.getAvailableBlocks();
            long availableSize = (blockSize * available);
            if (availableSize < MIN_SDCARD_SPACE) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static File getUriFile(String filePath, Context context) {
        Uri uri = Uri.parse(filePath);
        File file = null;
        if (uri != null && uri.getScheme() != null && uri.getScheme().equals("content")) {
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
            } catch (Exception e) {

            }
        } else if (uri != null && uri.getScheme() != null && uri.getScheme().equals("file")) {
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

    public static String createDefaultTempPath(Context context) {
        return getPicDir(context) + "temp_" + String.valueOf(System.currentTimeMillis());
    }

    public static String createCropPicPath(Context context) {
        return getSaveDir(context) + "crop_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
    }

    public static String createSavePicPath(Context context) {
        return getSaveDir(context) + "img_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
    }

    public static String createVideoCacheDir(Context context) {
        return getSaveDir(context) + "video_cache" + SAVE_DIR_PATH;
    }

    public static String createVideoDir(Context context) {
        return getSaveDir(context)/* + "video" + SAVE_DIR_PATH*/;
    }

    public static String createVideoPath(Context context) {
        return getSaveDir(context) + "video_" + String.valueOf(System.currentTimeMillis()) + ".mp4";
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

    public static void deleteDependon(String fileName) {
        File file = new File(fileName);
        file.delete();
    }

    public static String getPicDir(Context context) {
        String dir;

        if (hasSDCardMounted() && haveFreeSpace()) {
            // sdcard is mounted and it's space is enough
            dir = printSDCardRoot() + SD_ROOT + PIC_DIR_PATH;
        } else {
            dir = context.getFilesDir().getAbsolutePath() + PIC_DIR_PATH;
        }

        if (!doesExisted(dir)) {
            new File(dir).mkdirs();
        }

        return dir;
    }

    public static String getSaveDir(Context context) {
        String dir;

        if (hasSDCardMounted() && haveFreeSpace()) {
            // sdcard is mounted and it's space is enough
            dir = printSDCardRoot() + SD_ROOT + SAVE_DIR_PATH;
        } else {
            dir = context.getFilesDir().getAbsolutePath() + SAVE_DIR_PATH;
        }

        if (!doesExisted(dir)) {
            new File(dir).mkdirs();
        }

        return dir;
    }

    public static void createFile(String fileName) throws IOException {
        File file = new File(fileName);
        File dir = file.getParentFile();

        if (!dir.exists()) {
            dir.mkdirs();
        }

        file.createNewFile();
    }
//
//    public static void importBitmapFile(Context context, String defaultTempPath, Uri uri,
//            boolean isDeleteTmp) throws IOException {
//        if (!doesExisted(defaultTempPath)) {
//            createFile(defaultTempPath);
//        }
//        // 注意 copy 可能消耗很多时间。这主要取决于文件的大小
//
//        // 如果是从数据库提取数据
//        if (uri.getScheme().equals("content")) {
//            copy(context.getContentResolver().openInputStream(uri), new FileOutputStream(
//                    defaultTempPath));
//        }
//        // 如果是从文件系统提取数据
//        else if (uri.getScheme().equals("file")) {
//            copy(new FileInputStream(uri.getPath()), new FileOutputStream(defaultTempPath));
//            // copy 成功的话立刻删除 SDCard 上的缓存文件，节省空间
//            // 如果 copy 失败则不作任何处理
//            if (isDeleteTmp) {
//                deleteDependon(uri.getPath());
//            }
//        }
//        if (!verifyBitmap(defaultTempPath)) {
//            deleteDependon(defaultTempPath);
//            throw new IOException("\t 不是有效图片格式");
//        }
//
//        // 保证上传图片不会太大
//        AdjustImageSizeUtil.revitionSaveImageSize(defaultTempPath);
//    }

    /**
     * 检测是否可以解析成位图
     * 
     * @param input
     * @return
     */
    public static boolean verifyBitmap(InputStream input) {
        if (input == null) {
            return false;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        input = input instanceof BufferedInputStream ? input : new BufferedInputStream(input);
        BitmapFactory.decodeStream(input, null, options);
        closeStream(input);
        LogUtils.logd("\t outHeight:" + options.outHeight + "\t outWidth:" + options.outWidth);
        return (options.outHeight > 0) && (options.outWidth > 0);
    }

    /**
     * 检测是否可以解析成位图
     * 
     * @param path
     * @return
     */
    public static boolean verifyBitmap(String path) {
        try {
            return verifyBitmap(new FileInputStream(path));
        } catch (final FileNotFoundException e) {
            LogUtils.loge(e);
        }
        return false;
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

//    public static String getLocalPath(Context context, String url, int type) {
//        // int end = url.lastIndexOf(".");
//        // String path = end == -1 ? url : url.substring(0, end);
//        // int start = path.lastIndexOf("/");
//        // path = start == -1 ? path : path.substring(start + 1);
//        String path = MD5.hexdigest(url);
//
//        return getPicDir(context) + type + "/" + path;
//    }

    public static boolean saveImage(Context context, String path, Bitmap bitmap) {
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

            return true;
        } catch (FileNotFoundException e) {
            LogUtils.loge(e);
        } catch (IOException e) {
            LogUtils.loge(e);
        } finally {
            if (out != null) {
                closeStream(out);
            }
        }

        return false;
    }

    public static void delete(String path) {
        delete(new File(path));
    }

    public static boolean delete(File f) {
        if (f != null && f.exists()) {
            return f.delete();
        }


        return false;
    }

    public static void deleteFiles(String path) {
        deleteFiles(new File(path));
    }

    public static void deleteFiles(File file_) {
        if ((file_.exists()) && (file_.isDirectory())) {
            File[] childrenFile = file_.listFiles();
            if (childrenFile != null) {
                for (File f : childrenFile) {
                    if (f.isFile()) {
                        f.delete();
                    } else if (f.isDirectory()) {
                        deleteFiles(f);
                    }
                }
            }
            file_.delete();
        } else if ((file_.exists()) && (file_.isFile())) {
            file_.delete();
        }
    }
}
