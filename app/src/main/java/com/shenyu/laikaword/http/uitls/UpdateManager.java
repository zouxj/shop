package com.shenyu.laikaword.http.uitls;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.shenyu.laikaword.http.downloadmanager.DownLoadService;
import com.zxj.utilslibrary.utils.DeviceInfo;
import com.zxj.utilslibrary.utils.PackageManagerUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.ViewUtils;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class UpdateManager {

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
        String version_info = "更新内容\n" + "    1. 车位分享异常处理\n" + "    2. 发布车位折扣格式统一\n" + "    ";
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
    private void showNoticeDialog() {
        // 构造对话框
        ViewUtils.makeUpdate(mContext, "发现新版本", "xxxx", "暂不更新", "更新", false, new ViewUtils.ButtonCallback() {
            @Override
            public void onNegative(Dialog dialog) {

            }

            @Override
            public void onPositive(Dialog dialog) {

            }
        });
    }
}
