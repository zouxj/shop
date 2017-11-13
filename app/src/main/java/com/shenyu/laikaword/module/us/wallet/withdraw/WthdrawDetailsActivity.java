package com.shenyu.laikaword.module.us.wallet.withdraw;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.zxj.utilslibrary.utils.DateTimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现成功
 */
public class WthdrawDetailsActivity extends LKWordBaseActivity {


    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_moeny)
    TextView tvMoeny;
    @BindView(R.id.tv_wthdraw_time)
    TextView tvWthdrawTime;

    @Override
    public int bindLayout() {
        return R.layout.activity_wthdraw_details;
    }

    @Override
    public void initView() {
        setTitle("提现详情");
        setToolBarLeft(0,"",View.GONE);
        tvWthdrawTime.setText(DateTimeUtil.formatDate(DateTimeUtil.getEndDayOfTomorrow(),"yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public void doBusiness(Context context) {
            String money = getIntent().getStringExtra("money");
        String bankName = getIntent().getStringExtra("bankName");
        tvCard.setText(bankName);
        tvMoeny.setText("￥"+money);
    }

    @Override
    public void setupActivityComponent() {

    }


    @OnClick(R.id.bt_finish)
    public void onViewClicked() {
        finish();
    }


}
