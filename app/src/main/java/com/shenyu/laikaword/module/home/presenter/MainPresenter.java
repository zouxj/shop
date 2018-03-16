package com.shenyu.laikaword.module.home.presenter;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.model.bean.reponse.GoodBean;
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
import com.zxj.utilslibrary.utils.NetworkUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.UIUtil;

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
        if (!NetworkUtil.isNetworkConnected(UIUtil.getContext())){
            //TODO 判断网络是否可用无网络读取缓存显示
            ShopMainReponse shopMainReponse= (ShopMainReponse) SPUtil.readObject(Constants.MAIN_SHOP_KEY);
            if (null!=shopMainReponse) {
                RxBus.getDefault().post(new Event(EventType.ACTION_MAIN_SETDATE, shopMainReponse.getPayload().getGoods()));
                mvpView.showShop(shopMainReponse);
            }
            return;
        }
        mvpView.isLoading();
        addSubscription(lifecycleTransformer,apiStores.getMainShop(), new ApiCallback<ShopMainReponse>() {
            @Override
            public void onSuccess(ShopMainReponse model) {
                if (model.isSuccess()) {
                    SPUtil.saveObject(Constants.MAIN_SHOP_KEY,model);
                    RxBus.getDefault().post(new Event(EventType.ACTION_MAIN_SETDATE,model.getPayload().getGoods()));
                    mvpView.showShop(model);

                }
            }

            @Override
            public void onFailure(String msg) {
                mvpView.loadFailure();
            }

            @Override
            public void onFinish() {

            }
        });

    }

    /**
     * 上拉加载更多
     * @param lifecycleTransformer
     * @return
     */
    int page =2;
    public  void  onLoadMore(LifecycleTransformer lifecycleTransformer,String type){
        addSubscription(lifecycleTransformer,apiStores.getGoodsList(type,page,20), new ApiCallback<GoodsListReponse>() {
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
                mvpView.loadSucceed(model);
            }

            @Override
            public void onFailure(String msg) {
                mvpView.loadFailure();
            }

            @Override
            public void onFinish() {

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
                    SPUtil.saveObject(Constants.MAIN_SHOP_KEY,model);
                    RxBus.getDefault().post(new Event(EventType.ACTION_MAIN_SETDATE,model.getPayload().getGoods()));
                    mvpView.showShop(model);
                    mvpView.loadSucceed(model);
                }
            }

            @Override
            public void onFailure(String msg) {
                mvpView.loadFailure();
            }

            @Override
            public void onFinish() {

            }
        });

    }

    /**
     * 数据分类
     * @param shopBeanReponse
     */
    public int feileiItem(ShopMainReponse shopBeanReponse) {
        for (int i = 0; i < shopBeanReponse.getPayload().getGoods().size(); i++) {
            if (shopBeanReponse.getPayload().getGoods().get(i).getType().equals("hotSell")) {
                if (shopBeanReponse.getPayload().getGoods().get(i).getList().size() > 0) {
                    return 0;
                }
            }
            if (shopBeanReponse.getPayload().getGoods().get(i).getType().equals("yd")) {
                if (shopBeanReponse.getPayload().getGoods().get(i).getList().size() > 0) {
                    return 1;
                }
            }
            if (shopBeanReponse.getPayload().getGoods().get(i).getType().equals("jd")) {
                if (shopBeanReponse.getPayload().getGoods().get(i).getList().size() > 0) {
                    return 2;
                }
            }
            if (shopBeanReponse.getPayload().getGoods().get(i).getType().equals("lt")) {
                if (shopBeanReponse.getPayload().getGoods().get(i).getList().size() > 0) {
                    return 3;
                }
            }
            if (shopBeanReponse.getPayload().getGoods().get(i).getType().equals("dx")) {
                if (shopBeanReponse.getPayload().getGoods().get(i).getList().size() > 0) {
                    return 4;
                }
            }
        }
        return 0;
    }

    /**
     * 定时刷新页面
     */
    public void timeTask(LifecycleTransformer lifecycleTransformer){
        Observable.interval(0,30,TimeUnit.SECONDS).compose(lifecycleTransformer).take(Integer.MAX_VALUE).observeOn(Schedulers.io()).flatMap(new Function<Long, ObservableSource<ShopMainReponse>>() {

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

    @Override
    public void distribute(Event myEvent) {
        mvpView.subscribeEvent(myEvent);
    }
}
