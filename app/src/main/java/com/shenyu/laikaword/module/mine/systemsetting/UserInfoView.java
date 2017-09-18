package com.shenyu.laikaword.module.mine.systemsetting;

import android.graphics.Bitmap;

import com.shenyu.laikaword.base.BaseLoadView;

/**
 * Created by shenyu_zxjCode on 2017/9/15 0015.
 * 个人信息
 */

public interface UserInfoView extends BaseLoadView {

    /**
     * 上传信息
     */
    void updateImg(String uri);
    void setImg(String url);

}
