package com.shenyu.laikaword.module.us.appsetting.acountbind.presenter;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.module.us.appsetting.acountbind.view.ChangeBindPhoneView;

/**
 * Created by shenyu_zxjCode on 2017/12/29 0029.
 */

public class ChangeBindPhonePresenter extends BasePresenter<ChangeBindPhoneView> {

    public ChangeBindPhonePresenter(ChangeBindPhoneView changeBindPhoneView){
            this.mvpView=changeBindPhoneView;
            attachView(mvpView);

    }



}
