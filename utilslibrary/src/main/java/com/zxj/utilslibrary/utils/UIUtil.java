package com.zxj.utilslibrary.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.zxj.utilslibrary.AndroidUtilsCore;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class UIUtil {
    private static final String TAG = "UIUtil";

    public static Context getContext() {
        return AndroidUtilsCore.getContext();
    }

    /** 获取主线程消息轮询器 */
    public static android.os.Looper getMainLooper() {
        return AndroidUtilsCore.getContext().getMainLooper();
    }

    /** 获取主线程的handler */
    public static Handler getHandler() {
        return new android.os.Handler(getMainLooper());
    }

    /** 在主线程中延时一定时间执行runnable */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /** 在主线程执行runnable */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /** 从主线程looper里面移除runnable */
    public static void removeCallbacksFromMainLooper(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    /** ----------------------根据资源id获取资源------start------------------------- */

    /**
     * 填充layout布局文件
     */
    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
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
        return ContextCompat.getDrawable(getContext(), resId);
    }

    /**
     * 获取Bitmap
     */
    public static Bitmap getBitmap(int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return ContextCompat.getColorStateList(getContext(), resId);
    }

    /** ----------------------根据资源id获取资源------end------------------------- */

    /** ----------------------px与dip相互转换------start------------------------- */
    /**
     * dip转换为px
     */
    public static float dp2px(float dip) {
        if (dip <= 0) return 0;
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return dip * scale + 0.5f;
    }
    /**
     * px转换为dip
     */
    public static float px2dip(float px) {
        if (px <= 0) return 0;
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return px / scale + 0.5f;
    }
    public static float sp2px(Context context, float spValue) {
        if (spValue <= 0) return 0;
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }
}
