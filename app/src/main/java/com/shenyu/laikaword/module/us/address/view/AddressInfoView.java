package com.shenyu.laikaword.module.us.address.view;

import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.model.bean.reponse.AddressReponse;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;

/**
 * Created by zxjCo on 2018/1/12.
 * 用户地址信息View
 */

public interface AddressInfoView extends BaseLoadView {
    void subscribeEvent(Event myEvent);
    void setAddress(BaseReponse baseReponse);
    void getAddressInfo(AddressReponse baseReponse);
}
