package com.shenyu.laikaword.module.mine.systemsetting.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.module.login.activity.LoginActivity;
import com.shenyu.laikaword.rxbus.RxBus;
import com.shenyu.laikaword.rxbus.event.Event;
import com.shenyu.laikaword.rxbus.event.EventType;
import com.zxj.utilslibrary.utils.ActivityManageUtil;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SPUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingSystemActivity extends LKWordBaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_setting_system;
    }

    @Override
    public void initView() {
        setToolBarTitle("设置");
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.toolbar_subtitle, R.id.toolbar_title, R.id.toolbar, R.id.set_rl_user_info, R.id.set_rl_user_acount_bd, R.id.set_rl_about, R.id.set_tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_rl_user_info://个人资料
                IntentLauncher.with(this).launch(UserInfoActivity.class);
                break;
            case R.id.set_rl_user_acount_bd://账号绑定
                IntentLauncher.with(this).launch(AccountBindActivity.class);
                break;
            case R.id.set_rl_about://关于我们
                IntentLauncher.with(this).launch(AboutAppActivity.class);
                break;
            case R.id.set_tv_exit://退出当前账号
                SPUtil.removeSp(Constants.LOGININFO_KEY);
                RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER, null));
                IntentLauncher.with(this).launch(LoginActivity.class);

                break;
        }
    }
}
