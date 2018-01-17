package com.shenyu.laikaword.module.us.address.presenter;

import android.view.View;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.model.bean.reponse.AddressReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.module.us.address.ui.activity.EditAddressActivity;
import com.shenyu.laikaword.module.us.address.ui.activity.SelectAddressActivity;
import com.shenyu.laikaword.module.us.address.view.EditAddressView;
import com.shenyu.laikaword.module.us.address.view.SelectAddressView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.zxj.utilslibrary.utils.IntentLauncher;

/**
 * Created by zxjCo on 2018/1/16.
 */

public class SelectAddressPresneter extends BasePresenter<SelectAddressView> {

    public SelectAddressPresneter(SelectAddressView selectAddressView){
        attachView(selectAddressView);
        this.mvpView=selectAddressView;
    }

    @Override
    public void distribute(Event myEvent) {
        mvpView.subscribeEvent(myEvent);
    }

    public void requestAddress(LifecycleTransformer lifecycleTransformer,AddressReponse.PayloadBean payloadBean ){
            addSubscription(lifecycleTransformer,apiStores.getAddress(), new ApiCallback<AddressReponse>() {
                @Override
                public void onSuccess(AddressReponse model) {
                    mvpView.loadSucceed(model);
                    }

                @Override
                public void onFailure(String msg) {

                }

                @Override
                public void onFinish() {

                }
            });
    }
}
