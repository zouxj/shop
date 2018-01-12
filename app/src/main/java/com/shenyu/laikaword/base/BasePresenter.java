package com.shenyu.laikaword.base;

import com.shenyu.laikaword.model.net.api.ApiClient;
import com.shenyu.laikaword.model.net.retrofit.ApiStores;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zxj.utilslibrary.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rx.Subscription;


/**
 * Created by shenyu_zxjCode on 2017/9/21 0021.
 */

public class BasePresenter<V> {
    public V mvpView;


    protected Subscription mRxSub;
    protected ApiStores apiStores;




    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        apiStores = ApiClient.retrofit().create(ApiStores.class);
        subscribeEvent();

    }
    private void subscribeEvent(){
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(Event.class)
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
                    @Override
                    protected void onEvent(Event myEvent) {
                        distribute(myEvent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.e("AddBankPresenter", "onError");
                        /**
                         * 这里注意: 一旦订阅过程中发生异常,走到onError,则代表此次订阅事件完成,后续将收不到onNext()事件,
                         * 即 接受不到后续的任何事件,实际环境中,我们需要在onError里 重新订阅事件!
                         */
                        subscribeEvent();
                    }
                });
        RxSubscriptions.add(mRxSub);
    }
    public void distribute(Event myEvent){}



    public void detachView() {
        this.mvpView = null;

        onUnsubscribe();


    }





    //RXjava取消注册，以避免内存泄露

    public void onUnsubscribe() {

        RxSubscriptions.remove(mRxSub);

    }

    public void addSubscription(LifecycleTransformer lifecycleTransformer,Observable observable, Observer subscriber) {
                observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               .compose(lifecycleTransformer) .subscribe(subscriber);

    }
}
