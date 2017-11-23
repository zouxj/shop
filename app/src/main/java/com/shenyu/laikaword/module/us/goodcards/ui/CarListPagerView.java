package com.shenyu.laikaword.module.us.goodcards.ui;

import android.annotation.SuppressLint;
import
android.app.Activity;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.adapter.ViewHolder;
import com.shenyu.laikaword.model.adapter.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.BaseViewPager;
import com.shenyu.laikaword.model.bean.reponse.CarPagerReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.goods.pickupgoods.ui.activity.PickUpActivity;
import com.shenyu.laikaword.module.goods.pickupgoods.ui.activity.PickUpTelActivity;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by shenyu_zxjCode on 2017/10/16 0016.
 */

public class CarListPagerView extends BaseViewPager<CarPagerReponse> {
    RecyclerView recyclerView;
    private int mType;
    private List<CarPagerReponse.Bean> beanList;
    public CarListPagerView(Activity activity) {
        super(activity);
    }


    @Override
    public View initView() {
        View view = UIUtil.inflate(R.layout.fragment_car_list);
        recyclerView = view.findViewById(R.id.rlv_view);
//        subscribeEvent();
        return view;
    }

    @Override
    public void initData(CarPagerReponse carPagerReponse,int type) {
        beanList = new ArrayList<>();
        this.mType=type;
        if (carPagerReponse!=null) {
            switch (type) {
                case 0:
                    //TODO 全部的卡片
                    beanList.clear();
                    for (CarPagerReponse.Bean bean : carPagerReponse.getPayload().getJd().getList()) {
                        beanList.add(bean);
                    }
                    for (CarPagerReponse.Bean bean : carPagerReponse.getPayload().getDx().getList()) {
                        beanList.add(bean);
                    }
                    for (CarPagerReponse.Bean bean : carPagerReponse.getPayload().getYd().getList()) {
                        beanList.add(bean);
                    }
                    for (CarPagerReponse.Bean bean : carPagerReponse.getPayload().getLt().getList()) {
                        beanList.add(bean);
                    }
                    break;
                case 1:
                    //TODO 京东卡片
                    beanList.clear();
                    beanList.addAll(carPagerReponse.getPayload().getJd().getList());
                    setData(beanList);
                    break;
                case 2:
                    //TODO 移动卡片
                    beanList.clear();
                    beanList.addAll(carPagerReponse.getPayload().getYd().getList());
                    break;
                case 3:
                    //TODO 联通卡片
                    beanList.clear();
                    beanList.addAll(carPagerReponse.getPayload().getLt().getList());
                    break;
                case 4:
                    //TODO 电信卡片
                    beanList.clear();
                    beanList.addAll(carPagerReponse.getPayload().getDx().getList());
                    break;

            }
        }
        setData(beanList);
    }

    public void setData(List<CarPagerReponse.Bean> beanList){
        CommonAdapter commonAdapter=    new CommonAdapter<CarPagerReponse.Bean>(R.layout.item_carpackage,beanList) {
            @Override
            protected void convert(ViewHolder holder, final CarPagerReponse.Bean bean, int position) {
                holder.setText(R.id.tv_kpage_count,bean.getQuantity()+"张");
                holder.setText(R.id.tv_page_name,bean.getGoodsName());
                ImageUitls.loadImg(bean.getGoodsImage(),(ImageView) holder.getView(R.id.iv_page_img));
                holder.setOnClickListener(R.id.tv_tihuo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 手机号和京东卡提货
                        if (bean.getGoodsType().equals("jd"))
                            IntentLauncher.with(mActivity).putObjectString("bean",bean).launch(PickUpActivity.class);
                        else
                            IntentLauncher.with(mActivity).putObjectString("bean",bean).launch(PickUpTelActivity.class);
                    }
                });
            }
        };
        recyclerView.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.HORIZONTAL,(int) UIUtil.dp2px(5),UIUtil.getColor(R.color.main_bg_gray)));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        EmptyWrapper emptyWrapper = new EmptyWrapper(commonAdapter);
        emptyWrapper.setEmptyView(R.layout.empty_view,UIUtil.getString(R.string.pager_empty));
        recyclerView.setAdapter(emptyWrapper);
    }
}
