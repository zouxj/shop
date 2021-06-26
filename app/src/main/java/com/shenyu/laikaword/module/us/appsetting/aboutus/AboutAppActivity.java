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
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.model.web.GuessActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.PackageManagerUtil;
import com.zxj.utilslibrary.utils.SPUtil;
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
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.textView)
    TextView tvQQ;
    @BindView(R.id.tv_version_ch)
    TextView tvVersionCh;
    @BindView(R.id.tv_version_en)
    TextView tvVersionEn;

    @Override
    public int bindLayout() {
        return R.layout.activity_about_app;
    }

    @Override
    public void initView() {
        setToolBarTitle("关于我们");
        tvVersion.setText("淘卡商城 " + PackageManagerUtil.getVersionName(UIUtil.getContext()));

        final ShopMainReponse shopMainReponse = (ShopMainReponse) SPUtil.readObject(Constants.MAIN_SHOP_KEY);
        if (shopMainReponse != null) {
            String qq = shopMainReponse.getPayload().getContacts().getUrl();
            if (StringUtil.validText(qq))
                tvQQ.setText("客服QQ:" + qq);
            tvVersionCh.setText( shopMainReponse.getPayload().getCopyrightCn());
            tvVersionEn.setText(shopMainReponse.getPayload().getCopyrightEn());

        }
    }

    @Override
    public void doBusiness(Context context) {
        if (StringUtil.validText(Constants.VERSION_NEW)) {
            tvNewVersion.setText("发现新版本" + Constants.VERSION_NEW);
        }
    }

    @Override
    public void setupActivityComponent() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MPermissionSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
//            ToastUtil.showToastShort(UIUtil.getString(R.string.write_external_storage));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick({R.id.tv_check_update, R.id.tv_get_pignfen, R.id.textView, R.id.tv_server, R.id.tv_version})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.tv_version:
//                //TODO 版本切换
//                switch (Constants.host){
//                    case 0:
//                        ToastUtil.showToastShort("已经切为预发布");
//                       Constants.host=1;
//                       Constants.API_URL="https://pre.api.buycardlife.com/MApi/";
//                        break;
//                    case 1:
//                        Constants.host=2;
//                        ToastUtil.showToastShort("已经切为测试线");
//                        Constants.API_URL="http://t.shop.comingcard.com/MApi/";
//                        break;
//                    case 2:
//                        Constants.host=0;
//                        ToastUtil.showToastShort("已经切为正式线");
//                        Constants.API_URL="https://api.buycardlife.com/MApi/";
//                        break;
//                }
//
//                break;
            case R.id.textView:
                //TODO 客服电话
                break;
            case R.id.tv_check_update:
                if (MPermission.hasPermissions(AboutAppActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Have permission, do the thing!
                    new UpdateManager(mActivity).gerNewVersion(true);

                    //TODO去更新
                } else {
                    // Ask for one permission
                    MPermission.requestPermissions(AboutAppActivity.this, UIUtil.getString(R.string.write_wxternal),
                            Constants.INTALL_APK, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

                break;
            case R.id.tv_get_pignfen:
                //启动应用市场去评分
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intentpf = new Intent(Intent.ACTION_VIEW, uri);
                intentpf.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentpf);
                break;
            case R.id.tv_server:
                //TODO 服务条款
                ShopMainReponse shopMainReponse = (ShopMainReponse) SPUtil.readObject(Constants.MAIN_SHOP_KEY);
                if (null != shopMainReponse) {
                    IntentLauncher.with(this).put("weburl", shopMainReponse.getPayload().getAgreement()).launch(GuessActivity.class);
                }
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
//                LogUtil.i("获取到了到了权限");
                new UpdateManager(mActivity).gerNewVersion(true);
                break;
        }
    }



}
