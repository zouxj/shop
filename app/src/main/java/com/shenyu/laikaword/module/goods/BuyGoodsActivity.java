package com.shenyu.laikaword.module.goods;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.model.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.OrderListReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.zxj.utilslibrary.utils.DateTimeUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的购买
 */
public class BuyGoodsActivity extends LKWordBaseActivity {

    @BindView(R.id.rcl_goods)
    RecyclerView recyclerView;
    EmptyWrapper emptyWrapper;
    private List<OrderListReponse.PayloadBean> payload;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Override
    public int bindLayout() {
        return R.layout.activity_pick_up_goods;
    }
    private Boolean aBoolean=true;
    int page=2;
    @Override
    public void initView() {
        setToolBarTitle("我的购买");
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadmore(true);
        smartRefreshLayout.setFooterHeight(45);
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1500);
                refreshlayout.finishLoadmore(aBoolean);
                if (!aBoolean){
                    ToastUtil.showToastShort("没有更多数据了...");
                }
                oraderData(page,20);
                page++;
            }
        });
        payload = new ArrayList<>();
        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,(int) UIUtil.dp2px(9),UIUtil.getColor(R.color.main_bg_gray)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter commonAdapter=    new CommonAdapter<OrderListReponse.PayloadBean>(R.layout.item_goods,payload) {
            @Override
            protected void convert(ViewHolder holder, OrderListReponse.PayloadBean payloadBean, int position) {
                holder.setText(R.id.tv_goumai_count,"合计:￥"+payloadBean.getAmount());
                holder.setText(R.id.tv_goumai_indent_no,"订单编号:"+payloadBean.getOrderNo());
                TextView textoriginalPrice = holder.getView(R.id.tv_goumai_shop_original_price);
                textoriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.setText(R.id.tv_goumai_state,payloadBean.getPayStatus().equals("1")?"交易成功":"交易失败");
                holder.setText(R.id.tv_goumai_time, DateTimeUtil.formatDate(Long.parseLong(payloadBean.getCreateTime()),"yyyy-MM-dd HH:mm:ss"));
                if (payloadBean.getGoods()!=null) {
                    if (payloadBean.getGoods().size() > 0) {
                        textoriginalPrice.setText("￥" + payloadBean.getGoods().get(0).getOriginPrice());
                        holder.setText(R.id.tv_goumai_shop_name, payloadBean.getGoods().get(0).getGoodsName());
                        holder.setText(R.id.tv_goumai_shop_price, "￥" + payloadBean.getGoods().get(0).getCurrentPrice());
                        holder.setText(R.id.tv_goumai_shop_purchase, "X" + payloadBean.getGoods().get(0).getQuantity());
                        ImageUitls.loadImg(payloadBean.getGoods().get(0).getGoodsImage(), (ImageView) holder.getView(R.id.iv_goumai_img));
                    }
                }
            }
        };
         emptyWrapper = new EmptyWrapper(commonAdapter);
        emptyWrapper.setEmptyView(R.layout.empty_view,UIUtil.getString(R.string.goumai_empty));

        recyclerView.setAdapter(emptyWrapper);
    }

    @Override
    public void doBusiness(Context context) {

        oraderData(1,20);

    }

    @Override
    public void setupActivityComponent() {

    }
    private void oraderData(final int page, int pageSize){
        loadViewHelper.showLoadingDialog(this);
        retrofitUtils.addSubscription(RetrofitUtils.apiStores.myOrderList(page,pageSize), new ApiCallback<OrderListReponse>() {
            @Override
            public void onSuccess(OrderListReponse model) {
                if (model.isSuccess()) {
                    if (model.getPayload().size() > 0) {
                        for (OrderListReponse.PayloadBean payloadBean:model.getPayload()) {
                            payload.add(payloadBean);
                        }
                        aBoolean=true;
                        emptyWrapper.notifyDataSetChanged();
                    }else {
                        aBoolean=false;
                    }
                }

            }

            @Override
            public void onFailure(String msg) {
                loadViewHelper.closeLoadingDialog();
                aBoolean=false;
//                loadViewHelper.showErrorResert(BuyGoodsActivity.this, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        oraderData(1,20);
//                    }
//                });
            }

            @Override
            public void onFinish() {
                loadViewHelper.closeLoadingDialog();
            }
        });

    }
}
