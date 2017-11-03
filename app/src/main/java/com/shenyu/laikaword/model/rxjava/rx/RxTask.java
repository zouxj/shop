package com.shenyu.laikaword.model.rxjava.rx;


import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import rx.functions.Func1;

/**
 * Created by shenyu_zxjCode on 2017/10/26 0026.
 * RxJava响应式封装
 *
 */

public class RxTask {
//    private static RxTask rxTask;
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
    public synchronized   void   addSubscription(LifecycleTransformer lifecycleTransformer,Observable observable, Observer subscriber) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               .compose(lifecycleTransformer) .subscribe(subscriber);

    }
    //RXjava取消注册，以避免内存泄露

    public synchronized void onUnsubscribe() {


    }

    public static Observable<Long> countdown(LifecycleTransformer lifecycleTransformer,int time) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS).compose(lifecycleTransformer)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long,Long>() {
                    @Override
                    public Long apply(Long o) throws Exception {
                        return countTime - o;
                    }
                })
                .take(countTime + 1);

    }

}
