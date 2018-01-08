package com.shenyu.laikaword.module.us.zhuanmai.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.mine.MineModule;
import com.shenyu.laikaword.helper.TabLayoutHelper;
import com.shenyu.laikaword.model.adapter.ZhuanMaiViewAdapter;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.zxj.utilslibrary.utils.StringUtil;

import javax.inject.Inject;

import butterknife.BindView;

public class ResellActivity extends LKWordBaseActivity {
    @BindView(R.id.tb_pruchase_car_pack)
    TabLayout tbPruchaseCarPack;
    @BindView(R.id.vp_pruchase_car_pack)
    ViewPager vpPruchaseCarPack;
    @Inject
    ZhuanMaiViewAdapter zhuanmaiAdapter;

    @Override
    public int bindLayout() {
        return R.layout.activity_zhuanmai;
    }

    @Override
    public void initView() {
        tbPruchaseCarPack.post(new Runnable() {
            @Override
            public void run() {
                TabLayoutHelper.setIndicator(tbPruchaseCarPack, 50, 50);
            }
        });
        setToolBarTitle("我的转卖");
        vpPruchaseCarPack.setAdapter(zhuanmaiAdapter);
        tbPruchaseCarPack.setupWithViewPager(vpPruchaseCarPack);
    }

    @Override
    public void doBusiness(Context context) {
        String type = getIntent().getStringExtra("type");
        if (StringUtil.validText(type)) {
            if (type.equals("JD")) {
                vpPruchaseCarPack.setCurrentItem(1);
            } else if (type.equals("HUAFEI")) {
                vpPruchaseCarPack.setCurrentItem(0);
            }
        }


    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(getSupportFragmentManager())).inject(this);
    }

}