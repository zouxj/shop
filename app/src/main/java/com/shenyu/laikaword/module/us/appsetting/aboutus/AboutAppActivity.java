package com.shenyu.laikaword.module.us.appsetting.aboutus;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.leo618.mpermission.MPermission;
import com.leo618.mpermission.MPermissionSettingsDialog;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.CheckAppUpdateReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.model.net.downloadmanager.DownLoadService;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.zxj.utilslibrary.utils.DeviceInfo;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.List;

import butterknife.OnClick;

/**
 * 关于我们
 */
public class AboutAppActivity extends LKWordBaseActivity implements MPermission.PermissionCallbacks  {


    @Override
    public int bindLayout() {
        return R.layout.activity_about_app;
    }

    @Override
    public void initView() {
        setToolBarTitle("关于我们");
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MPermissionSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            ToastUtil.showToastShort(R.string.returned_from_app_settings_to_activity+"");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private  CheckAppUpdateReponse checkAppUpdateReponse;
    @OnClick({R.id.tv_check_update, R.id.tv_get_pignfen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_check_update:
               retrofitUtils.addSubscription(RetrofitUtils.apiStores.checkUpdate(1, DeviceInfo.getSystemVersion()), new ApiCallback<CheckAppUpdateReponse>() {
                    @Override
                    public void onSuccess(final CheckAppUpdateReponse model) {
                        if (model.isSuccess()){
                            checkAppUpdateReponse =model;
                               DialogHelper.makeUpdate(AboutAppActivity.this, "发现新版本", model.getPayload().getMessage(), "取消", "更新", model.getPayload().getType().equals("2"), new DialogHelper.ButtonCallback() {
                                   @Override
                                   public void onNegative(Dialog dialog) {
                                        //TODO 去更新
                                       if (MPermission.hasPermissions(AboutAppActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                           // Have permission, do the thing!
                                           //TODO去更新
                                           intalAPK();
                                       } else {
                                           // Ask for one permission
                                           MPermission.requestPermissions(AboutAppActivity.this, UIUtil.getString(R.string.write_wxternal), Constants.INTALL_APK, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                       }


                                   }

                                   @Override
                                   public void onPositive(Dialog dialog) {

                                   }
                               }).show();
                    }
                    else {
                            ToastUtil.showToastLong("已经是最新版本");
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
            case R.id.tv_get_pignfen:
                break;
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

    /**
     * 安装apk
     */
    public void intalAPK(){
        Intent intent = new Intent(AboutAppActivity.this, DownLoadService.class);
        intent.putExtra("DWONAPKURL",checkAppUpdateReponse.getPayload().getAndroidDownloadUrl());
        startService(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            //相机获取权限返回结果
            case Constants.INTALL_APK:
                intalAPK();
                break;
        }
    }
}
