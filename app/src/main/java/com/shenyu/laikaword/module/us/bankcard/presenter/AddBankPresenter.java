package com.shenyu.laikaword.module.us.bankcard.presenter;
import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.module.us.bankcard.view.AddBankView;
import com.zxj.utilslibrary.utils.LogUtil;

import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by shenyu_zxjCode on 2017/9/18 0018.
 */

public class AddBankPresenter extends BasePresenter<AddBankView> {

    public AddBankPresenter(AddBankView addBankView){
        this.mvpView=addBankView;
        attachView(mvpView);
    }

}
