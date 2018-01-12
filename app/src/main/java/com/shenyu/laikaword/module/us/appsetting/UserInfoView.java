package com.shenyu.laikaword.module.us.appsetting;

import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;

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
    void subscribeEvent(Event event);
}
