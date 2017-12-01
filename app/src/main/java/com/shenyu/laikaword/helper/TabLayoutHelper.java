package com.shenyu.laikaword.helper;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * Created by shenyu_zxjCode on 2017/10/16 0016.
 */

public final class TabLayoutHelper {
    /**
     * 指示器
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    @SuppressLint("NewApi")
    public static  void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        if (tabs==null)
            return;
        Class<?> tabLayout = tabs.getClass();
//        tabs.setElevation(0);
        Field tabStrip = null;
        try {
            if (tabLayout==null)
                return;
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }
}
