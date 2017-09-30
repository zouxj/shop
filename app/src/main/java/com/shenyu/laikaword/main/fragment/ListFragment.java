package com.shenyu.laikaword.main.fragment;


import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.MainItemSpaceItemDecoration;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.module.shop.activity.ConfirmOrderActivity;
import com.shenyu.laikaword.module.shop.activity.ShopDateilActivity;
import com.shenyu.laikaword.rxbus.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ListFragment extends IKWordBaseFragment {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    CommonAdapter commonAdapter;
    List<ShopMainReponse.PayloadBean.GoodsBean> goods;
    List<ShopMainReponse.PayloadBean.GoodsBean.ListBean> listBeans=new ArrayList<>();
    private int mType=0;


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
        commonAdapter=new CommonAdapter<ShopMainReponse.PayloadBean.GoodsBean.ListBean>(R.layout.item_home_shop,listBeans) {
            @Override
            protected void convert(ViewHolder holder, ShopMainReponse.PayloadBean.GoodsBean.ListBean listBean, int position) {
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
                      IntentLauncher.with(getActivity()).launch(ConfirmOrderActivity.class);
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
        recycleView.setAdapter(commonAdapter);
        RxBus.getDefault().toObservable(EventType.class).subscribe(new Action1<EventType>() {
            @Override
            public void call(EventType eventType) {
                switch (eventType.action){
                    case EventType.ACTION_LODE_MORE://上拉加载更多
//                        listBeans.addAll((List)eventType.object);
                        break;
                    case EventType.ACTION_PULL_REFRESH://下拉刷新
//                        listBeans.clear();
//                        listBeans.addAll((List)eventType.object);
                        break;
                    case EventType.ACTION_MAIN_SETDATE:
                        goods = (List<ShopMainReponse.PayloadBean.GoodsBean>) eventType.object;
                        if (null!=goods&&goods.size()>0)
                            setData(mType);
                        break;
                }
            }
        });
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
        LogUtil.i("post=>"+position);
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

}
