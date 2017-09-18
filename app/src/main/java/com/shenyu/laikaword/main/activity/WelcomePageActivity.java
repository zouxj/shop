package com.shenyu.laikaword.main.activity;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.BannerBean;
import com.shenyu.laikaword.helper.BannerHelper;
import com.shenyu.laikaword.main.MainModule;
import com.zxj.utilslibrary.utils.IntentLauncher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomePageActivity extends LKWordBaseActivity {

    @Inject
     BannerHelper mBannerHelper;

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
        //设置banner样式
        mBannerHelper.init(findViewById(R.id.banner_rootlayout));
        mBannerHelper.setIsAuto(true);
        initData();
    }


    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MainModule()).inject(this);
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
@OnClick({R.id.wl_tv_tiao})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.wl_tv_tiao:
                IntentLauncher.with(this).launch(MainActivity.class);
                finish();
                break;
        }
}

}
