package com.laonie.common.util;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.laonie.common.view.refresh.AbAppConfig;

import java.lang.reflect.Method;

public class UIUtils {

    public static final int MARGIN_HEIGHT = 50;


    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = AppUtil.getCtx().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(int px) {
        final float scale = AppUtil.getCtx().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static View inflate(int resId) {
        return LayoutInflater.from(AppUtil.getCtx()).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {

        return AppUtil.getCtx().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    public static void setViewUnderline(TextView view) {
        view.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        view.getPaint().setAntiAlias(true);
    }

    public static void setViewUnderline(TextView... views) {
        if (null != views) {
            for (TextView view : views) {
                view.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                view.getPaint().setAntiAlias(true);
            }
        }
    }

    public static void setEditTextHint(EditText editText, int fontSize) {
        Logger.e("size：" + editText.getTextSize() + "，hintSize：" + fontSize);
        CharSequence hint = editText.getHint();
        SpannableString ss = new SpannableString(hint);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(fontSize, true);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }


    public static void showErrMsg(final TextView view, String msg) {
        if (null != view) {
            view.setVisibility(View.VISIBLE);
            view.setText(msg);
            view.postDelayed(() -> view.setVisibility(View.GONE), 1500);
        }
    }

    public static void showErrMsg(final TextView view, String msg, long delayMillis) {
        if (null != view) {
            view.setVisibility(View.VISIBLE);
            view.setText(msg);
            view.postDelayed(() -> view.setVisibility(View.GONE), delayMillis);
        }
    }

    public static void setHeight(View view, int height) {
        if (view.getParent() instanceof RelativeLayout) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.height = height;
            view.setLayoutParams(params);
        } else if (view.getParent() instanceof LinearLayout) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.height = height;
            view.setLayoutParams(params);
        }
    }

    /**
     * titleView专用
     *
     * @param view
     * @param top
     * @param right
     * @param bottom
     * @param left
     */
    public static void setMargin(View view, int top, int right, int bottom, int left) {
        if (view.getParent() instanceof RelativeLayout) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.height = MARGIN_HEIGHT + top;
            params.setMargins(left, top, right, bottom);
            view.setLayoutParams(params);
        } else if (view.getParent() instanceof LinearLayout) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.height = MARGIN_HEIGHT + top;
            params.setMargins(left, top, right, bottom);
            view.setLayoutParams(params);
        }
    }

    public static void setMargin(int top, View view) {
        setMargin(view, top, 0, 0, 0);
    }


    public static void setCommonTitleViewPadding(View view, int top) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.height = top + 80;
        view.setLayoutParams(params);
        view.setPadding(view.getPaddingLeft(), top, view.getPaddingRight(), 0);
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) AppUtil.getCtx()
                .getSystemService(AppUtil.getCtx().WINDOW_SERVICE);

        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) AppUtil.getCtx()
                .getSystemService(AppUtil.getCtx().WINDOW_SERVICE);

        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取 虚拟按键的高度
     * @return
     */
    public static  int getBottomStatusHeight(){
        int totalHeight = getDpi();

        int contentHeight = getScreenHeight();

        return totalHeight  - contentHeight;
    }


    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi(){
        int dpi = 0;
        WindowManager windowManager = (WindowManager) AppUtil.getCtx().getSystemService(AppUtil.getCtx().WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi=displayMetrics.heightPixels;
        }catch(Exception e){
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     *
     * 设置文本颜色
     * @param text 文本
     * @param color 颜色
     * @param start 开始位置
     * @param end 结束位置
     * @return
     */
    public static SpannableStringBuilder setTextColor(String text,int color,int start,int end) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        builder.setSpan(colorSpan,start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static SpannableStringBuilder setTextColor(String text,int color,String...args){
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan colorSpan;
        if (args != null && args.length > 0) {
            int start = 0,end = 0;
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (StringUtils.isNotEmpty(arg)) {
                    start = text.indexOf(arg);
                    if (start <= 0) start = 0;
                    end = start + arg.length();
                    if (end <= 0) end = 0;
                    colorSpan = new ForegroundColorSpan(color);
                    builder.setSpan(colorSpan,start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return builder;
    }

    /**
     * 设置PX padding.
     *
     * @param view the view
     * @param left the left padding in pixels
     * @param top the top padding in pixels
     * @param right the right padding in pixels
     * @param bottom the bottom padding in pixels
     */
    public static void setPadding(View view, int left,
                                  int top, int right, int bottom) {
        int scaledLeft = scaleValue(view.getContext(), left);
        int scaledTop = scaleValue(view.getContext(), top);
        int scaledRight = scaleValue(view.getContext(), right);
        int scaledBottom = scaleValue(view.getContext(), bottom);
        view.setPadding(scaledLeft, scaledTop, scaledRight, scaledBottom);
    }


    /**
     * 描述：根据屏幕大小缩放.
     *
     * @param context the context
     * @return the int
     */
    public static int scaleValue(Context context, float value) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        //为了兼容尺寸小密度大的情况
        if(mDisplayMetrics.scaledDensity > AbAppConfig.UI_DENSITY){
            //密度
            if(mDisplayMetrics.widthPixels > AbAppConfig.UI_WIDTH){
                value = value*(1.3f - 1.0f/mDisplayMetrics.scaledDensity);
            }else if(mDisplayMetrics.widthPixels < AbAppConfig.UI_WIDTH){
                value = value*(1.0f - 1.0f/mDisplayMetrics.scaledDensity);
            }
        }
        return scale(mDisplayMetrics.widthPixels,
                mDisplayMetrics.heightPixels, value);
    }


    /**
     * 描述：根据屏幕大小缩放.
     *
     * @param displayWidth the display width
     * @param displayHeight the display height
     * @param pxValue the px value
     * @return the int
     */
    public static int scale(int displayWidth, int displayHeight, float pxValue) {
        if(pxValue == 0 ){
            return 0;
        }
        float scale = 1;
        try {
            float scaleWidth = (float) displayWidth / AbAppConfig.UI_WIDTH;
            float scaleHeight = (float) displayHeight / AbAppConfig.UI_HEIGHT;
            scale = Math.min(scaleWidth, scaleHeight);
        } catch (Exception e) {
        }
        return Math.round(pxValue * scale + 0.5f);
    }

    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context the context
     * @return mDisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null){
            mResources = Resources.getSystem();

        }else{
            mResources = context.getResources();
        }
        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }

    /**
     * 测量这个view
     * 最后通过getMeasuredWidth()获取宽度和高度.
     * @param view 要测量的view
     * @return 测量过的view
     */
    public static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight,
                    View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 缩放文字大小,这样设置的好处是文字的大小不和密度有关，
     * 能够使文字大小在不同的屏幕上显示比例正确
     * @param textView button
     * @param sizePixels px值
     * @return
     */
    public static void setTextSize(TextView textView, float sizePixels) {
        float scaledSize = scaleTextValue(textView.getContext(),sizePixels);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,scaledSize);
    }


    /**
     * 描述：根据屏幕大小缩放文本.
     *
     * @param context the context
     * @return the int
     */
    public static int scaleTextValue(Context context, float value) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        //为了兼容尺寸小密度大的情况
        if(mDisplayMetrics.scaledDensity > 2){
            //缩小到密度分之一
            //value = value*(1.1f - 1.0f/mDisplayMetrics.scaledDensity);
        }
        return scale(mDisplayMetrics.widthPixels,
                mDisplayMetrics.heightPixels, value);
    }





}
