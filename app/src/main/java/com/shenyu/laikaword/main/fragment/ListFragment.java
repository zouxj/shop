package com.shenyu.laikaword.main.fragment;


import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.bean.reponse.GoodBean;
import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.shenyu.laikaword.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.module.login.activity.LoginActivity;
import com.shenyu.laikaword.module.shop.activity.ConfirmOrderActivity;
import com.shenyu.laikaword.module.shop.activity.ShopDateilActivity;
import com.shenyu.laikaword.rxbus.event.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.shenyu.laikaword.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.rxbus.RxSubscriptions;
import com.shenyu.laikaword.rxbus.event.Event;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ListFragment extends IKWordBaseFragment {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    CommonAdapter commonAdapter;
    List<ShopMainReponse.PayloadBean.GoodsBean> goods;
    List<GoodBean> listBeans=new ArrayList<>();
    private int mType=0;
    private Subscription mRxSub;


    @SuppressLint("ValidFragment")
    public ListFragment(int type){
        this.mType = type;
    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_list;
    }

    @Override
    public void initView(View view) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recycleView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void doBusiness() {
        goods = (List<ShopMainReponse.PayloadBean.GoodsBean>) SPUtil.readObject(Constants.MAIN_SHOP_KEY);
        commonAdapter=new CommonAdapter<GoodBean>(R.layout.item_home_shop,listBeans) {
            @Override
            protected void convert(ViewHolder holder, GoodBean listBean, int position) {
                Picasso.with(UIUtil.getContext()).load(listBean.getGoodsImage()).placeholder(R.mipmap.yidong_icon).error(R.mipmap.yidong_icon).into((ImageView) holder.getView(R.id.iv_main_shop_img));
                holder.setText(R.id.tv_main_shop_name, listBean.getGoodsName());
                holder.setText(R.id.tv_main_shop_original_price, "￥"+listBean.getOriginPrice());
                TextView textView=holder.getView(R.id.tv_main_shop_original_price);
                textView .getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.setText(R.id.tv_main_shop_price, "￥"+listBean.getDiscountPrice());
                holder.setText(R.id.tv_main_shop_surplus, "还剩"+listBean.getStock());
                holder.setText(R.id.tv_main_shop_seller,listBean.getNickName()+" 出售");
                holder.setText(R.id.tv_mian_shop_discount,listBean.getDiscount()+"折");
                holder.setOnClickListener(R.id.tv_main_shop_purchase, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                holder.setOnClickListener(R.id.lv_main_shop, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    IntentLauncher.with(getActivity()).launch(ShopDateilActivity.class);
                    }
                });


            }
        };
        subscribeEvent();
        recycleView.setAdapter(commonAdapter);



    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {
        setData(mType);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("destory");
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
        commonAdapter.notifyDataSetChanged();
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
                        LogUtil.e(TAG, "onError");
                        /**
                         * 这里注意: 一旦订阅过程中发生异常,走到onError,则代表此次订阅事件完成,后续将收不到onNext()事件,
                         * 即 接受不到后续的任何事件,实际环境中,我们需要在onError里 重新订阅事件!
                         */
                        subscribeEvent();
                    }
                });

        RxSubscriptions.add(mRxSub);
    }
    @Override
    public void onDestroyView() {
        RxSubscriptions.remove(mRxSub);
        super.onDestroyView();
    }
}
