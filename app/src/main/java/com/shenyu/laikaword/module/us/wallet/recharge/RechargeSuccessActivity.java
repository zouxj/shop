package com.shenyu.laikaword.module.us.wallet.recharge;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提现成功
 */
public class RechargeSuccessActivity extends LKWordBaseActivity {


    @BindView(R.id.tv_rechage_type)
    TextView tvRechageType;
    @BindView(R.id.tv_money)
    TextView tvMoney;

    @Override
    public int bindLayout() {
        return R.layout.activity_recharge_success;
    }

    @Override
    public void initView() {
        setTitle("支付详情");
        setToolBarLeft(0, null, View.GONE);
    }

    @Override
    public void doBusiness(Context context) {
        tvMoney.setText(getIntent().getStringExtra("money"));
        tvRechageType.setText(getIntent().getStringExtra("pay_type").equals("3")?"支付宝":"微信支付");

    }

    @Override
    public void setupActivityComponent() {

    }

    @OnClick(R.id.bt_finish)
    public void onViewClicked() {
        finish();
    }
}
