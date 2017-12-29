package com.shenyu.laikaword.module.us.appsetting.acountbind.presenter;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.module.us.appsetting.acountbind.view.ChangeBindPhoneView;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by shenyu_zxjCode on 2017/12/29 0029.
 */

public class ChangeBindPhonePresenter extends BasePresenter<ChangeBindPhoneView> {

    public ChangeBindPhonePresenter(ChangeBindPhoneView changeBindPhoneView){
            this.mvpView=changeBindPhoneView;
            attachView(mvpView);

    }

    /**
     * 发送更换手机号码验证码
     */
    public void sendChangePhoneCode(LifecycleTransformer lifecycleTransformer,String phone){
       addSubscription(lifecycleTransformer,apiStores.bindPhone(phone, "code"), new ApiCallback<BaseReponse>() {
            @Override
            public void onSuccess(BaseReponse model) {
                mvpView.sendChangePhoneCode(model.isSuccess());
            }

            @Override
            public void onFailure(String msg) {
                mvpView.sendChangePhoneCode(false);
            }

            @Override
            public void onFinish() {

            }
        });
    }

}
