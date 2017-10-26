package com.shenyu.laikaword.helper;

import android.app.Dialog;
import android.content.Context;

import com.zxj.utilslibrary.utils.PackageManagerUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.shenyu.laikaword.helper.DialogHelper;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public final class UpdateManager {

    private Context mContext;

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate(final boolean isToast) {
        /**
         * 在这里请求后台接口，获取更新的内容和最新的版本号
         */
        // 版本的更新信息
        int mVersion_code = PackageManagerUtil.getVersionCode();// 当前的版本号
        int nVersion_code = 2;
        if (mVersion_code < nVersion_code) {
            // 显示提示对话
            showNoticeDialog();
        } else {
            if (isToast) {
                ToastUtil.showToastLong("已经是最新版本");
            }
        }
    }

    /**
     * 显示更新对话框
     *
     */
    public void showNoticeDialog() {
        // 构造对话框
        DialogHelper.makeUpdate(mContext, "发现新版本", "xxxx", "暂不更新", "更新", false, new DialogHelper.ButtonCallback() {
            @Override
            public void onNegative(Dialog dialog) {

            }

            @Override
            public void onPositive(Dialog dialog) {

            }
        });
    }
}
