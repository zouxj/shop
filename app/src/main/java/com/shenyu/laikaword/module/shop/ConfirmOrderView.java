package com.shenyu.laikaword.module.shop;

import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.bean.reponse.GoodBean;
import com.shenyu.laikaword.bean.reponse.LoginReponse;

/**
 * Created by shenyu_zxjCode on 2017/9/25 0025.
 */

public interface ConfirmOrderView extends BaseLoadView {
    //支付成功
    void paySuccess();
    //支付失败
    void payFaire();
    void showData(LoginReponse loginReponse, GoodBean goodBean);
}
