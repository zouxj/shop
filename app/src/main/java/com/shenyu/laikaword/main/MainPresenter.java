package com.shenyu.laikaword.main;

import android.app.Activity;
import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.CircleTransform;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.shenyu.laikaword.rxbus.event.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.shenyu.laikaword.rxbus.event.Event;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 首页业务
 */

public class MainPresenter extends BasePresenter<MainView> {

    private CompositeSubscription rxSubscriptions = new CompositeSubscription();
    public MainPresenter(MainView mainView){
        this.mvpView = mainView;
        attachView(mvpView);
    }
    public void requestData(){
        //TODO 请求商品数据
        mvpView.isLoading();
        addSubscription(apiStores.getMainShop(), new ApiCallback<ShopMainReponse>() {
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
        List<String> moreList = new ArrayList<>();
        for (int i=0;i<10;i++){
            moreList.add("more"+i);
        }
        mvpView.loadMore(moreList);
    }

    /**
     * 下拉刷新
     */
    public void loadRefresh(){
        List<String> refesh = new ArrayList<>();
        for (int i=0;i<10;i++){
            refesh.add("refrsh"+i);
        }
        mvpView.refreshPull(refesh);
    }

    /**
     * 加载头像
     * @param headURl
     * @param headImg
     */
    public void setImgHead(String headURl, ImageView headImg){
        Picasso.with(UIUtil.getContext()).load(headURl) .placeholder(R.mipmap.left_user_icon)
                .error(R.mipmap.left_user_icon).resize(50, 50).transform(new CircleTransform()).into(headImg);
    }

    /**
     * 定时刷新页面
     */
    public void timeTask(){
        rx.Observable.interval(30000,30000, TimeUnit.MILLISECONDS).take(Integer.MAX_VALUE).observeOn(Schedulers.io()).flatMap(new Func1<Long, Observable<ShopMainReponse>>() {
            @Override
            public rx.Observable<ShopMainReponse> call(Long aLong) {
                return RetrofitUtils.getRetrofitUtils().apiStores.getMainShop();
            }
        }).subscribe(new Action1<ShopMainReponse>() {
            @Override
            public void call(ShopMainReponse shopBeanReponse) {
                SPUtil.saveObject(Constants.MAIN_SHOP_KEY,shopBeanReponse.getPayload().getGoods());
                RxBus.getDefault().post(new Event(EventType.ACTION_MAIN_SETDATE,shopBeanReponse.getPayload().getGoods()));
            }
        });
    }

    /**
     * 移除任务
     */
    public void romveTask(){
        if (null!=rxSubscriptions) {
            rxSubscriptions.unsubscribe();
            rxSubscriptions=null;
        }
    }
}
