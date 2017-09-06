package com.laonie.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.laonie.common.functions.Action1;

import java.io.File;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2016 12 03 11:25
 * @DESC 加载图片帮助类
 */
public class ImageLoader {
    private static String TAG = ImageLoader.class.getSimpleName();

    public static final String AVATAR_PIC_FILE = Environment.getExternalStorageDirectory() + "/cache/img/" + "avatar.jpg";

    /**
     * 加载图片（圆形图片）
     *
     * @param imageView
     * @param imageUrl 附件id
     * @param dftResourdeId 默认显示图片资源id
     */
    public static void loadCircleImageView(Context context, ImageView imageView, String imageUrl, int dftResourdeId, boolean isDiskCache,boolean isSkipMemoryCache) {
        if (TextUtils.isEmpty(imageUrl)) {
            loadResourceImage(context,dftResourdeId,imageView);
            return;
        }
        Logger.i("圆形图片的链接:" + imageUrl);
        Glide.with(context).load(imageUrl).error(dftResourdeId).placeholder(dftResourdeId).dontAnimate().bitmapTransform(new CropCircleTransformation(context)).diskCacheStrategy(isDiskCache ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE).skipMemoryCache(isSkipMemoryCache).crossFade(1000).into(imageView);
}


    public static void loadCircleImageView(Context context, ImageView imageView, String attachementId, int dftResourdeId) {
        loadCircleImageView(context, imageView, attachementId, dftResourdeId, true,false);
    }

    /**
     * 加载本地文件图片
     * @param context
     * @param imageView
     * @param fileUrl
     */
    public static void loadFileImage(Context context,ImageView imageView,String fileUrl) {
        try {
            File file = new File(fileUrl);
            if (file.exists()) {
                Glide.with(context).load(file).asBitmap().into(imageView);
            }
        }catch (Exception e) {
            Logger.e(TAG,"============加载本地图片异常" + e.getMessage());
        }
    }


    /**
     * 加载图片
     *
     * @param imageView
     * @param imageUrl 附件地址
     * @param dftResourdeId 默认显示图片资源id
     */
    public static void loadImage(Context context, ImageView imageView, String imageUrl, int dftResourdeId) {
        if (imageUrl == null) {
            loadResourceImage(context,dftResourdeId,imageView);
            return;
        }
        RequestManager with;
        if (context instanceof Activity) {
            with = Glide.with((Activity) context);
        } else {
            with = Glide.with(context);
        }
        Logger.i("商家详情图片链接:" + imageUrl);
        with.load(imageUrl).error(dftResourdeId).placeholder(dftResourdeId).crossFade(1000).dontAnimate().into(imageView);
    }

    /**
     * 加载资源文件
     *
     * @param context
     * @param resourceId
     * @param imageView
     */
    public static void loadResourceImage(Context context, int resourceId, ImageView imageView) {
        Glide.with(context).load(resourceId).dontAnimate().into(imageView);
    }


    /**
     * 加载gif图片
     *
     * @param context
     * @param resourceId
     * @param imageView
     */
    public static void loadResourceGifImage(Context context, int resourceId, ImageView imageView) {
        Glide.with(context).load(resourceId).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

    public static void loadResourceCircleImage(Context context, int resourceId, ImageView imageView) {
        Glide.with(context).load(resourceId).bitmapTransform(new CropCircleTransformation(context)).dontAnimate().into(imageView);
    }


    /**
     * 加载图片
     *
     * @param imageView
     * @param imageUrl 附件地址
     */
    public static void loadImage(Context context, ImageView imageView, String imageUrl) {
        if (StringUtils.isEmpty(imageUrl)) {
            return;
        }
        //Logger.e(TAG, "fileUrl：" + imageUrl);
        Glide.with(context).load(imageUrl).crossFade(1000).dontAnimate().into(imageView);
    }

    /**
     * 下载网络图片
     *
     * @param context
     * @param imageUrl
     * @param callback
     */
    public static void downLoadImage(Context context, String imageUrl, final Action1<Bitmap> callback) {
        if (StringUtils.isEmpty(imageUrl)) {
            callback.call(null);
            return;
        }
        //Logger.e(TAG, "fileUrl：" + imageUrl);
        Glide.with(context).load(imageUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                callback.call(resource);
            }
        });
    }


    /**
     * 清除当前生命周期内context的glide内存缓存
     *
     * @param context
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 清除当前生命周期内context的glide磁盘缓存
     *
     * @param context
     */
    public static void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }
}
