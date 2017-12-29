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

    @Override
    public void subscribeEvent() {
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(Event.class)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
                    @Override
                    protected void onEvent(Event myEvent) {
                        mvpView.subscribeEvent(myEvent);

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.e("AddBankPresenter", "onError");
                        /**
                         * 这里注意: 一旦订阅过程中发生异常,走到onError,则代表此次订阅事件完成,后续将收不到onNext()事件,
                         * 即 接受不到后续的任何事件,实际环境中,我们需要在onError里 重新订阅事件!
                         */
                        subscribeEvent();
                    }
                });
        RxSubscriptions.add(mRxSub);
    }
}
