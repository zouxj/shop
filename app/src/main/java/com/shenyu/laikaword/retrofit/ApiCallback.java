package com.shenyu.laikaword.retrofit;

import com.zxj.utilslibrary.utils.LogUtil;
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
            ToastUtil.showToastShort(msg);
            onFailure(msg);

        } else {

            onFailure(e.getMessage());

        }

        onFinish();

    }



    @Override

    public void onNext(M model) {
        onSuccess(model);

    }



    @Override

    public void onCompleted() {
        onFinish();

    }
}
