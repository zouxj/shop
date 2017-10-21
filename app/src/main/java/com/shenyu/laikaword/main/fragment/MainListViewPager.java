package com.shenyu.laikaword.main.fragment;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.adapter.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.BaseViewPager;
import com.shenyu.laikaword.bean.reponse.GoodBean;
import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.shenyu.laikaword.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.GridDividerItemDecoration;
import com.shenyu.laikaword.helper.GridSpacingItemDecoration;
import com.shenyu.laikaword.module.login.activity.LoginActivity;
import com.shenyu.laikaword.module.shop.activity.ConfirmOrderActivity;
import com.shenyu.laikaword.module.shop.activity.ShopDateilActivity;
import com.shenyu.laikaword.rxbus.RxBus;
import com.shenyu.laikaword.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.rxbus.RxSubscriptions;
import com.shenyu.laikaword.rxbus.event.Event;
import com.shenyu.laikaword.rxbus.event.EventType;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SPUtil;
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
        gridLayoutManager.setAutoMeasureEnabled(true);
        recycleView.setLayoutManager(gridLayoutManager);
        recycleView.addItemDecoration(new GridSpacingItemDecoration(2,(int) UIUtil.dp2px(10),true));
        return view;
    }

    @Override
    public void initData() {
        goods = (List<ShopMainReponse.PayloadBean.GoodsBean>) SPUtil.readObject(Constants.MAIN_SHOP_KEY);

        commonAdapter=new CommonAdapter<GoodBean>(R.layout.item_home_shop,listBeans) {
            @Override
            protected void convert(ViewHolder holder, final GoodBean listBean, int position) {
                Picasso.with(UIUtil.getContext()).load(listBean.getGoodsImage()).placeholder(R.mipmap.yidong_icon).error(R.mipmap.yidong_icon).into((ImageView) holder.getView(R.id.iv_main_shop_img));
                holder.setText(R.id.tv_main_shop_name, listBean.getGoodsName());
                holder.setText(R.id.tv_main_shop_original_price, "￥"+listBean.getOriginPrice());
                holder.setText(R.id.tv_main_shop_price, "￥"+listBean.getDiscountPrice());
                holder.setText(R.id.tv_main_shop_surplus, "还剩"+listBean.getStock());
                holder.setText(R.id.tv_main_shop_seller,listBean.getNickName()+" 出售");
                holder.setText(R.id.tv_mian_shop_discount,listBean.getDiscount()+"折");
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
        emptyWrappe.setEmptyView(R.layout.empty_view);
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
        switch (position){
        case 0:
        for (ShopMainReponse.PayloadBean.GoodsBean goodsBeans:goods){
        if (goodsBeans.getType().equals("yd")){
        listBeans.clear();
        listBeans.addAll(goodsBeans.getList());
        }
        }
        break;
        case 1:
        for (ShopMainReponse.PayloadBean.GoodsBean goodsBeans:goods){
        if (goodsBeans.getType().equals("jd")){
        listBeans.clear();
        listBeans.addAll(goodsBeans.getList());
        }
        }
        break;
        case 2:
        for (ShopMainReponse.PayloadBean.GoodsBean goodsBeans:goods){
        if (goodsBeans.getType().equals("lt")){
        listBeans.clear();
        listBeans.addAll(goodsBeans.getList());
        }
        }
        break;
        case 3:
        for (ShopMainReponse.PayloadBean.GoodsBean goodsBeans:goods){
        if (goodsBeans.getType().equals("dx")){
        listBeans.clear();
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
