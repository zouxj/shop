package com.shenyu.laikaword.helper;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.leo618.mpermission.MPermission;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.bean.reponse.CheckAppUpdateReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.downloadmanager.DownLoadService;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.module.us.appsetting.aboutus.AboutAppActivity;
import com.zxj.utilslibrary.utils.DeviceInfo;
import com.zxj.utilslibrary.utils.PackageManagerUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.shenyu.laikaword.helper.DialogHelper;
import com.zxj.utilslibrary.utils.UIUtil;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public final class UpdateManager {

    private Context mContext;
    public UpdateManager(Context context) {
        this.mContext = context;
    }
    public void    gerNewVersion(final boolean toast){
        RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.checkUpdate(1, DeviceInfo.getSystemVersion()), new ApiCallback<CheckAppUpdateReponse>() {
            @Override
            public void onSuccess(final CheckAppUpdateReponse model) {
                if (model.isSuccess()&&model.getPayload()!=null) {
                    if (StringUtil.validText(model.getPayload().getNewVersion())) {
                        Constants.VERSION_NEW = model.getPayload().getNewVersion();
                        DialogHelper.makeUpdate(mContext, "发现新版本", model.getPayload().getMessage(), "取消", "更新", model.getPayload().getType().equals("2"), new DialogHelper.ButtonCallback() {
                            @Override
                            public void onNegative(Dialog dialog) {
                                //TODO 去更新
                                intalAPK(model.getPayload().getAndroidDownloadUrl());
                            }

                            @Override
                            public void onPositive(Dialog dialog) {

                            }
                        }).show();
                    }else {
                        if (toast){
                            ToastUtil.showToastShort("已经是最新版本");
                        }
                    }
                }

            }
            @Override
            public void onFailure(String msg) {
                ToastUtil.showToastShort(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 安装apk
     */
    private void intalAPK(String downURL) {
        Intent intent = new Intent(mContext, DownLoadService.class);
        intent.putExtra("DWONAPKURL", downURL);
        mContext.startService(intent);
    }
}
