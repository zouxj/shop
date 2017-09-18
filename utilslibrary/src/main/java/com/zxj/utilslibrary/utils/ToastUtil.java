package com.zxj.utilslibrary.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zxj.utilslibrary.AndroidUtilsCore;
import com.zxj.utilslibrary.R;

import static com.zxj.utilslibrary.utils.UIUtil.post;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class ToastUtil {
    private static Toast mToast;
    private ToastUtil(){}
    private static ToastUtil toastUti;
    public static ToastUtil Toast(){
        if (null==toastUti){
           synchronized (ToastUtil.class){
                if (null==toastUti){
                    toastUti = new ToastUtil();
                }
            }
        }
        return  toastUti;
    }
    public static void showToastShort(final String msg) {
        showToastShort(msg, Gravity.CENTER);
    }

    public static void showToastShort(final String msg, final int gravity) {
        showToastShort(msg, Toast.LENGTH_SHORT, "#e0000000", 14, UIUtil.dp2px(5f), gravity);
    }

    public static void showToastLong(final String msg) {
        showToastLong(msg, Gravity.CENTER);
    }

    public static void showToastLong(final String msg, final int gravity) {
        showToastShort(msg, Toast.LENGTH_LONG, "#666666", 14, UIUtil.dp2px(5f), gravity);
    }

    public static void showToastShort(final String msg, final int duration, final String bgColor,
                                      final int textSp, final float cornerRadius, final int gravity) {
        if (mToast != null && mToast.getView().getParent() != null) {
            return;
        }
        post(new Runnable() {
            @Override
            public void run() {
                buildToast(msg, duration, bgColor, textSp, cornerRadius, gravity,0).show();
            }
        });
    }


//    public static void showCustomLong(final String msg, final int spTex, final int gravity, final int y) {
////        if (mToast != null && mToast.getView().getParent() != null) {
////            return;
////        }
//        buildToast(msg, spTex, gravity,  y).show();
////        post(new Runnable() {
////            @Override
////            public void run() {
////                buildToast(msg, spTex, gravity,  y).show();
////            }
////        });
//    }
    /**
     * 构造Toast
     *
     * @param msg          消息
     * @param duration     显示时间
     * @param bgColor      背景颜色
     * @param textSp       文字大小
     * @param cornerRadius 四边圆角弧度
     * @return Toast
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("ShowToast")
    private static Toast buildToast(String msg, int duration, String bgColor, int textSp, float cornerRadius, int gravity,int y) {
        mToast = new Toast(AndroidUtilsCore.getContext());
        mToast.setDuration(duration);
        mToast.setGravity(gravity, 0, Gravity.BOTTOM == gravity ? 150 : 0);
        // 设置Toast文字
        TextView tv = new TextView(AndroidUtilsCore.getContext());
        int dpPaddingLR = (int) UIUtil.dp2px(20);
        int dpPaddingTB = (int) UIUtil.dp2px(7);
        tv.setPadding(dpPaddingLR, dpPaddingTB, dpPaddingLR, dpPaddingTB);
        tv.setGravity(Gravity.CENTER);
        tv.setText(msg);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSp);
        // Toast文字TextView容器
        LinearLayout mLayout = new LinearLayout(AndroidUtilsCore.getContext());
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(Color.parseColor(bgColor));
        shape.setCornerRadius(cornerRadius);
        shape.setStroke(1, Color.parseColor(bgColor));
        shape.setAlpha(204);
        mLayout.setBackgroundDrawable(UIUtil.getDrawable(R.drawable.toast_bg));
        mLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mLayout.setLayoutParams(params);
        mLayout.setGravity(Gravity.CENTER);
        mLayout.addView(tv);
        // 将自定义View覆盖Toast的View
        mToast.setView(mLayout);
        return mToast;
    }
//
//    private static Toast buildToast(String msg,   int textSp, int gravity,int y) {
//       Toast mToast = new Toast(AndroidUtilsCore.getContext());
//        mToast.setDuration(Toast.LENGTH_SHORT);
//        mToast.setGravity(gravity,  -500,-y);
////        mToast.setGravity(gravity, 0, Gravity.BOTTOM == gravity ? 150 : 0);
//        // 设置Toast文字
//        TextView tv = new TextView(AndroidUtilsCore.getContext());
//        int dpPaddingLR = (int) UIUtil.dp2px(20);
//        int dpPaddingTB = (int) UIUtil.dp2px(7);
//        tv.setPadding(dpPaddingLR, dpPaddingTB, dpPaddingLR, dpPaddingTB);
//        tv.setGravity(Gravity.CENTER);
//        tv.setText(msg);
//        tv.setTextColor(Color.WHITE);
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSp);
//        // Toast文字TextView容器
//        LinearLayout mLayout = new LinearLayout(AndroidUtilsCore.getContext());
//        mLayout.setBackgroundDrawable(UIUtil.getDrawable(R.drawable.toast_bg));
//        mLayout.setOrientation(LinearLayout.VERTICAL);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.CENTER;
//        mLayout.setLayoutParams(params);
//        mLayout.setGravity(Gravity.CENTER);
//        mLayout.addView(tv);
//        // 将自定义View覆盖Toast的View
//        mToast.setView(mLayout);
//        return mToast;
//    }
}
