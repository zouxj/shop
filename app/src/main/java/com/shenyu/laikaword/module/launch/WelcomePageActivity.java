package com.shenyu.laikaword.module.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.BannerBean;
import com.shenyu.laikaword.helper.BannerHelper;
import com.shenyu.laikaword.module.home.ui.activity.MainActivity;
import com.trello.rxlifecycle2.components.RxActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shenyu_zxjCode on 2017/10/27 0027.
 */

public  class WelcomePageActivity extends RxActivity {

    @BindView(R.id.tv_into_app)
    TextView tvIntoApp;

    BannerHelper mBannerHelper;

    public int bindLayout() {
        return R.layout.activity_welcome_page;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(bindLayout());
        ButterKnife.bind(this);
        doBusiness();
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



    public void doBusiness() {
        //设置banner样式
        mBannerHelper=BannerHelper.getInstance();
        mBannerHelper.init(findViewById(R.id.banner_rootlayout));
        mBannerHelper.setIsAuto(false);
        mBannerHelper.setPoitSize(12);
        mBannerHelper.setmPointersLayout(Gravity.CENTER,0,0,0,15);
        initData();
    }



    private void initData() {
        //test data
        mBannerHelper.setCirculate(false);
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
                IntentLauncher.with(this).launchFinishCpresent(MainActivity.class);
                break;
            case R.id.tv_into_app:
                IntentLauncher.with(this).launchFinishCpresent(MainActivity.class);
                break;
        }
    }

}
