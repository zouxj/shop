package com.shenyu.laikaword.module.home.ui.viewpager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.BetterHighlightSpan;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.model.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.BaseViewPager;
import com.shenyu.laikaword.model.bean.reponse.GoodBean;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.GridSpacingItemDecoration;
import com.shenyu.laikaword.module.login.ui.activity.LoginActivity;
import com.shenyu.laikaword.module.goods.order.ui.activity.ConfirmOrderActivity;
import com.shenyu.laikaword.model.web.ShopDateilActivity;
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
import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by shenyu_zxjCode on 2017/10/16 0016.
 */

public class MainListViewPager extends BaseViewPager {

    RecyclerView recycleView;
    CommonAdapter commonAdapter;
    EmptyWrapper emptyWrappe;
    List<ShopMainReponse.GoodsBean> goods;
    List<GoodBean> listBeans=new ArrayList<>();
    private String mType;
    private Subscription mRxSub;

    public MainListViewPager(Activity activity,String type) {
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
                StringBuilder sb = new StringBuilder(listBean.getDiscount());//构造一个StringBuilder对象
                sb.insert(1, ".");//在指定的
                sb.append("折");
                spannableText(holder, listBean, sb);
//                holder.setText(R.id.tv_main_shop_original_price, "¥"+listBean.getOriginPrice());
                holder.setText(R.id.tv_main_shop_price, "¥"+listBean.getDiscountPrice());
//                holder.setText(R.id.tv_main_shop_surplus, StringUtil.formatIntger(listBean.getStock())>=5?"":"还剩"+StringUtil.formatIntger(listBean.getStock())+"张");
                holder.setText(R.id.tv_main_shop_seller,listBean.getNickName());
                TextView tvBuy = holder.getView(R.id.tv_main_shop_purchase);
                LinearLayout linearLayout =holder.getView(R.id.lv_main_shop);
                if (StringUtil.validText(listBean.getStock())){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        tvBuy.setBackground(StringUtil.formatIntger(listBean.getStock())>0
                                                ?UIUtil.getDrawable(R.drawable.bg_main_goumai_rectangle):UIUtil.getDrawable(R.drawable.bg_main_goumai_default_rectangle));
                    }else {
                        tvBuy.setBackgroundResource(StringUtil.formatIntger(listBean.getStock())>0
                                ?R.drawable.bg_main_goumai_rectangle:R.drawable.bg_main_goumai_default_rectangle);
                    }
                    tvBuy.setText(StringUtil.formatIntger(listBean.getStock())>0
                            ?"抢购":"已抢光");
                    tvBuy.setEnabled(StringUtil.formatIntger(listBean.getStock())>0);
                    linearLayout.setEnabled(StringUtil.formatIntger(listBean.getStock())>0);

                }
//                StringBuilder sb = new StringBuilder(listBean.getDiscount());//构造一个StringBuilder对象
//                sb.insert(1, ".");//在指定的
//                holder.setText(R.id.tv_mian_shop_discount,sb.toString()+"折");
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
    BetterHighlightSpan bgcolorSpan = new BetterHighlightSpan(UIUtil.getColor(R.color.app_theme_red),1);
    ForegroundColorSpan textcolorSpan = new ForegroundColorSpan(UIUtil.getColor(R.color.white));
    RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(0.7f);
    RelativeSizeSpan sizeSpan06 = new RelativeSizeSpan(1.0f);
    private void spannableText(ViewHolder holder, GoodBean listBean, StringBuilder sb) {
//        Spanned spanned=Html.fromHtml("<span style='background-color:#ff7b02'>"+sb.toString()+"</span>"+"<font>" + "<big>"+listBean.getGoodsName() +"</big></font> ");
        SpannableString spannableString = new SpannableString(sb.toString()+listBean.getGoodsName());
        spannableString.setSpan(sizeSpan06, 10, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(bgcolorSpan, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(textcolorSpan, 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan01, 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        TextView name= holder.getView(R.id.tv_main_shop_name);
        name.setText(spannableString);
    }

    /**
 * 设置商品数据
 * @param mType
 */
private void  setData(String mType){
        if (null==goods)
        return;
        listBeans.clear();
        for (ShopMainReponse.GoodsBean goodsBeans:goods){
        if (goodsBeans.getName().equals(mType)){
            listBeans.addAll(goodsBeans.getList());
            break;
        }
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
            Map<Integer,List> listMap = (Map) myEvent.object;
            List<GoodBean> goodBeans= listMap.get(mType);
            if(goodBeans!=null) {
                for (GoodBean goodBean : goodBeans) {
                    listBeans.add(goodBean);
                }
                emptyWrappe.notifyDataSetChanged();
            }
        break;
        case EventType.ACTION_PULL_REFRESH://下拉刷新
            goods = (List<ShopMainReponse.GoodsBean>) myEvent.object;
            if (null != goods && goods.size() > 0)
                setData(mType);
        break;
        case EventType.ACTION_MAIN_SETDATE:
        goods = (List<ShopMainReponse.GoodsBean>) myEvent.object;
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
