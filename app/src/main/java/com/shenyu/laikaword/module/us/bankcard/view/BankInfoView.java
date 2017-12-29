package com.shenyu.laikaword.module.us.bankcard.view;

import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;

/**
 * Created by shenyu_zxjCode on 2017/12/29 0029.
 */

public interface BankInfoView extends BaseLoadView {

    void subscribeEvent(Event eventType);
    void showData(BaseReponse baseReponse);
    void deteBank(boolean bool);
}
