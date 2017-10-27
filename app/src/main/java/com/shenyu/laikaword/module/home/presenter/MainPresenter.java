package com.shenyu.laikaword.module.home.presenter;

import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.module.home.view.MainView;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.CircleTransform;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.observers.FullArbiterObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * 首页业务
 */

public class MainPresenter extends BasePresenter<MainView> {
    public MainPresenter(MainView mainView, LifecycleTransformer mlifecycleTransformer){
        this.mvpView = mainView;
        attachView(mvpView);
    }
    public void requestData(LifecycleTransformer lifecycleTransformer){
        //TODO 请求商品数据
        mvpView.isLoading();
        addSubscription(lifecycleTransformer,apiStores.getMainShop(), new ApiCallback<ShopMainReponse>() {
            @Override
            public void onSuccess(ShopMainReponse model) {
                if (model.isSuccess())
                mvpView.showShop(model);
            }

            @Override
            public void onFailure(String msg) {
                mvpView.loadFailure();
            }

            @Override
            public void onFinish() {
                mvpView.loadFinished();
            }
        });

    }
    public void onLoadMore(){
//        List<String> moreList = new ArrayList<>();
//        for (int i=0;i<10;i++){
//            moreList.add("more"+i);
//        }
//        mvpView.loadMore(moreList);
    }

    /**
     * 下拉刷新
     */
    public void loadRefresh(LifecycleTransformer lifecycleTransformer){
        mvpView.isLoading();
        addSubscription(lifecycleTransformer,apiStores.getMainShop(), new ApiCallback<ShopMainReponse>() {
            @Override
            public void onSuccess(ShopMainReponse model) {
                if (model.isSuccess()) {
                    SPUtil.saveObject(Constants.MAIN_SHOP_KEY,model);
                    RxBus.getDefault().post(new Event(EventType.ACTION_MAIN_SETDATE,model.getPayload().getGoods()));
                }
            }

            @Override
            public void onFailure(String msg) {
                mvpView.loadFailure();
            }

            @Override
            public void onFinish() {
                mvpView.loadFinished();
            }
        });

    }



    /**
     * 定时刷新页面
     */
    public void timeTask(){
        Observable.interval(30000,30000, TimeUnit.MILLISECONDS).take(Integer.MAX_VALUE).observeOn(Schedulers.io()).flatMap(new Function<Long, ObservableSource<ShopMainReponse>>() {

            @Override
            public ObservableSource<ShopMainReponse> apply(Long aLong) throws Exception {
                return RetrofitUtils.getRetrofitUtils().apiStores.getMainShop();
            }
        }).subscribe(new Observer<ShopMainReponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ShopMainReponse shopMainReponse) {
                SPUtil.saveObject(Constants.MAIN_SHOP_KEY,shopMainReponse);
                RxBus.getDefault().post(new Event(EventType.ACTION_MAIN_SETDATE,shopMainReponse.getPayload().getGoods()));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 移除任务
     */
    public void romveTask(){
//        if (null!=rxSubscriptions) {
//            rxSubscriptions.unsubscribe();
//            rxSubscriptions=null;
//        }
    }
}
