package com.shenyu.laikaword.module.us.bankcard.presenter;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.model.bean.reponse.BankInfoReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.module.us.bankcard.view.SelectBankView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zxj.utilslibrary.utils.LogUtil;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by shenyu_zxjCode on 2017/12/29 0029.
 */

/**
 * 选择银行卡Present
 */
public class SelectBankPresent extends BasePresenter<SelectBankView> {

    public SelectBankPresent(SelectBankView selectBankView){
            this.mvpView=selectBankView;
            attachView(selectBankView);
    }


    @Override
    public void distribute(Event myEvent) {
        mvpView.eventBus(myEvent);
    }

    /**
     * 请求数据
     * @param lifecycleTransformer
     */
    public void requestData(LifecycleTransformer lifecycleTransformer){
      addSubscription(lifecycleTransformer,apiStores.getBankCard(), new ApiCallback<BankInfoReponse>() {
            @Override
            public void onSuccess(BankInfoReponse model) {
                if (model.isSuccess()) {
                    mvpView.showData(model);
                }

            }

            @Override
            public void onFailure(String msg) {
                mvpView.loadFailure();
            }

            @Override
            public void onFinish() {
            }
        });
    }
}
