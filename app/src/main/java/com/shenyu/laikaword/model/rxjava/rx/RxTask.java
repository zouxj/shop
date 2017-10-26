package com.shenyu.laikaword.model.rxjava.rx;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by shenyu_zxjCode on 2017/10/26 0026.
 * RxJava响应式封装
 *
 */

public class RxTask {
//    private static RxTask rxTask;
    CompositeSubscription mCompositeSubscription;
//    private RxTask(){
//
//    }

//    public static RxTask getRxTask(){
//        if (rxTask==null){
//            synchronized (RetrofitUtils.class){
//                if (null==rxTask)
//                    rxTask = new RxTask();
//
//            }
//        }
//        return rxTask;
//    }
    public synchronized   void   addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));

    }
    //RXjava取消注册，以避免内存泄露

    public synchronized void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();

        }

    }
}
