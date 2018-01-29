package com.shenyu.laikaword.module.us.resell.presenter;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.SellInfoReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.module.us.resell.view.ResellInputCodeView;
import com.trello.rxlifecycle2.LifecycleTransformer;

public class ResellInputCodePresenter extends BasePresenter<ResellInputCodeView> {

    public ResellInputCodePresenter(ResellInputCodeView resellInputCodeView){
        this.mvpView = resellInputCodeView;
        attachView(mvpView);
    }

    /**loadSucceed
     *
     * @param cdKeys 兑换码
     * @param userCode 客户编码
     */
    public void sellInfo(LifecycleTransformer lifecycleTransformer,String cdKeys, String userCode){
        addSubscription(lifecycleTransformer, apiStores.sellInfo(cdKeys, userCode), new ApiCallback<SellInfoReponse>() {
            @Override
            public void onSuccess(SellInfoReponse model) {
                mvpView.loadSucceed(model);

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
