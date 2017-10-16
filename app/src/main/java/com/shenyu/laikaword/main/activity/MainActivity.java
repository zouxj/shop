package com.shenyu.laikaword.main.activity;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.main.MainModule;
import com.shenyu.laikaword.main.fragment.LeftFragment;
import com.shenyu.laikaword.main.fragment.MainFragment;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.shenyu.laikaword.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.rxbus.RxSubscriptions;
import com.shenyu.laikaword.rxbus.event.Event;
import com.shenyu.laikaword.rxbus.event.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.zxj.utilslibrary.utils.ActivityManageUtil;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.ToastUtil;


import javax.inject.Inject;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 首页Acitivity
 */
public class MainActivity extends LKWordBaseActivity {

    @BindView(R.id.left_drawer)
    FrameLayout frameLeft;
    @Inject
    FragmentTransaction fragmentTransaction;
    @Inject
    LeftFragment leftFragment;
    @Inject
    MainFragment mainFragment;
    @BindView(R.id.drawer_main)
    DrawerLayout drawerLayout;
    private long exitTime = 0;
    @Override
    public int bindLayout() {
        return R.layout.activity_main2;
    }

    @Override
    public void initView() {

////        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,mToolbar,0,1);
//        actionBarDrawerToggle.syncState(); drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }

    @Override
    public void doBusiness(Context context) {
        subscribeEvent();
        initFragment();

    }
    private void subscribeEvent() {
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(Event.class)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
                    @Override
                    protected void onEvent(Event myEvent) {
                        switch (myEvent.event) {
                            case EventType.ACTION_OPONE_LEFT:
                            drawerLayout.openDrawer(frameLeft);
                            break;
                            case EventType.ACTION_UPDATA_USER_REQUEST:
                                RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.getUserInfo(), new ApiCallback<BaseReponse>() {
                                    @Override
                                    public void onSuccess(BaseReponse loginReponse) {
                                        if (loginReponse.isSuccess()){
                                            SPUtil.saveObject(Constants.LOGININFO_KEY,loginReponse);
                                            RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER,null));
                                        }
                                    }

                                    @Override
                                    public void onFailure(String msg) {

                                    }

                                    @Override
                                    public void onFinish() {

                                    }
                                });
                                break;
                        }
//            }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.e(TAG, "onError");
                        /**
                         * 这里注意: 一旦订阅过程中发生异常,走到onError,则代表此次订阅事件完成,后续将收不到onNext()事件,
                         * 即 接受不到后续的任何事件,实际环境中,我们需要在onError里 重新订阅事件!
                         */
                        subscribeEvent();
                    }
                });
        RxSubscriptions.add(mRxSub);
    }
    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MainModule(getSupportFragmentManager())).inject(this);
    }

    /**
     * 初始化Fragment
     */
    private void initFragment(){
        //添加MianFragment
        fragmentTransaction.replace(R.id.content_frame, mainFragment);
        //添加LeftFragment
        fragmentTransaction.replace(R.id.left_drawer, leftFragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis()-exitTime>2000){
            ToastUtil.showToastShort("在按一次退出应用");
            RxBus.getDefault().removeAllStickyEvents();
            exitTime=System.currentTimeMillis();
        }else {
            ActivityManageUtil.getAppManager().finishAllActivity();
            System.exit(0);
        }
    }


}
