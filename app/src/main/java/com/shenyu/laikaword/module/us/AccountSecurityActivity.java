package com.shenyu.laikaword.module.us;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.module.us.appsetting.acountbind.AccountBindActivity;
import com.shenyu.laikaword.module.us.appsetting.acountbind.BoundPhoneActivity;
import com.shenyu.laikaword.module.us.setpassword.SetPassWordMsgCodeActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountSecurityActivity extends LKWordBaseActivity {


    @BindView(R.id.tv_upate_phone)
    TextView tvUpatePhone;
    @BindView(R.id.head_user_back)
    ImageView headUserBack;
    @BindView(R.id.rl_bd_phone)
    RelativeLayout rlBdPhone;
    @BindView(R.id.head_user_name)
    ImageView headUserName;
    @BindView(R.id.rl_bd_account)
    RelativeLayout rlBdAccount;
    @BindView(R.id.head_user_qq)
    ImageView headUserQq;
    @BindView(R.id.rl_seting_pay_password)
    RelativeLayout rlSetingPayPassword;

    @Override
    public int bindLayout() {
        return R.layout.activity_account_security;
    }

    @Override
    public void initView() {
        setToolBarTitle("账号与安全");
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }


    @OnClick({R.id.rl_bd_phone, R.id.rl_bd_account, R.id.rl_seting_pay_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bd_phone:
                //TODO 修改手机号
                IntentLauncher.with(mActivity).launch(BoundPhoneActivity.class);
                break;
            case R.id.rl_bd_account:
                //TODO 绑定账号
                IntentLauncher.with(mActivity).launch(AccountBindActivity.class);
                break;
            case R.id.rl_seting_pay_password:
                //TODO设置支付密码
                IntentLauncher.with(mActivity).launch(SetPassWordMsgCodeActivity.class);
                break;
        }
    }
}
