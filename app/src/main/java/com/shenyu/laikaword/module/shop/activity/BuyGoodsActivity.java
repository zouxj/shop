package com.shenyu.laikaword.module.shop.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.adapter.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.reponse.OrderListReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.DateTimeUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BuyGoodsActivity extends LKWordBaseActivity {

    @BindView(R.id.rcl_goods)
    RecyclerView recyclerView;
    EmptyWrapper emptyWrapper;
    private List<OrderListReponse.PayloadBean> payload;
    @Override
    public int bindLayout() {
        return R.layout.activity_pick_up_goods;
    }

    @Override
    public void initView() {
        setToolBarTitle("我的购买");
        payload = new ArrayList<>();

        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,(int) UIUtil.dp2px(9),UIUtil.getColor(R.color.main_bg_gray)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter commonAdapter=    new CommonAdapter<OrderListReponse.PayloadBean>(R.layout.item_goods,payload) {
            @Override
            protected void convert(ViewHolder holder, OrderListReponse.PayloadBean payloadBean, int position) {
                holder.setText(R.id.tv_goumai_count,payloadBean.getDiscount());
                holder.setText(R.id.tv_goumai_indent_no,"订单编号:"+payloadBean.getOrderId());
                holder.setText(R.id.tv_goumai_shop_name,payloadBean.getGoodsName());
                holder.setText(R.id.tv_goumai_shop_original_price,payloadBean.getOriginalPrice());
                holder.setText(R.id.tv_goumai_shop_price,payloadBean.getDiscountPrice());
                holder.setText(R.id.tv_goumai_shop_purchase,payloadBean.getQuantity()+"");
                holder.setText(R.id.tv_goumai_time, DateTimeUtil.formatDate(payloadBean.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                Picasso.with(UIUtil.getContext()).
                        load(payloadBean.getGoodsImage()).
                        placeholder(R.mipmap.defaul_icon).
                        error(R.mipmap.defaul_icon).into((ImageView) holder.getView(R.id.iv_goumai_img));
            }
        };
         emptyWrapper = new EmptyWrapper(commonAdapter);
        emptyWrapper.setEmptyView(R.layout.empty_view);
        recyclerView.setAdapter(emptyWrapper);
    }

    @Override
    public void doBusiness(Context context) {

        oraderData();

    }

    @Override
    public void setupActivityComponent() {

    }
    private void oraderData(){
        loadViewHelper.showLoadingDialog(this);
        RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.orderList(), new ApiCallback<OrderListReponse>() {
            @Override
            public void onSuccess(OrderListReponse model) {
                if (model.isSuccess()){
                    payload.clear();
                    payload.addAll(model.getPayloads());
                    emptyWrapper.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String msg) {
                loadViewHelper.showErrorResert(BuyGoodsActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        oraderData();
                    }
                });
            }

            @Override
            public void onFinish() {
                loadViewHelper.closeLoadingDialog();
            }
        });

    }
}
