package com.shenyu.laikaword.http.uitls;

import android.widget.Toast;

import com.zxj.utilslibrary.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class ExceptionSubscriber<T> extends Subscriber<T> {

    private SimpleCallback<T> simpleCallback;

    public ExceptionSubscriber(SimpleCallback simpleCallback){
        this.simpleCallback = simpleCallback;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(simpleCallback != null)
            simpleCallback.onStart();
    }

    @Override
    public void onCompleted() {
        if(simpleCallback != null)
            simpleCallback.onComplete();
        if(!this.isUnsubscribed())
            this.unsubscribe();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            ToastUtil.showToastLong("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            ToastUtil.showToastShort("网络中断，请检查您的网络状态");
        } else {
            ToastUtil.showToastShort(e.getMessage());
        }
        if(simpleCallback != null)
            simpleCallback.onComplete();
        if(!this.isUnsubscribed())
            this.unsubscribe();
    }

    @Override
    public void onNext(T t) {
        if(simpleCallback != null)
            simpleCallback.onNext(t);
    }
}