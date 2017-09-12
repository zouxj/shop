package com.shenyu.laikaword.main.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.BannerBean;
import com.shenyu.laikaword.helper.BannerHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomePageActivity extends LKWordBaseActivity {

    private BannerHelper mBannerHelper;

    @Override
    public int bindLayout() {
        return R.layout.activity_welcome_page;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBannerHelper.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBannerHelper.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBannerHelper.onResume();

    }


    @Override
    public void doBusiness(Context context) {
//        IntentLauncher.with(this).launch(LoginActivity.class);
//        IntentLauncher.with(this).launch(TestMainActivity.class);
        //设置banner样式
        mBannerHelper = BannerHelper.getInstance().init(findViewById(R.id.banner_rootlayout));
        mBannerHelper.setIsAuto(true);
        //delay post for simulate net access
        initData();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                initData();
//            }
//        }, 3000);
    }


    @Override
    public void setupActivityComponent() {

    }
    private void initData() {
        //test data
        List<BannerBean> dataList = new ArrayList<>();
        dataList.add(new BannerBean(R.mipmap.ic_launcher, "http://img-ws.doupai.cc/Ie548c2459f324e6f79180d6eb61d78be.jpg!avatar", null, null));
        dataList.add(new BannerBean(R.mipmap.ic_launcher, "http://img-ws.doupai.cc/I2a776ee24c2b3adfa62288bb252bb68a.jpg!avatar", null, null));
        dataList.add(new BannerBean(R.mipmap.ic_launcher, "http://img-ws.doupai.cc/Ia0003e4a1ac7d9c8e1d48bca6235f599.jpg?imageView2/1/w/200", null, null));
        dataList.add(new BannerBean(R.mipmap.ic_launcher, "http://img-ws.doupai.cc/Ia0003e4a1ac7d9c8e1d48bca6235f599.jpg!origin", null, null));

        mBannerHelper.startBanner(dataList, new BannerHelper.OnItemClickListener() {
            @Override
            public void onItemClick(BannerBean bean) {
//                Toast.makeText(TestActivity.this, "click:" + bean.getDesc(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
