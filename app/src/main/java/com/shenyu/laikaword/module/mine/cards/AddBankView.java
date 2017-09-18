package com.shenyu.laikaword.module.mine.cards;

import com.shenyu.laikaword.base.BaseLoadView;

/**
 * Created by shenyu_zxjCode on 2017/9/18 0018.
 */

public interface AddBankView extends BaseLoadView {

    //获取到银行卡名字
    void setBankName(String bankName);
    //获取到短信验证码
    void setMsgCode(String code);
}
