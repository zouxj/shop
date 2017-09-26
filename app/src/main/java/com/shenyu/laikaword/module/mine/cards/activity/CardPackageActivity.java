package com.shenyu.laikaword.module.mine.cards.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CarPackageViewPagerAdapter;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.module.mine.MineModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * g购物卡管理
 */
public class CardPackageActivity extends LKWordBaseActivity {


    @BindView(R.id.tb_car_pack)
    TabLayout tbCarPack;
    @BindView(R.id.vp_car_pack)
    ViewPager vpCarPack;
    @Inject
    CarPackageViewPagerAdapter carPackageViewPagerAdapter;

    @Override
    public int bindLayout() {
        return R.layout.activity_card_package;
    }

    @Override
    public void initView() {
        setToolBarTitle("我的卡包");
        vpCarPack.setAdapter(carPackageViewPagerAdapter);
        tbCarPack.setupWithViewPager(vpCarPack);
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(getSupportFragmentManager())).inject(this);
    }

}
