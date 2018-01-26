package com.shenyu.laikaword.module.us.resell.presenter;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.module.us.resell.view.CommitResellView;
import com.shenyu.laikaword.module.us.resell.view.ResellInputCodeView;

public class CommitResellPresenter extends BasePresenter<CommitResellView> {
    public CommitResellPresenter(CommitResellView commitResellView){
        this.mvpView = commitResellView;
        attachView(mvpView);
    }
}
