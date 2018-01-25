package com.shenyu.laikaword.module.us.resell.presenter;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.module.us.resell.view.ResellInputCodeView;

public class ResellInputCodePresenter extends BasePresenter<ResellInputCodeView> {

    public ResellInputCodePresenter(ResellInputCodeView resellInputCodeView){
        this.mvpView = resellInputCodeView;
        attachView(mvpView);
    }
}
