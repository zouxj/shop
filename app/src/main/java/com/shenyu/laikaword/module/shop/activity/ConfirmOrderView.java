package com.shenyu.laikaword.module.shop.activity;

import com.shenyu.laikaword.base.BaseLoadView;

/**
 * Created by shenyu_zxjCode on 2017/9/25 0025.
 */

public interface ConfirmOrderView extends BaseLoadView {
    //支付成功
    void paySuccess();
    //支付失败
    void payFaire();
}
