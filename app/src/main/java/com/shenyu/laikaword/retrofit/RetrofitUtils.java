package com.shenyu.laikaword.retrofit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by shenyu_zxjCode on 2017/9/22 0022.
 */

public class RetrofitUtils {

    public static ApiStores apiStores;
    private CompositeSubscription mCompositeSubscription;

    private static RetrofitUtils retrofitUtils;
    private RetrofitUtils(){

    }

    public static RetrofitUtils getRetrofitUtils(){
        if (retrofitUtils==null){
            synchronized (RetrofitUtils.class){
                if (null==retrofitUtils)
                    retrofitUtils = new RetrofitUtils();

            }
        }
        apiStores = ApiClient.retrofit().create(ApiStores.class);
        return retrofitUtils;
    }

    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));

    }
    //RXjava取消注册，以避免内存泄露

    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();

        }

    }
}
