package com.shenyu.laikaword.module.mine.systemsetting;

import android.graphics.Bitmap;

import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.bean.reponse.ImgSTSReponse;
import com.shenyu.laikaword.bean.reponse.LoginReponse;

/**
 * Created by shenyu_zxjCode on 2017/9/15 0015.
 * 个人信息
 */

public interface UserInfoView extends BaseLoadView {

    /**
     * 上传信息
     */
    void setImg(String url);
    void setUserInfo(LoginReponse loginReponse);
    void upadteHeadImgStart();
    void upadteHeadFinsh(boolean bool);
}
