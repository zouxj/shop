package com.shenyu.laikaword.module.home.presenter;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.module.home.view.GoodsDetailsView;
import com.trello.rxlifecycle2.LifecycleTransformer;

public class GoodsPresenter extends BasePresenter<GoodsDetailsView> {
    public GoodsPresenter(GoodsDetailsView mainView, LifecycleTransformer mlifecycleTransformer){
        this.mvpView = mainView;
        attachView(mvpView);
    }
}
