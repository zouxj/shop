package com.shenyu.laikaword.http.uitls;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public interface SimpleCallback<T> {
    void onStart();
    void onNext(T t);
    void onComplete();
}
