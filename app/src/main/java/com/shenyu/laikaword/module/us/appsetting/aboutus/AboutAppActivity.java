package com.shenyu.laikaword.module.us.appsetting.aboutus;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.leo618.mpermission.MPermission;
import com.leo618.mpermission.MPermissionSettingsDialog;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.UpdateManager;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于我们
 */
public class AboutAppActivity extends LKWordBaseActivity implements MPermission.PermissionCallbacks {


    @BindView(R.id.tv_new_version)
    TextView tvNewVersion;
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
        if (StringUtil.validText(Constants.VERSION_NEW)){
            tvNewVersion.setText("发现新版本"+Constants.VERSION_NEW);
        }
    }

    @Override
    public void setupActivityComponent() {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MPermissionSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            ToastUtil.showToastShort(R.string.write_external_storage + "");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick({R.id.tv_check_update, R.id.tv_get_pignfen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_check_update:
                if (MPermission.hasPermissions(AboutAppActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Have permission, do the thing!
                  new UpdateManager(mActivity).gerNewVersion(true);


                    //TODO去更新
                } else {
                    // Ask for one permission
                    MPermission.requestPermissions(AboutAppActivity.this, UIUtil.getString(R.string.write_wxternal), Constants.INTALL_APK, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

                break;
            case R.id.tv_get_pignfen:
                //启动应用市场去评分
                Uri uri = Uri.parse("market://details?id="+getPackageName());
                Intent intentpf = new Intent(Intent.ACTION_VIEW,uri);
                intentpf.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentpf);
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



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            //安装权限
            case Constants.INTALL_APK:
                new UpdateManager(mActivity).gerNewVersion(true);
            break;
        }
    }


}
