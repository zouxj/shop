package com.shenyu.laikaword.module.us.wallet.remaining;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.PurchaseViewPagerAdapter;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.TabLayoutHelper;
import com.shenyu.laikaword.di.module.MineModule;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 提貨记录
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
                TabLayoutHelper.setIndicator(tbPruchaseCarPack, 50, 50);
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


}
