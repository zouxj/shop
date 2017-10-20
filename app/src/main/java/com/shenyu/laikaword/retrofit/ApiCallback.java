package com.shenyu.laikaword.retrofit;

import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.rxbus.RxBus;
import com.shenyu.laikaword.rxbus.event.Event;
import com.shenyu.laikaword.rxbus.event.EventType;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.ToastUtil;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * Created by shenyu_zxjCode on 2017/9/21 0021.
 */

public abstract class ApiCallback<M> extends Subscriber<M> {
    public abstract void onSuccess(M model);
    public abstract void onFailure(String msg);
    public abstract void onFinish();
    public  void onStarts(){}

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
            onFailure(e.getMessage());

        }

        onFailure(e.getMessage());

    }



    @Override

    public void onNext(M model) {
        BaseReponse apiModel = (BaseReponse) model;
        if (null!=apiModel.getError()&&apiModel.getError().getCode() == ErrorCode.code) {

            ToastUtil.showToastShort(apiModel.getError().getMessage()+"请重新登录");
            //TODO do things
            SPUtil.removeSp(Constants.LOGININFO_KEY);
            RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER, null));
            onFinish();
        }else {
            onSuccess(model);
        }


    }



    @Override

    public void onCompleted() {
        onFinish();

    }

    @Override
    public void onStart() {
        onStarts();
    }
}
