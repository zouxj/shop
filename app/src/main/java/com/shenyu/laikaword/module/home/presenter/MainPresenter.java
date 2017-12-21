package com.shenyu.laikaword.module.home.presenter;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.model.bean.reponse.GoodBean;
import com.shenyu.laikaword.model.bean.reponse.GoodsBean;
import com.shenyu.laikaword.model.bean.reponse.GoodsListReponse;
import com.shenyu.laikaword.module.home.view.MainView;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zxj.utilslibrary.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
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

    /**
     * 上拉加载更多
     * @param lifecycleTransformer
     * @return
     */
    int page =2;
    public  void  onLoadMore(LifecycleTransformer lifecycleTransformer,int type){
        String types = null;
        switch (type){
            case 0:
                types="yd";
                break;
            case 1:
                types="jd";
                break;
            case 2:
                types="lt";
                break;
            case 3:
                types="dx";
                break;
        }
        addSubscription(lifecycleTransformer,apiStores.getGoodsList(types,page,20), new ApiCallback<GoodsListReponse>() {
            @Override
            public void onSuccess(GoodsListReponse model) {
                List<GoodBean> list=new ArrayList<>();
                if (model.isSuccess()&&model.getPayload()!=null&&model.getPayload().size()>0) {
                    for (GoodsListReponse.PayloadBean payloadBean:model.getPayload()){
                        GoodBean goodBean = new GoodBean();
                        goodBean.setDiscount(payloadBean.getDiscount());
                        goodBean.setDiscountPrice(payloadBean.getDiscountPrice());
                        goodBean.setGoodsId(payloadBean.getGoodsId());
                        goodBean.setGoodsImage(payloadBean.getGoodsImage());
                        goodBean.setNickName(payloadBean.getNickName());
                        goodBean.setStock(payloadBean.getStock());
                        goodBean.setSellerAvatar(payloadBean.getSellerAvatar());
                        goodBean.setGoodsName(payloadBean.getGoodsName());
                        goodBean.setOriginPrice(payloadBean.getOriginPrice());
                        list.add(goodBean);
                    }
                   page++;

                }
                mvpView.loadMore(list);
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
     * 下拉刷新
     */
    public void loadRefresh(LifecycleTransformer lifecycleTransformer){
        mvpView.isLoading();
        addSubscription(lifecycleTransformer,apiStores.getMainShop(), new ApiCallback<ShopMainReponse>() {
            @Override
            public void onSuccess(ShopMainReponse model) {
                if (model.isSuccess()) {
                    mvpView.showShop(model);
                    page=2;
//                    SPUtil.saveObject(Constants.MAIN_SHOP_KEY,model);
//                    RxBus.getDefault().post(new Event(EventType.ACTION_MAIN_SETDATE,model.getPayload().getGoods()));
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
    public void timeTask(LifecycleTransformer lifecycleTransformer){
        Observable.interval(30000,30000, TimeUnit.MILLISECONDS).compose(lifecycleTransformer).take(Integer.MAX_VALUE).observeOn(Schedulers.io()).flatMap(new Function<Long, ObservableSource<ShopMainReponse>>() {

            @Override
            public ObservableSource<ShopMainReponse> apply(Long aLong) throws Exception {
                return RetrofitUtils.getRetrofitUtils().apiStores.getMainShop();
            }
        }).subscribe(new ApiCallback<ShopMainReponse>() {
            @Override
            public void onSuccess(ShopMainReponse shopMainReponse) {
                SPUtil.saveObject(Constants.MAIN_SHOP_KEY,shopMainReponse);
                RxBus.getDefault().post(new Event(EventType.ACTION_MAIN_SETDATE,shopMainReponse.getPayload().getGoods()));
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }});
    }
}
