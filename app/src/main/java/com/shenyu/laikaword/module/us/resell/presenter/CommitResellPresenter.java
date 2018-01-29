package com.shenyu.laikaword.module.us.resell.presenter;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.module.us.resell.view.CommitResellView;
import com.shenyu.laikaword.module.us.resell.view.ResellInputCodeView;
import com.trello.rxlifecycle2.LifecycleTransformer;

public class CommitResellPresenter extends BasePresenter<CommitResellView> {
    public CommitResellPresenter(CommitResellView commitResellView){
        this.mvpView = commitResellView;
        attachView(mvpView);
    }

    /**
     * 转卖申请接口
     * @param cdKeys 兑换码
     * @param discount 折扣价格
     */
    public void sellApply(LifecycleTransformer lifecycleTransformer,String cdKeys, String discount){
            addSubscription(lifecycleTransformer, apiStores.sellApply(cdKeys, discount), new ApiCallback<BaseReponse>() {
                @Override
                public void onSuccess(BaseReponse model) {
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
