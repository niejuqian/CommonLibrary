package com.laonie.common.scan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2016 12 19 10:07
 * @DESC：
 *      创建二维码
 */
public class CreateQrImage {
    private final static String TAG = CreateQrImage.class.getSimpleName();
    /**
     * 二维码默认颜色 黑色
     */
    public static final String DEFAULT_COLOR = "#000000";
    /**
     * 成功
     */
    public static final String SUCCESS = "success";
    /**
     * 失败
     */
    public static final String ERROR = "error";

    /**
     * 生成不带logo的二维码
     * @param context 上下文
     * @param content 内容
     * @param callback 返回
     */
    public static void createQrImage(Context context, String content, QrImgCallback2<Bitmap> callback) {
        createQrImage(context, content, DEFAULT_COLOR, callback);
    }

    /**
     * 生成不带logo的二维码
     * @param context 上下文
     * @param content 内容
     * @param imageView 显示二维码的view
     * @param callback 回调 success-成功 error-失败
     * @return
     */
    public static void createQrImage(Context context, String content, ImageView imageView, QrImgCallback<String> callback) {
        createQrImage(context, content, DEFAULT_COLOR, imageView, callback);
    }

    /**
     * 生成不带logo的二维码
     * @param context 上下文
     * @param content 内容
     * @param imageView 显示二维码的view
     * @return
     */
    public static void createQrImage(Context context, String content, ImageView imageView) {
        createQrImage(context, content, DEFAULT_COLOR, imageView);
    }

    /**
     * 生成带logo的二维码
     * @param context
     * @param content
     * @param logoResId
     * @param imageView
     * @param callback
     */
    public static void createQrImageWithLogo(Context context, String content,int logoResId,ImageView imageView, QrImgCallback<String> callback) {
        createQrImageWithLogo(context, content, DEFAULT_COLOR, logoResId, imageView, callback);
    }

    /**
     * 生成带logo的二维码
     * @param context 上下文
     * @param content 内容
     * @param logoResId logo资源
     * @param callback 回调 bitmap
     */
    public static void createQrImageWithLogo(Context context, String content,int logoResId, QrImgCallback<Bitmap> callback) {
        createQrImageWithLogo(context, content, DEFAULT_COLOR, logoResId, callback);
    }

    /**
     * 生成带Logo的二维码（logo使用网络图片替代）
     * @param context 上下文
     * @param content 内容
     * @param fileUrl LogoURL
     * @param logoResId 下载失败时默认Logo资源
     * @param callback 回调 bitmap
     */
    public static void createQrImageWithHttp(Context context,String content,String fileUrl,int logoResId,QrImgCallback<Bitmap> callback) {
        createQrImageWithHttp(context, content, DEFAULT_COLOR, fileUrl, logoResId, callback);
    }

    /**
     * 生成带Logo的二维码（logo使用网络图片替代）
     * @param context 上下文
     * @param content 内容
     * @param fileUrl LogoURL
     * @param logoResId 下载失败时默认Logo资源
     * @param imageView 显示图片的view
     * @param callback 回调 bitmap
     */
    public static void createQrImageWithHttp(Context context,String content,String fileUrl,int logoResId,ImageView imageView,QrImgCallback<String> callback) {
        createQrImageWithHttp(context,content,DEFAULT_COLOR,fileUrl,logoResId,imageView,callback);
    }

    /*----------------------------------------------不带logo的二维码 start-----------------------------------------------------*/


    /**
     * 生成不带logo的二维码
     * @param context 上下文
     * @param content 内容 'http://www.baidu.com'
     * @param color 二维码颜色 '#ffffff'
     * @param callback 返回 bitmap
     */
    public static void createQrImage(final Context context, final String content, final String color, final QrImgCallback2<Bitmap> callback) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(context, 150), Color.parseColor(color));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                callback.call(bitmap,content);
            }
        }.execute();
    }



    /**
     * 生成不带logo的二维码
     * @param context 上下文
     * @param content 内容
     * @param color 颜色 '#000000'
     * @param imageView 显示二维码的view
     * @param callback 回调 success-成功 error-失败
     * @return
     */
    public static void createQrImage(final Context context, final String content, final String color,final ImageView imageView, final QrImgCallback<String> callback) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(context, 150), Color.parseColor(color));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    callback.call(SUCCESS);
                } else {
                    callback.call(ERROR);
                }
            }
        }.execute();
    }

    /**
     * 生成不带logo的二维码
     * @param context 上下文
     * @param content 内容
     * @param color 颜色 '#000000'
     * @param imageView 显示二维码的view
     * @return
     */
    public static void createQrImage(final Context context, final String content, final String color,final ImageView imageView) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(context, 150), Color.parseColor(color));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }.execute();
    }
    /*----------------------------------------------不带logo的二维码 end-----------------------------------------------------*/



    /*----------------------------------------------带logo的二维码 start-----------------------------------------------------*/


    /**
     *
     */
    public static void createQrImageWithLogo(final Context context, final String content, final String color, final int logoResId, final QrImgCallback<Bitmap> callback) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(context.getResources(), logoResId);
                return QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(context, 150), Color.parseColor(color),logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                callback.call(bitmap);
            }
        }.execute();
    }


    /**
     * 生成带logo的二维码
     * @param context 上下文
     * @param content 内容 'http://www.baidu.com'
     * @param color 二维码颜色 '#ffffff'
     * @param logoResId 中间logo资源
     * @param callback 返回 bitmap
     */
    public static void createQrImageWithLogo(final Context context, final String content, final String color, final int logoResId,final ImageView imageView, final QrImgCallback<String> callback) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(context.getResources(), logoResId);
                return QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(context, 150), Color.parseColor(color),logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    if (null != callback){
                        callback.call(SUCCESS);
                    }
                } else {
                    if (null != callback){
                        callback.call(ERROR);
                    }
                }
            }
        }.execute();
    }


    /*----------------------------------------------带logo的二维码 end-----------------------------------------------------*/


    /*----------------------------------------------使用网络图片作为logo start-----------------------------------------------------*/

    /**
     * 生成不带logo的二维码
     * @param context 上下文
     * @param content 内容 'http://www.baidu.com'
     * @param color 二维码颜色 '#ffffff'
     * @param callback 返回 bitmap
     */
    public static void createQrImageWithHttp(final Context context, final String content, final String color, final String fileUrl, final int logoResId, final QrImgCallback<Bitmap> callback) {
        Glide.with(context).load(fileUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap logoBitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                Log.e("image", "download success................................");
                createBitmap(logoBitmap,context, logoResId, content, color, callback);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                Log.e(TAG, "======================下载网络图片失败了");
                createBitmap(null, context, logoResId, content, color, callback);
            }


        });
    }

    private static void createBitmap(Bitmap logoBitmap,Context context, int logoResId, String content, String color, QrImgCallback<Bitmap> callback) {
        if (null == logoBitmap) {
            logoBitmap = BitmapFactory.decodeResource(context.getResources(), logoResId);
        }
        Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(context, 150), Color.parseColor(color), logoBitmap);
        callback.call(bitmap);
    }

    public static void createQrImageWithHttp(final Context context, final String content, final String color, final String fileUrl, final int logoResId, final ImageView imageView, final QrImgCallback<String> callback) {
        Glide.with(context).load(fileUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap logoBitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                Log.e("image", "download success................................");
                createBitmap(logoBitmap,context, logoResId, content, color, imageView, callback);
            }
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                Log.e(TAG,"下载网络图片失败了");
                createBitmap(null,context, logoResId, content, color, imageView, callback);
            }
        });
    }

    private static void createBitmap(Bitmap logoBitmap,Context context, int logoResId, String content, String color, ImageView imageView, QrImgCallback<String> callback) {
        if (null == logoBitmap) {
            logoBitmap = BitmapFactory.decodeResource(context.getResources(), logoResId);
        }
        Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(context, 150), Color.parseColor(color), logoBitmap);
        if (null != bitmap) {
            imageView.setImageBitmap(bitmap);
            if (null != callback){
                callback.call(SUCCESS);
            }
        } else {
            if (null != callback){
                callback.call(ERROR);
            }
        }
    }

    /*----------------------------------------------使用网络图片作为logo end-----------------------------------------------------*/
}
