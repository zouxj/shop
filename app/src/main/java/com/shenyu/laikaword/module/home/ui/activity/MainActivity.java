package com.shenyu.laikaword.module.home.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import com.leo618.mpermission.MPermission;
import com.leo618.mpermission.MPermissionSettingsDialog;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.helper.UpdateManager;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.di.module.MainModule;
import com.shenyu.laikaword.module.home.ui.fragment.LeftFragment;
import com.shenyu.laikaword.module.home.ui.fragment.MainFragment;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.zxj.utilslibrary.utils.ActivityManageUtil;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;


import java.lang.reflect.Field;
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


    }

    @Override
    public void doBusiness(Context context) {

        getMPermission();
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
                                refreshUser();
                                break;
                        }
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
        LaiKaApplication.get(this).getAppComponent().plus(
                new MainModule(getSupportFragmentManager(),bindToLifecycle())).inject(this);
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
        if (MPermission.hasPermissions(this,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.REQUEST_INSTALL_PACKAGES
                ,Manifest.permission.SYSTEM_ALERT_WINDOW)) {
            // Have permission, do the thing!
            checkIsAndroidO();
//            new UpdateManager(mActivity).gerNewVersion(false);
//          ToastUtil.showToastShort("TODO: Camera things");
        } else {
            // Ask for one permission
            MPermission.requestPermissions(this,
                    UIUtil.getString(R.string.rationale_read_external),
                    Constants.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.REQUEST_INSTALL_PACKAGES
                    ,Manifest.permission.SYSTEM_ALERT_WINDOW

            );
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
//            ToastUtil.showToastShort(UIUtil.getString(R.string.write_external_storage));
        }
        switch (requestCode) {
            case 10012:
                checkIsAndroidO();
                break;

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //相机获取权限返回结果
            case Constants.READ_EXTERNAL_STORAGE:
//                ToastUtil.showToastShort(UIUtil.getString(R.string.write_external_storage));
//                new UpdateManager(mActivity).gerNewVersion(false);
                checkIsAndroidO();
                break;
            case 10010:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new UpdateManager(mActivity).gerNewVersion(false);
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    startActivityForResult(intent, 10012);
                }
        }
    }
    // 通过反射获取状态栏高度
      public  int getStatusBarHeight(Context context) {
        try {            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();        }
        return 0;    }
    /**
     * 判断是否是8.0,8.0需要处理未知应用来源权限问题,否则直接安装
     */
    private void checkIsAndroidO() {

        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = getPackageManager().canRequestPackageInstalls();
            if (b) {
                new UpdateManager(mActivity).gerNewVersion(false);
            } else {
                DialogHelper.commonDialog(this, "安装权限", "需要打开此应用安装权限，才能自动更新升级应用", "确定", "取消", false, new DialogHelper.ButtonCallback() {
                                @Override
                                public void onNegative(Dialog dialog) {
                                dialog.dismiss();
                                }
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onPositive(Dialog dialog) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, 10010);

                                }
                            }).show();


            }
        } else {
            new UpdateManager(mActivity).gerNewVersion(false);
        }

    }
}
