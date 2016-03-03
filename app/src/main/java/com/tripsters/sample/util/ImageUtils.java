package com.tripsters.sample.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tripsters.android.model.Gender;
import com.tripsters.android.model.Identity;
import com.tripsters.android.model.MediaInfo;
import com.tripsters.android.model.PicInfo;
import com.tripsters.android.model.UserInfo;
import com.tripsters.sample.R;
import com.tripsters.sample.view.PortraitView;

import java.io.File;

public class ImageUtils {

    public static final int[] BG_DEFAULTS = {R.color.bg_default_1, R.color.bg_default_2,
            R.color.bg_default_3, R.color.bg_default_4, R.color.bg_default_5, R.color.bg_default_6,
            R.color.bg_default_7, R.color.bg_default_8};

    /**
     * 显示图片
     *
     * @param context
     * @param imageView 图片ImageView
     * @param picInfo 图片对象
     * @param picType 图片对象类型
     */
    public static void setImage(Context context, ImageView imageView, PicInfo picInfo,
                                PicInfo.PicType picType, Rect rect) {
        setImage(context, imageView, picInfo, picType, rect, R.color.tb_bg_grey);
    }

    /**
     * 显示图片
     *
     * @param context
     * @param imageView 图片ImageView
     * @param picInfo 图片对象
     * @param picType 图片对象类型
     */
    public static void setImage(Context context, ImageView imageView, PicInfo picInfo,
                                PicInfo.PicType picType, Rect rect, int defaultResId) {
        PicInfo.Pic pic = picType.getPic(picInfo);

        if (pic == null || TextUtils.isEmpty(pic.getPic())) {
            imageView.setImageResource(defaultResId);
        } else {
            Picasso.with(context).load(pic.getPic()).placeholder(defaultResId)
                    .resize(rect.width(), rect.height()).centerCrop().into(imageView);
        }
    }

    /**
     * 显示图片
     *
     * @param context
     * @param imageView 图片ImageView
     * @param mediaInfo 图片对象
     */
    public static void setImage(Context context, ImageView imageView, MediaInfo mediaInfo, Rect rect) {
        setImage(context, imageView, mediaInfo, rect, R.color.tb_bg_grey);
    }

    /**
     * 显示图片
     *
     * @param context
     * @param imageView 图片ImageView
     * @param mediaInfo 图片对象
     */
    public static void setImage(Context context, ImageView imageView, MediaInfo mediaInfo,
                                Rect rect, int placeholderResId) {
        if (TextUtils.isEmpty(mediaInfo.getPath())) {
            // imageView.setImageResource(R.drawable.image_default);
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);

            if (mediaInfo.getType() == MediaInfo.PIC) {
                Picasso.with(context).load(new File(mediaInfo.getPath()))
                        .placeholder(placeholderResId).resize(rect.width(), rect.height())
                        .centerCrop().into(imageView);
            } else {
                imageView.setImageBitmap(getVideoFrame(mediaInfo.getPath()));
            }
        }
    }

    public static Bitmap getVideoFrame(String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        // // 取得视频的长度(单位为毫秒)
        // String time =
        // retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        // 取得视频的长度(单位为秒)
        Bitmap bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);

        return bitmap;
    }

    /**
     * 显示国家图
     *
     * @param context context
     * @param imageView imageview
     * @param url 缩略图Url
     * @param position index
     */
    public static void setCountryPic(Context context, ImageView imageView, String url, int position) {
        DisplayMetrics dm = Utils.getWindowRect(context);
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width = dm.widthPixels;
        params.height = dm.widthPixels * 300 / 640;
        imageView.setLayoutParams(params);

        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(BG_DEFAULTS[position % BG_DEFAULTS.length]);
        } else {
            Picasso.with(context).load(url).placeholder(BG_DEFAULTS[position % BG_DEFAULTS.length])
                    .resize(params.width, params.height).centerCrop().into(imageView);
        }
    }

    /**
     * 设置帖子评论图片
     *
     * @param context
     * @param ivPic
     * @param picInfo
     * @param picType
     */
    public static void setDataBottomPic(Context context, ImageView ivPic, PicInfo picInfo,
                                        PicInfo.PicType picType) {
        ViewGroup.LayoutParams params = ivPic.getLayoutParams();
        params.width = context.getResources().getDimensionPixelSize(R.dimen.blog_comment_pic_size);
        params.height = context.getResources().getDimensionPixelSize(R.dimen.blog_comment_pic_size);
        ivPic.setLayoutParams(params);

        setImage(context, ivPic, picInfo, picType,
                new Rect(0, 0, params.width, params.height));
    }

    /**
     * 显示限制大小的图片
     *
     * @param context
     * @param imageView 图片ImageView
     * @param picInfo 图片对象
     * @param picType 图片对象类型
     */
    public static void setResizeImage(Context context, ImageView imageView, PicInfo picInfo,
                                      PicInfo.PicType picType) {
        PicInfo.Pic pic = picType.getPic(picInfo);

        if (pic == null || TextUtils.isEmpty(pic.getPic())) {
            // imageView.setImageResource(R.drawable.image_default);
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);

            Rect rect = getImageRect(context, pic);

            int minSize = context.getResources().getDimensionPixelSize(R.dimen.image_min_size);
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.width = rect.width() < minSize ? minSize : rect.width();
            params.height = rect.height() < minSize ? minSize : rect.height();
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_START);

            Picasso.with(context).load(pic.getPic()).placeholder(R.color.tb_bg_grey)
                    .resize(rect.width(), rect.height()).centerCrop().into(imageView);
        }
    }

    /**
     * 显示头像
     *
     * @param context
     * @param imageview 头像ImageView
     * @param url 头像Url
     */
    public static void setAvata(Context context, ImageView imageview, String url, boolean female) {
        if (TextUtils.isEmpty(url)) {
            imageview.setImageResource(female ? R.drawable.portrait_default_female
                    : R.drawable.portrait_default_male);
        } else {
            Picasso.with(context)
                    .load(url)
                    .placeholder(
                            female ? R.drawable.portrait_default_female
                                    : R.drawable.portrait_default_male).into(imageview);
        }
    }

    /**
     * 显示头像
     *
     * @param context
     * @param imageview 头像ImageView
     * @param url 头像Url
     */
    public static void setAvata(Context context, ImageView imageview, String url, int defaultResId) {
        if (TextUtils.isEmpty(url)) {
            imageview.setImageResource(defaultResId);
        } else {
            Picasso.with(context).load(url).placeholder(defaultResId).into(imageview);
        }
    }

    /**
     * 显示头像
     *
     * @param context
     * @param portraitView 头像View
     * @param url 头像Url
     */
    public static void setAvata(Context context, PortraitView portraitView, String url,
                                Gender gender, Identity identity) {
        if (TextUtils.isEmpty(url)) {
            portraitView.setDeault(gender == Gender.FEMALE);
        } else {
            portraitView.setPortrait(url, gender == Gender.FEMALE, identity);
        }
    }

    /**
     * 显示头像
     *
     * @param context
     * @param portraitView 头像View
     * @param url 头像Url
     */
    public static void setAvata(Context context, PortraitView portraitView, String url,
                                Identity identity, int defaultResId) {
        if (TextUtils.isEmpty(url)) {
            portraitView.setDeault(defaultResId);
        } else {
            portraitView.setPortrait(url, defaultResId, identity);
        }
    }

    /**
     * 显示头像
     *
     * @param context
     * @param portraitView
     * @param userInfo
     */
    public static void setAvata(Context context, PortraitView portraitView, UserInfo userInfo) {
        if (userInfo == null || TextUtils.isEmpty(userInfo.getAvatar())) {
            portraitView.setDeault(userInfo == null || userInfo.getGender() == Gender.FEMALE);
        } else {
            portraitView.setPortrait(userInfo.getAvatar(), userInfo.getGender() == Gender.FEMALE,
                    Identity.getFromUser(userInfo));
        }
    }

    private static Rect getImageRect(Context context, PicInfo.Pic pic) {
        DisplayMetrics metrics = Utils.getWindowRect(context);
        int maxSize;

        switch (pic.getType()) {
            case ORI:
            case BIG:
                maxSize = metrics.widthPixels;
                break;
            case MIDDLE:
                maxSize =
                        context.getResources().getDimensionPixelSize(R.dimen.image_middle_max_size);
                break;
            case SMALL:
                maxSize =
                        context.getResources().getDimensionPixelSize(R.dimen.image_small_max_size);
                break;
            default:
                maxSize = metrics.widthPixels;
                break;
        }

        int width = (int) (pic.getWidth() * metrics.density);
        int height = (int) (pic.getHeight() * metrics.density);

        if (width > 0 && height > 0) {
            if (width >= height) {
                if (width > maxSize) {
                    height = height * maxSize / width;
                    width = maxSize;
                }
            } else {
                if (height > maxSize) {
                    width = width * maxSize / height;
                    height = maxSize;
                }
            }
        } else {
            width = maxSize;
            height = maxSize;
        }

        return new Rect(0, 0, width, height);
    }
}
