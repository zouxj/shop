package com.shenyu.laikaword.module.us.appsetting.acountbind.presenter;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.module.us.appsetting.acountbind.view.ChangeBindPhoneInputCodeView;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by zxjCo on 2018/1/3.
 */

public class ChangeBindPhoneInputCodePresent extends BasePresenter<ChangeBindPhoneInputCodeView> {

    public ChangeBindPhoneInputCodePresent(ChangeBindPhoneInputCodeView changeBindPhoneInputCodeView){
        this.mvpView=changeBindPhoneInputCodeView;
        attachView(mvpView);

    }
    public void sendChangePhoneCode(LifecycleTransformer lifecycleTransformer, String phone,String code){
        /**
         * 发送更换手机号码验证码
         */
            addSubscription(lifecycleTransformer,apiStores.changePhone(phone,code), new ApiCallback<BaseReponse>() {
                @Override
                public void onSuccess(BaseReponse model) {
                    mvpView.verifyMsgCode(model.isSuccess(),model.getError().getMessage());
                }

                @Override
                public void onFailure(String msg) {
                    mvpView.verifyMsgCode(false,msg);
                }

                @Override
                public void onFinish() {

                }
            });
    }
}
