package com.shenyu.laikaword.model.net.retrofit;
import com.shenyu.laikaword.model.net.api.ApiClient;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shenyu_zxjCode on 2017/9/22 0022.
 */

public class RetrofitUtils  {

    public static ApiStores apiStores;
    LifecycleTransformer mLifecycleTransformer;
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

    public synchronized  RetrofitUtils setLifecycleTransformer(LifecycleTransformer lifecycleTransformer){
        this.mLifecycleTransformer=lifecycleTransformer;
        return  retrofitUtils;
    }
    public  synchronized void  addSubscription(Observable observable, Observer subscriber) {
        if (mLifecycleTransformer==null) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(subscriber);
        }else{
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(mLifecycleTransformer).subscribe(subscriber);
        }


    }
    //RXjava取消注册，以避免内存泄露

//    public  void onUnsubscribe() {
//        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
//            mCompositeSubscription.unsubscribe();
//
//        }
//
//    }
}
