package com.shenyu.laikaword.module.mine.systemsetting.activity;

import android.content.Context;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;

import butterknife.OnClick;

public class AccountBindActivity extends LKWordBaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_account_bind;
    }

    @Override
    public void initView() {
        setToolBarTitle("账号绑定");
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }



    @OnClick({R.id.bind_rl_phone, R.id.bind_rl_weixin, R.id.bind_rl_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bind_rl_phone:
                //TODO 更换手机号
                IntentLauncher.with(this).launch(ChangePhoneActivity.class);
                break;
            case R.id.bind_rl_weixin:
                //TODO 绑定微信
                break;
            case R.id.bind_rl_qq:
                //TODO 绑定QQ
                break;
        }
    }
}
