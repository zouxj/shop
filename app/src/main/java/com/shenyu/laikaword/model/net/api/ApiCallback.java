package com.shenyu.laikaword.model.net.api;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;

import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.model.net.retrofit.ErrorCode;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.PackageManagerUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by shenyu_zxjCode on 2017/9/21 0021.
 */

public abstract class ApiCallback<M> implements Observer<M> {
    public abstract void onSuccess(M model);
    public abstract void onFailure(String msg);
    public abstract void onFinish();


    @Override

    public void onError(Throwable e) {

        e.printStackTrace();

        if (e instanceof HttpException) {

            HttpException httpException = (HttpException) e;

            //httpException.response().errorBody().string()

            int code = httpException.code();

            String msg = httpException.getMessage();

            LogUtil.d("code=" + code);

            if (code == 504) {

                msg = "网络不给力";

            }

            if (code == 502 || code == 404||code==500) {

                msg = "服务器异常，请稍后再试";

            }
            onFailure(msg);

        } else {
            onFailure("网络异常");

        }


    }


    @Override

    public void onNext(M model) {
        BaseReponse apiModel = (BaseReponse) model;
        if (apiModel.isSuccess()){
            onSuccess(model);
        }else {
            if (apiModel.getError().getCode() == ErrorCode.error_code_login_503) {
                ToastUtil.showToastShort("您的登录已经失效,或者在其他设备登录,请重新登录");
                //TODO do things
                SPUtil.removeSp(Constants.LOGININFO_KEY);
                RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER, null));
            } else if(apiModel.getError().getCode()==506){
                onSuccess(model);
            }else {
                onSuccess(model);
                ToastUtil.showToastShort(apiModel.getError().getMessage());
            }
        }
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }
}
