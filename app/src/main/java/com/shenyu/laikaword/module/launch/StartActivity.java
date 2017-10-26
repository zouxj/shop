package com.shenyu.laikaword.module.launch;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.MainModule;
import com.shenyu.laikaword.helper.BannerBean;
import com.shenyu.laikaword.helper.BannerHelper;
import com.shenyu.laikaword.module.home.ui.activity.MainActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

/**
 * 启动页
 */
public class StartActivity extends LKWordBaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_start;
    }
    String usename;
    String password;
    @Override
    public void doBusiness(Context context) {
         usename = SPUtil.getString("usename","");
         password =SPUtil.getString("password","");
        if (!StringUtil.validText(SPUtil.getString("start_app",""))){
            //TODO 第一次登录
            IntentLauncher.with(this).launch(WelcomePageActivity.class);
            SPUtil.putString("start_app","one");
            finish();
        }else {
            if(StringUtil.validText(usename)&&StringUtil.validText(password)){
                    //TODO 登录进入APP
//                login();
            }else{
                //TODO 没有登录过直接延时进入主页面
                Observable.timer(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        IntentLauncher.with(StartActivity.this).launch(MainActivity.class);
                        finish();
                    }
                });

            }

        }
    }

    @Override
    public void setupActivityComponent() {

    }
//    public void login(){
//        NetWorks.loginUser(usename, password, new SimpleCallback<UserReponse>() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onNext(UserReponse userReponse) {
//                IntentLauncher.with(mActivity).launch(MainActivity.class);
//                finish();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//
//    }

    public static class WelcomePageActivity extends LKWordBaseActivity {

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
}
