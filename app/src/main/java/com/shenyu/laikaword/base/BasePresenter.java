package com.shenyu.laikaword.base;

import com.shenyu.laikaword.model.net.api.ApiClient;
import com.shenyu.laikaword.model.net.retrofit.ApiStores;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by shenyu_zxjCode on 2017/9/21 0021.
 */

public class BasePresenter<V> {
    public V mvpView;



    protected ApiStores apiStores;




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



    }





    public void addSubscription(LifecycleTransformer lifecycleTransformer,Observable observable, Observer subscriber) {

                observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               .compose(lifecycleTransformer) .subscribe(subscriber);

    }
}
