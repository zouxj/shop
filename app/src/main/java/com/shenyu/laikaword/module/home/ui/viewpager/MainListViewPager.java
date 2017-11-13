package com.shenyu.laikaword.module.home.ui.viewpager;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.adapter.ViewHolder;
import com.shenyu.laikaword.model.adapter.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.BaseViewPager;
import com.shenyu.laikaword.model.bean.reponse.GoodBean;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.GridSpacingItemDecoration;
import com.shenyu.laikaword.module.login.ui.activity.LoginActivity;
import com.shenyu.laikaword.module.goods.order.ui.activity.ConfirmOrderActivity;
import com.shenyu.laikaword.ui.web.ShopDateilActivity;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by shenyu_zxjCode on 2017/10/16 0016.
 */

public class MainListViewPager extends BaseViewPager {

    RecyclerView recycleView;
    CommonAdapter commonAdapter;
    EmptyWrapper emptyWrappe;
    List<ShopMainReponse.PayloadBean.GoodsBean> goods;
    List<GoodBean> listBeans=new ArrayList<>();
    private int mType=0;
    private Subscription mRxSub;

    public MainListViewPager(Activity activity,int type) {
        super(activity);
        this.mType = type;

    }

    @Override
    public View initView() {
        View view = UIUtil.inflate(R.layout.fragment_list);
        recycleView = view.findViewById(R.id.recycle_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,2);
        recycleView.setLayoutManager(gridLayoutManager);
        recycleView.addItemDecoration(new GridSpacingItemDecoration(2,(int) UIUtil.dp2px(10),true));
        return view;
    }

    @Override
    public void initData() {
        ShopMainReponse shopMainReponse=  (ShopMainReponse)SPUtil.readObject(Constants.MAIN_SHOP_KEY);
        if (shopMainReponse!=null) {
            goods = shopMainReponse.getPayload().getGoods();
        }
        commonAdapter=new CommonAdapter<GoodBean>(R.layout.item_home_shop,listBeans) {
            @Override
            protected void convert(ViewHolder holder, final GoodBean listBean, int position) {
                ImageUitls.loadImg(listBean.getGoodsImage(),(ImageView) holder.getView(R.id.iv_main_shop_img));
                holder.setText(R.id.tv_main_shop_name, listBean.getGoodsName());
//                holder.setText(R.id.tv_main_shop_original_price, "￥"+listBean.getOriginPrice());
                holder.setText(R.id.tv_main_shop_price, "￥"+listBean.getDiscountPrice());
                holder.setText(R.id.tv_main_shop_surplus, StringUtil.formatIntger(listBean.getStock())>=5?"":"还剩"+StringUtil.formatIntger(listBean.getStock())+"张");
                holder.setText(R.id.tv_main_shop_seller,listBean.getNickName());
                StringBuilder sb = new StringBuilder(listBean.getDiscount());//构造一个StringBuilder对象
                sb.insert(1, ".");//在指定的
                holder.setText(R.id.tv_mian_shop_discount,sb.toString()+"折");
                holder.setOnClickListener(R.id.tv_main_shop_purchase, new View.OnClickListener() {
          @Override
     public void onClick(View view) {
              LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
              if (null==loginReponse) {
                  IntentLauncher.with(mActivity).launch(LoginActivity.class);
                  return;
              }IntentLauncher.with(mActivity).putObjectString("order",listBean).launch(ConfirmOrderActivity.class);
        }
        });
        holder.setOnClickListener(R.id.lv_main_shop, new View.OnClickListener() {
        @Override
    public void onClick(View view) {
        IntentLauncher.with(mActivity).putObjectString("GoodBean",listBean).launch(ShopDateilActivity.class);
        }
        });


        }
        };
        emptyWrappe = new EmptyWrapper(commonAdapter);
        emptyWrappe.setEmptyView(R.layout.empty_view,UIUtil.getString(R.string.shop_empty));
        subscribeEvent();
        recycleView.setAdapter(emptyWrappe);

        setData(mType);
        }

/**
 * 设置商品数据
 * @param position
 */
private void  setData(int position){
        if (null==goods)
        return;
        listBeans.clear();
        switch (position){
        case 0:
        for (ShopMainReponse.PayloadBean.GoodsBean goodsBeans:goods){
        if (goodsBeans.getType().equals("yd")){
        listBeans.addAll(goodsBeans.getList());
        }
        }
        break;
        case 1:
        for (ShopMainReponse.PayloadBean.GoodsBean goodsBeans:goods){
        if (goodsBeans.getType().equals("jd")){
        listBeans.addAll(goodsBeans.getList());
        }
        }
        break;
        case 2:
        for (ShopMainReponse.PayloadBean.GoodsBean goodsBeans:goods){
        if (goodsBeans.getType().equals("lt")){
        listBeans.addAll(goodsBeans.getList());
        }
        }
        break;
        case 3:
        for (ShopMainReponse.PayloadBean.GoodsBean goodsBeans:goods){
        if (goodsBeans.getType().equals("dx")){
        listBeans.addAll(goodsBeans.getList());
        }
        }
        break;
        }
     emptyWrappe.notifyDataSetChanged();
        }

private void subscribeEvent() {
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(Event.class)
        .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
@Override
protected void onEvent(Event myEvent) {
        switch (myEvent.event) {
        case EventType.ACTION_LODE_MORE://上拉加载更多
//                        listBeans.addAll((List)eventType.object);
        break;
        case EventType.ACTION_PULL_REFRESH://下拉刷新
//                        listBeans.clear();
//                        listBeans.addAll((List)eventType.object);
        break;
        case EventType.ACTION_MAIN_SETDATE:
        goods = (List<ShopMainReponse.PayloadBean.GoodsBean>) myEvent.object;
        if (null != goods && goods.size() > 0)
        setData(mType);
        break;
        }
        //        RxBus.getDefault().toObservable(EventType.class).subscribe(new Action1<EventType>() {
//            @Override
//            public void call(EventType eventType) {
//                switch (eventType.action){
//                    case EventType.ACTION_LODE_MORE://上拉加载更多
////                        listBeans.addAll((List)eventType.object);
//                        break;
//                    case EventType.ACTION_PULL_REFRESH://下拉刷新
////                        listBeans.clear();
////                        listBeans.addAll((List)eventType.object);
//                        break;
//                    case EventType.ACTION_MAIN_SETDATE:
//                        goods = (List<ShopMainReponse.PayloadBean.GoodsBean>) eventType.object;
//                        if (null!=goods&&goods.size()>0)
//                            setData(mType);
//                        break;
//                }
//            }
//        });
        }
@Override
public void onError(Throwable e) {
        super.onError(e);
        /**
         * 这里注意: 一旦订阅过程中发生异常,走到onError,则代表此次订阅事件完成,后续将收不到onNext()事件,
         * 即 接受不到后续的任何事件,实际环境中,我们需要在onError里 重新订阅事件!
         */
        subscribeEvent();
        }
        });

        RxSubscriptions.add(mRxSub);
        }
        }
