package com.shenyu.laikaword.main.activity;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.BannerBean;
import com.shenyu.laikaword.helper.BannerHelper;
import com.shenyu.laikaword.main.MainModule;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomePageActivity extends LKWordBaseActivity {

    @BindView(R.id.tv_into_app)
    TextView tvIntoApp;
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
        mBannerHelper.setIsAuto(false);

        initData();
    }


    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MainModule()).inject(this);
    }
    private void initData() {
        //test data
        final List<BannerBean> dataList = new ArrayList<>();
        dataList.add(new BannerBean(R.mipmap.pager_weclome_one, "", null, null));
        dataList.add(new BannerBean(R.mipmap.pager_weclome_two, "", null, null));
        dataList.add(new BannerBean(R.mipmap.pager_weclome_three, "", null, null));
        mBannerHelper.startBanner(dataList, new BannerHelper.OnItemClickListener() {
            @Override
            public void onItemClick(BannerBean bean) {

            }
        });
        mBannerHelper.setOnPageChangeListener(new BannerHelper.OnPageChangeListener() {
            @Override
            public void onItemClick(int positon) {
                if (positon==dataList.size()-1)
                    tvIntoApp.setVisibility(View.VISIBLE);
                else
                    tvIntoApp.setVisibility(View.GONE);
            }
        });

    }
@OnClick({R.id.wl_tv_tiao,R.id.tv_into_app})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.wl_tv_tiao:
                IntentLauncher.with(this).launch(MainActivity.class);
                finish();
                break;
            case R.id.tv_into_app:
                IntentLauncher.with(this).launch(MainActivity.class);
                finish();
                break;
        }
}

}
