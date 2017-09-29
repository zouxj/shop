package com.shenyu.laikaword.module.mine.remaining;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.PurchaseViewPagerAdapter;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.module.mine.MineModule;

import java.lang.reflect.Field;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购买记录
 */
public class PurchaseCardActivity extends LKWordBaseActivity {


    @BindView(R.id.tb_pruchase_car_pack)
    TabLayout tbPruchaseCarPack;
    @BindView(R.id.vp_pruchase_car_pack)
    ViewPager vpPruchaseCarPack;
    @Inject
    PurchaseViewPagerAdapter purchaseViewPagerAdapter;

    @Override
    public int bindLayout() {
        return R.layout.activity_purchase_money;
    }

    @Override
    public void initView() {
        tbPruchaseCarPack.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tbPruchaseCarPack,50,50);
            }
        });
        setToolBarTitle("我的提货");
        vpPruchaseCarPack.setAdapter(purchaseViewPagerAdapter);
        tbPruchaseCarPack.setupWithViewPager(vpPruchaseCarPack);
    }

    @Override
    public void doBusiness(Context context) {


    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(getSupportFragmentManager())).inject(this);
    }

    /**
     * 指示器
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
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
