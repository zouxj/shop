package com.shenyu.laikaword.module.mine.remaining;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的余额
 */
public class UserRemainingActivity extends LKWordBaseActivity {


    @BindView(R.id.re_tv_sum)
    TextView reTvSum;

    @Override
    public int bindLayout() {
        return R.layout.activity_user_remaining;
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void initView() {
        setToolBarTitle("我的余额");
        setToolBarRight("明细",0);

    }

    @Override
    public void setupActivityComponent() {

    }
    @OnClick({R.id.re_bt_pay,R.id.re_bt_withdraw,R.id.toolbar_subtitle})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.re_bt_pay:
                //TODO 充值
                IntentLauncher.with(this).launch(RechargeMoneyActivity.class);
                break;
            case R.id.re_bt_withdraw:
                //TODO 提现
                IntentLauncher.with(this).launch(WthdrawMoneyActivity.class);
                break;
            case R.id.toolbar_subtitle:
                //TODO 明细
                IntentLauncher.with(this).launch(DetailMoneyActivity.class);
                break;
        }
    }

}
