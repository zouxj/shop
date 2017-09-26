package com.shenyu.laikaword.module.mine.remaining;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.PurchaseViewPagerAdapter;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.module.mine.MineModule;

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
}