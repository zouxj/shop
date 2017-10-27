package com.shenyu.laikaword.base;

import com.shenyu.laikaword.model.net.api.ApiClient;
import com.shenyu.laikaword.model.net.retrofit.ApiStores;
import com.trello.rxlifecycle2.LifecycleTransformer;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by shenyu_zxjCode on 2017/9/21 0021.
 */

public class BasePresenter<V> {
    public V mvpView;



    protected ApiStores apiStores;

    private CompositeSubscription mCompositeSubscription;



    public void attachView(V mvpView) {

        this.mvpView = mvpView;
        apiStores = ApiClient.retrofit().create(ApiStores.class);

    }





    public void detachView() {

        this.mvpView = null;

        onUnsubscribe();

    }





    //RXjava取消注册，以避免内存泄露

    private void onUnsubscribe() {

        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {

            mCompositeSubscription.unsubscribe();

        }

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
}
