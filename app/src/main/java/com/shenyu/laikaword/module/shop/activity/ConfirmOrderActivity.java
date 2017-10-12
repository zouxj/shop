package com.shenyu.laikaword.module.shop.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.module.shop.ConfirmOrderView;
import com.shenyu.laikaword.module.shop.ShopModule;
import com.shenyu.laikaword.widget.AmountView;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 确认订单Activity
 */
public class ConfirmOrderActivity extends LKWordBaseActivity implements ConfirmOrderView {

    @BindView(R.id.av_zj)
    AmountView mAmountView;
    @BindView(R.id.rl_pay_type)
    RecyclerView recyclerView;
    @Inject
    RecycleViewDivider recycleViewDivider;
    @Inject
    LinearLayoutManager linearLayoutManager;
    private String payType;//支付类型
    private int payFlogType =0;
    private int count=1;//购买数量
    @Inject
    ConfirmOrderPresenter mConfirmOrderPresenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_confirm_order;

    }

    @Override
    public void initView() {
        setToolBarTitle("确认订单");
        mAmountView.setGoods_storage(50);
        mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                count = amount;
            }
        });
        List<String> typeData = new ArrayList<>();
        typeData.add("账户余额");
        typeData.add("支付宝");
        typeData.add("QQ钱包");
        typeData.add("微信支付");
        final int[] payIconList = {R.mipmap.pay_yue_icon,R.mipmap.pay_zhifubao_icon,R.mipmap.pay_qq_icon,R.mipmap.pay_wechat_icon};
        recyclerView.addItemDecoration(recycleViewDivider );
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new CommonAdapter<String>(R.layout.item_pay_type,typeData) {
            int selectedPosition=0;
            @Override
            protected void convert(final ViewHolder holder, String strings, final int position) {
                holder.setText(R.id.tv_pay_type,strings);
                holder.setBackground(R.id.iv_pay,UIUtil.getDrawable(payIconList[position]));
                CheckBox checkBox=  holder.getView(R.id.cb_pay_type);
                checkBox.setChecked((position==selectedPosition?true:false));
                holder.setOnClickListener(R.id.item_ck, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedPosition!=position){
                            //先取消上个item的勾选状态
                            if (selectedPosition!=-1) {
                                holder.itemView.setSelected(false);
                                notifyItemChanged(selectedPosition);
                                payType="";
                            }
                            //设置新Item的勾选状态
                            selectedPosition = position;
                            holder.itemView.setSelected(true);
                            notifyItemChanged(selectedPosition);
                            switch (position){
                                case 0:
                                    payType="账户余额";
                                    payFlogType=0;
                                    break;
                                case 1:
                                    payType="支付宝";
                                    payFlogType=1;
                                    break;
                                case 2:
                                    payType="QQ钱包";
                                    payFlogType=2;
                                    break;
                                case 3:
                                    payType="微信支付";
                                    payFlogType=3;
                                    break;
                            }

                        }else if (selectedPosition==position){
                            selectedPosition = -1; //选择的position赋值给参数，
                            holder.itemView.setSelected(false);
                            notifyItemChanged(position);//刷新当前点击item
                            payType="";
                        }
                    }
                });
            }
        });
    }
    @OnClick({R.id.tv_to_pay})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_to_pay:
                //TODO 去支付
                mConfirmOrderPresenter.cofirmPay(payFlogType);
                break;
        }
    }
    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new ShopModule(this,this)).inject(this);
    }

    @Override
    public void isLoading() {

    }

    @Override
    public void dataCountChanged(int count) {

    }

    @Override
    public void loadFinished() {

    }

    @Override
    public void loadFailure() {

    }

    @Override
    public void paySuccess() {

    }

    @Override
    public void payFaire() {


    }
}
