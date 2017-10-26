package com.shenyu.laikaword.module.home.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import com.leo618.mpermission.MPermission;
import com.leo618.mpermission.MPermissionSettingsDialog;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.base.BaseReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.di.module.MainModule;
import com.shenyu.laikaword.module.home.ui.fragment.LeftFragment;
import com.shenyu.laikaword.module.home.ui.fragment.MainFragment;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.zxj.utilslibrary.utils.ActivityManageUtil;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 首页Acitivity
 */
public class MainActivity extends LKWordBaseActivity implements  MPermission.PermissionCallbacks {

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
        getMPermission();
        subscribeEvent();
        initFragment();
        ToastUtil.showToastShort("我是热修复之后的插件");

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
                                        }
                                        RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER,null));
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

    /**
     * 获取权限
     */
    public void getMPermission(){
        if (MPermission.hasPermissions(this, Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Have permission, do the thing!

//          ToastUtil.showToastShort("TODO: Camera things");
        } else {
            // Ask for one permission
            MPermission.requestPermissions(this, UIUtil.getString(R.string.rationale_read_external), Constants.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
}

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (MPermission.somePermissionPermanentlyDenied(this, perms)) {
            new MPermissionSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MPermissionSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            ToastUtil.showToastShort(R.string.returned_from_app_settings_to_activity+"");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //相机获取权限返回结果
            case Constants.READ_EXTERNAL_STORAGE:
                getMPermission();
                break;
        }
    }
}