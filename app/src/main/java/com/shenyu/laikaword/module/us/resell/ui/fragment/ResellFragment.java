package com.shenyu.laikaword.module.us.resell.ui.fragment;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.model.wrapper.EmptyWrapper;
import com.shenyu.laikaword.model.bean.reponse.ZhuanMaiReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.module.us.resell.ui.activity.ResellDetailsActivity;
import com.zxj.utilslibrary.utils.DateTimeUtil;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ResellFragment extends IKWordBaseFragment {
    @BindView(R.id.pirchase_ry_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smartRefreshLayout;
    private int type=0;
    int page=1;
    int pagerSize=20;
    private List<ZhuanMaiReponse.PayloadBean> payload=new ArrayList<>();
    @SuppressLint("ValidFragment")
    public ResellFragment(int position) {
        if (position==0){
            this.type=1;
        }
        if (position==1){
            this.type=2;
        }

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_zhuanmai;
    }
    EmptyWrapper emptyWrapper;
    CommonAdapter adapter;
    @Override
    public void initView(final View view) {
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,(int) UIUtil.dp2px(9),UIUtil.getColor(R.color.main_bg_gray)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommonAdapter<ZhuanMaiReponse.PayloadBean>(R.layout.zhuanmaiview_item,payload) {
            @Override
            protected void convert(ViewHolder holder, final ZhuanMaiReponse.PayloadBean payloadBean, int position) {
                //转卖日期
                holder.setText(R.id.tv_zhuanmai_time,DateTimeUtil.formatDate(StringUtil.formatLong(payloadBean.getCreateTime()),"yyyy-MM-dd HH:mm:ss"));
                //转卖编号
                holder.setText(R.id.tv_zhuanmai_no,"编号:"+payloadBean.getResellId());
                //转卖价格
                TextView tvZhuanPrice=holder.getView(R.id.tv_zhuamai_price);
                tvZhuanPrice.setText(Html.fromHtml("<font color= '#999999'>转卖价:</font>"+payloadBean.getDiscountPrice()+"元/张"));
                //商品名字
                holder.setText(R.id.tv_zhuanmai_shop_name,payloadBean.getGoodsName());
                //转卖数量
               TextView tvZhuanmaiCount=holder.getView(R.id.tv_zhuanmai_count);
               tvZhuanmaiCount.setText(Html.fromHtml("<font color= '#999999'>转卖数量:</font>"+"x"+payloadBean.getOriStock()));

                //已经转卖数量
                TextView tvZhuanMaiFinsh=holder.getView(R.id.tv_zhuanmaied_count);
                tvZhuanMaiFinsh.setText(Html.fromHtml("<font color= '#999999'>已转卖:</font>"+(StringUtil.formatIntger(payloadBean.getOriStock())-StringUtil.formatIntger(payloadBean.getStock())))+"张");

                ImageUitls.loadImg(payloadBean.getGoodsImage(),(ImageView) holder.getView(R.id.img_purchase_img));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentLauncher.with(getActivity()).put("type",String.valueOf(type)).put("goodId",payloadBean.getGoodsId()).launch(ResellDetailsActivity.class);
                    }
                });
            }


        };
        emptyWrapper =new EmptyWrapper(adapter);
        emptyWrapper.setEmptyView(R.layout.empty_view,"哇哦，篮子暂时是空的～");
        recyclerView.setAdapter(emptyWrapper);

        smartRefreshLayout.setFooterHeight(45);
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadmore(true);
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1500);
                requestData();

            }
        });
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {
        retrofitUtils.addSubscription(RetrofitUtils.apiStores.resellList(type,page,pagerSize), new ApiCallback<ZhuanMaiReponse>() {
            @Override
            public void onSuccess(ZhuanMaiReponse model) {
                page++;
                if (model.isSuccess()&&model.getPayload().size()>0) {
                    for (ZhuanMaiReponse.PayloadBean payloadBean:model.getPayload()){
                        payload.add(payloadBean);
                    }
                    emptyWrapper.notifyDataSetChanged();
                }else {
                    if (page>=2){
                        ToastUtil.showToastShort("没有更多数据");
                    }
                }
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

}
